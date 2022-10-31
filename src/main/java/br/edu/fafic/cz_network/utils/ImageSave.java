package br.edu.fafic.cz_network.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageSave {

    public static void save(File fileToSave, MultipartFile image) throws IOException {
        OutputStream os = new FileOutputStream(fileToSave);
        os.write(image.getBytes());
        os.close();
    }

}
