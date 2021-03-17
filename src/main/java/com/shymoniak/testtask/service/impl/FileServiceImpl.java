package com.shymoniak.testtask.service.impl;

import com.shymoniak.testtask.service.FileService;
import com.shymoniak.testtask.service.utils.Validator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.shymoniak.testtask.constant.ApplicationConstants.*;

@Service
public class FileServiceImpl implements FileService {
    Validator validator = new Validator();

    @Override
    public void uploadFile(MultipartFile multipartFile) {
        try {
            multipartFile.transferTo(new File(RESOURCES_PATH + multipartFile.getOriginalFilename()));
            File file = new File(RESOURCES_PATH + multipartFile.getOriginalFilename());
            if(!isValidFile(file.getName())){
                file.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidFile(String fileName) throws IOException {
        return validator.isValidCSVFile(new File(RESOURCES_PATH + fileName));
    }
}
