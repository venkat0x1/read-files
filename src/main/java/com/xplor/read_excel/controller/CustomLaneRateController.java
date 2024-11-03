package com.xplor.read_excel.controller;

import com.xplor.read_excel.service.CustomLaneRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/custom-lane-rates")
public class CustomLaneRateController {

    @Autowired
    private CustomLaneRateService customLaneRateService;

    @PostMapping("/upload-rates")
    public ResponseEntity<String> uploadRates(@RequestParam("file") MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Please upload a file");
        }

        customLaneRateService.extractAndSaveDataFromFile(file);

        return ResponseEntity.ok("File uploaded successfully");
    }


    @GetMapping("/rates-xml-file")
    public ResponseEntity<File> getRatesXMLFile() throws IOException {
        File file = customLaneRateService.getLaneRatesXMLFile();
        return ResponseEntity.ok(file);

        // Prepare the response with the file
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

//        return new ResponseEntity<>(new FileSystemResource(file), headers, HttpStatus.OK);
    }


}