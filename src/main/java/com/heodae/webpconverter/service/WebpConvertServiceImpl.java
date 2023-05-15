package com.heodae.webpconverter.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WebpConvertServiceImpl implements WebpConvertService {

    private String getCwebpExecutablePath() {
        String osName = System.getProperty("os.name").toLowerCase();
        String cwebpExecutablePath;

        if (osName.contains("win")) {
            // Windows
            cwebpExecutablePath = "D:\\dev\\libwebp-1.3.0-windows-x64\\libwebp-1.3.0-windows-x64\\bin\\cwebp.exe"; // Windows에서의 cwebp 실행 파일 경로
        } else {
            // Linux 또는 기타 운영 체제
            cwebpExecutablePath = "/usr/bin/cwebp"; // Linux에서의 cwebp 실행 파일 경로
        }

        return cwebpExecutablePath;
    }

    @Override
    public String convertToWebP(String filePath) throws IOException {
        String webpFilePath = filePath + ".webp";
        String cwebpPath = getCwebpExecutablePath();
        ProcessBuilder processBuilder = new ProcessBuilder(cwebpPath, filePath, "-o", webpFilePath);
        Process process = processBuilder.start();
        try {
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("WebP conversion process returned non-zero exit code: " + exitCode);
            }
        } catch (InterruptedException e) {
            throw new IOException("WebP conversion process was interrupted", e);
        }
        return webpFilePath;
    }

}
