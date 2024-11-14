package com.example.boardproj.service;

import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.dto.PageRequestDTO;
import com.example.boardproj.dto.PageResponseDTO;
import com.example.boardproj.dto.ReplyDTO;
import com.example.boardproj.entity.Board;
import com.example.boardproj.entity.Reply;
import com.example.boardproj.repository.BoardRepository;
import com.example.boardproj.repository.ReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService{

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @Override
    public void register(ReplyDTO replyDTO) {
        // 등록   : 댓글은 참조관계로 부모가 Board 이기에 Board 에서 가지고 있는
        // entity 를 set 하고 나서 저장해야 한다.
        // 일반적으로 Board board = new board(); 라고 만든
        // board.setBno(replyDTO.genBno) 로 만든 객체는 임의의 객체임으로
        // 저장이 되지 않는다.
        Optional<Board> optionalBoard =
        boardRepository.findById(replyDTO.getBno());    // entity 를 찾아온다.

        Board board = optionalBoard.orElseThrow(EntityNotFoundException::new);
        // 없을때는 예외처리

        Reply reply =
        modelMapper.map(replyDTO, Reply.class);
        reply.setBoard(board);
//        reply.setUser(user)   // 유저도 참조한다면

        replyRepository.save(reply);

    }



    @Override
    public ReplyDTO read(Long rno) {
        Reply reply =
        replyRepository.findById(rno).orElseThrow(EntityNotFoundException::new);

        ReplyDTO replyDTO = modelMapper.map(reply, ReplyDTO.class);

        return replyDTO;
    }



    @Override
    public List<ReplyDTO> list(Long bno) {
        List<Reply> replyList =
        replyRepository.findByBoardBno(bno);

        List<ReplyDTO> list =
        replyList.stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class).setBoardDTO(modelMapper.map(reply.getBoard(), BoardDTO.class)))
                .collect(Collectors.toList());

        // list 에서 하나씩 순차적으로 0부터 꺼내서 꺼낸 아이를 변수 reply 라 칭하고
        // 이걸 가지고 ReplyDTO 타입으로 변환한 다음 리턴값으로 ReplyDTO 타입으로
        // 받는데 받은 ReplyDTO 에 setBoardDTO 를 하기 위해 아까 받은 reply 에서
        // reply.getBoard 를 받아서 다시 BoardDTO 타입으로 변환해서 set 하기 위해
        // setBoardDTO 의 리턴타입을 ReplyDTO 로 줘서 리턴을 다시 ReplyDTO 로 만든다.
        // 리턴값이 있어야지만 .collect(Collectors.toList()); 를 사용할 수 있기 때문

        return list;
    }



    @Override
    public Page<ReplyDTO> listpage(Long bno, Pageable pageable) {
        // pageable 은 컨트롤러로부터 현재 페이지를 받아 -1 을 줘서
        // 내부적으로 limit 을 사용할 수 있도록 값을 주며,
        // 현재 페이지당 보여질 객체수를 결정해 줍니다.
        // 위 2가지만 설정 가능하며(오버로딩), 추가로 정렬조건 또한 줄 수 있습니다.
        // 현재는 만들어진 pageable 을 받았으나 컨트롤러에서 만들어서 넣어주면
        // 아니면 내부적으로 페이지만 받아서 처리하던
        // select * from table order by 정렬조건 limit 현재페이지에서 시작할 번호, 몇개씩
        // select * from table (pageable) 파라미터로 넣어주면 페이지객체로 반환합니다.
        // 15페이지라 하면 10개씩이라 하면 10페이지부터 10부터 ~ 20까지 보여줄 예정
        // 내부에서 직접만들고 page 와 size 를 받았다면
//        Pageable ex = PageRequest
//                .of((int) (bno-1), 10, Sort.by("rno").descending());

        Page<Reply> replyPage =
        replyRepository.findByBoardBno(bno, pageable);
        // pageImpl 을 이용해서 재지정
        List<Reply> replyList =
                replyPage.getContent();

        List<ReplyDTO> replyDTOList =
            replyList.stream().map(
                    reply -> modelMapper.map(reply, ReplyDTO.class)
                            .setBoardDTO(modelMapper.map(reply.getBoard(), BoardDTO.class))
            ).collect(Collectors.toList());

        Page<ReplyDTO> replyDTOPage =

        new PageImpl<>(replyDTOList, pageable, replyPage.getTotalElements());

        return replyDTOPage;
    }



    @Override
    public int totalEl() {
        Pageable pageable
                = PageRequest.of(0, 10);

        Page<Reply> replyPage =
                replyRepository.findAll(pageable);
        return (int) replyPage.getTotalElements();
    }



    @Override
    public PageResponseDTO<ReplyDTO> pageList(PageRequestDTO pageRequestDTO, Long bno) {

        Pageable pageable
                = pageRequestDTO.getPageable("rno");

        Page<Reply> replyPage =
        replyRepository.findByBoardBno(bno, pageable);

        List<Reply> replyList = replyPage.getContent();

        List<ReplyDTO> replyDTOList =
        replyList.stream()
                .map(reply -> modelMapper.map(reply, ReplyDTO.class)
                        .setBoardDTO(modelMapper.map(reply.getBoard(), BoardDTO.class)))
                        .collect(Collectors.toList());

        PageResponseDTO<ReplyDTO> responseDTO =
                PageResponseDTO.<ReplyDTO>withAll()
                        .dtoList(replyDTOList)
                        .total((int) replyPage.getTotalElements())
                        .pageRequestDTO(pageRequestDTO)
                        .build();

        return responseDTO;
    }



    @Override
    public ReplyDTO update(ReplyDTO replyDTO) {
        // 들어온 replyDTO 값을 찍어주고
        log.info("댓글서비스 replyDTO" + replyDTO);
        // 수정할 데이터를 찾는다.
        Reply reply =
        replyRepository.findById(replyDTO.getRno())
                .orElseThrow(EntityNotFoundException::new);
        // 수정할 데이터를 수정한다.   // 댓글 내용만 수정할 것이다.
        reply.setReplyText(replyDTO.getReplyText());

        // 업데이트 수행
        // 수정한 뒤에 reply 을 replyDTO 로 변환해서 반환을 해야하지만...
        return modelMapper.map(reply, ReplyDTO.class);
    }



    @Override
    public void remove(Long rno) {
        log.info(rno);
//        Reply reply =
//        replyRepository.findById(rno).orElseThrow(EntityNotFoundException::new);
//        replyRepository.delete(reply);
        replyRepository.deleteById(rno);

    }

    @Override
    public void removeWithBoard(Long bno) {
        replyRepository.delreplybno(bno);
        boardRepository.deleteById(bno);
    }
}
