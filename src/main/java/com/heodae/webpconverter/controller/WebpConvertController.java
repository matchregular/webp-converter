package com.heodae.webpconverter.controller;

import com.heodae.webpconverter.service.WebpConvertService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Controller
public class WebpConvertController {
    private static final String uploadDirectory = "D:\\upload"; // 실제 파일을 저장할 디렉토리 경로로 변경해야 합니다.

    private final WebpConvertService webpConvertService;

    public WebpConvertController(WebpConvertService webpConvertService) {
        this.webpConvertService = webpConvertService;
    }

    // 업로드 화면
    @GetMapping("/upload")
    public String uploadForm() {
        // 업로드 화면을 반환하는 코드 작성
        return "home";
    }

    @PostMapping("/upload")
    public void handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        // 업로드된 파일 처리 로직
        String originalFilename = file.getOriginalFilename();
        String filePath = uploadDirectory + File.separator + originalFilename;

        // 파일 저장
        Path targetLocation = Paths.get(filePath);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        // WebP로 변환
        String webpFilePath = webpConvertService.convertToWebP(filePath);

        // WebP 파일 다운로드를 위한 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("image/webp"));
        headers.setContentDispositionFormData("attachment", getWebPFileName(originalFilename));

        // WebP 파일 다운로드
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, headers.getContentDisposition().toString());
        response.setContentType(headers.getContentType().toString());

        try (InputStream inputStream = Files.newInputStream(Paths.get(webpFilePath));
             OutputStream outputStream = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace();
        }

        deleteUploadedFile(file.getOriginalFilename());
        deleteUploadedFile(file.getOriginalFilename()+".webp");
    }

    private String getWebPFileName(String originalFilename) {
        String filename = StringUtils.cleanPath(originalFilename);
        int extensionIndex = filename.lastIndexOf('.');
        if (extensionIndex != -1) {
            filename = filename.substring(0, extensionIndex);
        }
        return filename + ".webp";
    }

    // 업로드된 파일 삭제
    private void deleteUploadedFile(String filename) {
        String filePath = uploadDirectory + File.separator + filename;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }
}
