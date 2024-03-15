package dev.marvin.savings.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUpload {

    public static String uploadFile(MultipartFile multipartFile){

        File file;
        try {
            InputStream inputStream = multipartFile.getInputStream();
            byte[] data = inputStream.readAllBytes();

            file = new File("/resources/fakes3" + multipartFile.getOriginalFilename());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(data);
            fileOutputStream.flush();

            fileOutputStream.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file.getPath();
    }
}
