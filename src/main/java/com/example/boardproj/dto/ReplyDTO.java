package com.example.boardproj.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long rno;

    private Long bno;

    @NotBlank(message = "댓글은 공백일 수 없습니다.")
    @Size(min = 2, max = 255, message = "댓글은 2~255 글자 사이여야 합니다.")
    private String replyText;

    @NotBlank(message = "작성자는 공백일 수 없습니다.")
    @Size(min = 2, max = 255, message = "작성자는 2~255 글자 사이여야 합니다.")
    private String writer;  // 이것도 나중에 게시판도 나중에 회원을 참조하자

    private BoardDTO boardDTO;    // dto 는 dto 를

    // 날짜

    private LocalDate regdate;

    private LocalDate updatedate;

    public ReplyDTO setBoardDTO(BoardDTO boardDTO) {
        this.boardDTO = boardDTO;
        return this;
    }
}
