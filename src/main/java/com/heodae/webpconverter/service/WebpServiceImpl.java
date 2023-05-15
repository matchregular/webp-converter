package com.heodae.webpconverter.service;

import com.luciad.imageio.webp.WebPWriteParam;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

@Service
public class WebpServiceImpl implements FileConvertService {
    @Override
    public File convert(File file) {
        File convertFile = null;

        String fileName = file.getName().toLowerCase(Locale.ROOT);
        String[] imageExt = {"jpg", "jpeg", "png"};

        if(FilenameUtils.isExtension(fileName, imageExt)){
            try {
                BufferedImage image = ImageIO.read(file);

                ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

                WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
                writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                writeParam.setCompressionType(writeParam.getCompressionTypes()[WebPWriteParam.LOSSY_COMPRESSION]);

                convertFile = new File(file.getName() + "." +FilenameUtils.getExtension(file.getName()));
                writer.setOutput(new FileImageOutputStream(convertFile));
                writer.write(null, new IIOImage(image, null, null), writeParam);
            }catch (IOException e){
                e.printStackTrace();
                convertFile = null;
            }
        }else{
            convertFile = null;
        }

        return convertFile;
    }
}
