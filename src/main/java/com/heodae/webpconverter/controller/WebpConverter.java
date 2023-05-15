package com.heodae.webpconverter.controller;

import com.luciad.imageio.webp.WebPWriteParam;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;

public class WebpConverter {
    public static void main(String[] args) throws Exception {
        WebpConverter wc = new WebpConverter();
        wc.convertJpegToWebpLossless();
    }

    private void convertJpegToWebpLossless() throws Exception {
        ClassPathResource classPathResource = new ClassPathResource("home/Cat03.jpg");

        File readFile = new File(classPathResource.getURI());
        BufferedImage image = ImageIO.read(readFile);

        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

        WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
        //Notify encoder to consider WebPWriteParams
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        //Set lossless compression
        writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);

        File writeFile = new File("test22.webp");
        // Save the image
        writer.setOutput(new FileImageOutputStream(writeFile));
        writer.write(null, new IIOImage(image, null, null), writeParam);
    }


}
