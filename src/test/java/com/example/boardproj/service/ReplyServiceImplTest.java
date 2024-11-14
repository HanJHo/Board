package com.example.boardproj.service;

import ch.qos.logback.core.model.Model;
import com.example.boardproj.dto.PageResponseDTO;
import com.example.boardproj.dto.ReplyDTO;
import com.example.boardproj.repository.ReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ReplyServiceImplTest {

    @Autowired
    ReplyService replyService;

//    @Test
//    public void listpageTest(){
//        Long bno = 1L;
//        Pageable pageable = PageRequest.of(0, 10);
//
//        Page<ReplyDTO> replyDTOPage =
//        replyService.listpage(bno, pageable);
//
//        log.info(replyDTOPage.getTotalPages());
//        log.info(replyDTOPage.getTotalElements());
//        log.info(replyDTOPage.getTotalContent());
//
////        PageResponseDTO<ReplyDTO>   << 이유는
//
//        Model model = null;
//        model.addAttribute("count", replyDTOPage.getTotalElements());
//        model.addAttribute("dtoList", replyDTOPage.getContent());
//        model.addAttribute("page", ??);
//        model.addAttribute("size", ??);
//
//        <div th:each="dto : ${dtoList}>
//        <div th:each="dto : ${#numbers.sequence((Math.ceil(this.page / 10.0)) * 10)-size, (Math.ceil(total / (double) size))}>
//    }

    @Test
    public void updatedelTest(){

        // 1번을 수정 및 지울 예정
        ReplyDTO replyDTO = new ReplyDTO();
        replyDTO.setRno(1L);
        replyDTO.setReplyText("이걸로 수정할래");

        ReplyDTO replyDTO1 =
        replyService.update(replyDTO);
        log.info("수정 이후 결과" + replyDTO1);

        // 삭제 수정 이후 바로 삭제
        replyService.remove(1L);

    }

    @Test
    public void listTest(){

//        List<ReplyDTO> list =
//        replyService.list();

//        list.forEach(replyDTO -> log.info(replyDTO));

    }


    @Test
    public void registerTest(){
        // 입력하고 싶어요
        ReplyDTO replyDTO = new ReplyDTO();

        replyDTO.setReplyText("처음다는 댓글");
        replyDTO.setWriter("신형만");
        // 두가지 값만 넘어온다.
        // 그리고 어디에 달리는 댓글인지
        // 부모가 누구인지
        replyDTO.setBno(1999L);

        try {
            replyService.register(replyDTO);
            log.info("예외처리된다면 이건 출력 안됨");
        }catch (EntityNotFoundException e){
            log.info("부모 board 객체를 찾을 수 없습니다.");
            log.info("그래서 저장불가");
        }

    }

}