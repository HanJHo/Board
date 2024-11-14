package com.example.boardproj.repository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
class ReplyRepositoryTest {


    @Autowired
    ReplyRepository replyRepository;

    @Test
    @Transactional
    public  void boardreplyTest(){


        replyRepository.findByBoardBno(305L).forEach(reply -> log.info(reply));
        replyRepository.delreplybno(305L);
        log.info(replyRepository.findByBoardBno(305L).size());
        log.info(replyRepository.findByBoardBno(305L).size());
        log.info(replyRepository.findByBoardBno(305L).size());
        log.info(replyRepository.findByBoardBno(305L).size());
        log.info(replyRepository.findByBoardBno(305L).size());


    }

}