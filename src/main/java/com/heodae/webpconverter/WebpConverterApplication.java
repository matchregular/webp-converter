package com.heodae.webpconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class WebpConverterApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebpConverterApplication.class, args);
    }

}
