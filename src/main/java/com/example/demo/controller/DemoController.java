package com.example.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
public class DemoController {

    private static final String APPLICATION_KEY = "applicationKey";
    private static final String PATH = "path";
    private static final String NAME = "name";


     @GetMapping(value = "/download/{path}")
     public HttpServletResponse getDocument(
             @RequestHeader(name = APPLICATION_KEY) String applicationKey,
             @PathVariable(name = PATH) String path,
             HttpServletRequest request, HttpServletResponse response)
             throws  IOException {

         //log.info("Debut du telechargement :");

         try (InputStream responseStream = getInputStream()) {
             try (BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream())) {
                 IOUtils.copy(responseStream, bos);
             }
         }
         return response;
    }


    private InputStream getInputStream() throws FileNotFoundException {
         InputStream inputStream = new FileInputStream("file.pdf");
         return inputStream;
    }



    @PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE,
            headers = {"content-type=multipart/mixed", "content-type=multipart/form-data"})
    ResponseEntity uploadDocument(
            @RequestHeader(name = APPLICATION_KEY) String applicationKey,
            @RequestParam(name = "file") MultipartFile file,
            @RequestParam(value = "body", required = false) String params,
            HttpServletRequest request
    ) {

         //log.info("File uploaded : " ,file);
         return ResponseEntity.ok(file.getName());
    }


}
