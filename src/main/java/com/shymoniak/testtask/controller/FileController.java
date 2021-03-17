package com.shymoniak.testtask.controller;

import com.shymoniak.testtask.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService service;

    @PostMapping
    ResponseEntity<Void> addFile(@RequestParam("file") MultipartFile file) {
        service.uploadFile(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
