package com.heodae.webpconverter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SpringBootTest
class FileConvertServiceApplicationTests {

    @Test
    void contextLoads() throws IOException {
        readWebpImage();
    }

    private void readWebpImage() throws IOException {
        BufferedImage image = ImageIO.read(new File("/home/test.webp"));
        System.out.printf("\nDimension of the image:%dx%d", image.getWidth(), image.getHeight());
    }

}
