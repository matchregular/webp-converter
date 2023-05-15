package com.heodae.webpconverter.controller;

import com.heodae.webpconverter.service.FileConvertService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class HomeController {
    private final FileConvertService fileConvertService;

    public HomeController(FileConvertService fileConvertService) {
        this.fileConvertService = fileConvertService;
    }

    @GetMapping("/home")
    public String home(){
        return "home";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile[] uploadFile){
        File newFile = new File(uploadFile[0].getOriginalFilename());
        try {
            uploadFile[0].transferTo(newFile);
            File convert = fileConvertService.convert(newFile);

            System.out.println("convert : " + convert.getName());

            ArrayList arrayList = new ArrayList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "index";
    }

}
