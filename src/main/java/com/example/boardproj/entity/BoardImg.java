package com.example.boardproj.entity;

import com.example.boardproj.dto.BoardDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ino;

    private String imgPath;

    private String imgName;

    // uuid 사용시
    private String newImgName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bno")
    private Board board;        // pk 값으로 객체를 가져와서 담을 객체타입으로 작성
}
