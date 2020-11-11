package com.phellipesander.cursomc.services;

import com.phellipesander.cursomc.services.exception.FileException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public BufferedImage getJpgImageFromFile(MultipartFile uploadedMultipartFile){
        String ext = FilenameUtils.getExtension(uploadedMultipartFile.getOriginalFilename());
        if (ext != null && !"png".equals(ext.toLowerCase()) && !"jpg".equals(ext.toLowerCase())) {
            throw new FileException("Somente imagens PNG e JPG são permitidas");
        }
        try {
            BufferedImage img = ImageIO.read(uploadedMultipartFile.getInputStream());
            if (ext != null && "png".equals(ext.toLowerCase())) {
                img = pngToJpg(img);
            }
            return img;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo");
        }

    }

    public BufferedImage pngToJpg(BufferedImage img) {
        BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    public InputStream getInputStream(BufferedImage img, String extension) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);
            return new ByteArrayInputStream(os.toByteArray());
        }catch (IOException e){
            throw new FileException("Erro ao ler arquivo");
        }
    }

}
