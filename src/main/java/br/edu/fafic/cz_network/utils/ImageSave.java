package br.edu.fafic.cz_network.utils;

import br.edu.fafic.cz_network.model.Usuario;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageSave {

    public void save(MultipartFile image, Usuario usuario, Boolean... postagem) throws IOException {

        String fotoUrl = "C:\\DEVELOP\\JAVA\\cz-network\\src\\main" +
                "\\java\\br\\edu\\fafic\\cz_network\\imagens\\";

        if (postagem.length > 0) {
            fotoUrl += "postagem\\" + usuario.getId();
        } else {
            fotoUrl += usuario.getId();
        }


        File fileToSave = new File(fotoUrl);

        if (!fileToSave.exists()) {
            final boolean directoryCreated = fileToSave.mkdirs();
            if (directoryCreated) {
                fileToSave = new File(fotoUrl + "\\"
                        + image.getOriginalFilename());

                OutputStream os = new FileOutputStream(fileToSave);
                os.write(image.getBytes());
                os.close();
            }
        } else {
            fileToSave = new File(fotoUrl + "\\"
                    + image.getOriginalFilename());

            OutputStream os = new FileOutputStream(fileToSave);
            os.write(image.getBytes());
            os.close();
        }
    }

}
