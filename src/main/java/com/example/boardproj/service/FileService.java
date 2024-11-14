package com.example.boardproj.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;



@Component
public class FileService {

    @Value("${imgLocation}")
    public String imgLocation;



    public void register(MultipartFile multipartFile, String newImgName){
        // 들어온 사진 이름

        System.out.println(imgLocation+ "\\" +newImgName);

        // 사진 경로 및 이름
        File file = new File(imgLocation+ "\\" +newImgName);
        // 사진 저장

        try {
            multipartFile.transferTo(file);
        } catch (IOException e){

        }

    }

    // 물리파일 삭제
    public void delFile(String filePath){

        File deleteFilekkk = new File(filePath);

        // if(파일객체변수명 deleteFilekkk 가 존재한다면~~)
        if(deleteFilekkk.exists()){
            deleteFilekkk.delete(); // 물리 파일삭제
            System.out.println("파일 삭제 함");
        } else {
            System.out.println("파일 못 삭제 함");
            System.out.println("파일 못 삭제 함");
            System.out.println("파일 못 삭제 함");
        }

    }



}


