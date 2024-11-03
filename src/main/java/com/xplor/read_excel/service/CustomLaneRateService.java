package com.xplor.read_excel.service;

import com.xplor.read_excel.reository.CustomLaneRateRepository;
import com.xplor.read_excel.entity.CustomLaneRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class CustomLaneRateService {

    @Autowired
    private CustomLaneRateRepository customLaneRateRepository;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private XMLService xmlService;

    // extract the data from file and save to DB
    public void extractAndSaveDataFromFile(MultipartFile file) throws IOException {

        List<CustomLaneRate> customLaneRates = new ArrayList<>();

        //1. read the excel file and save the data to DB
        if (file.getOriginalFilename() != null && file.getOriginalFilename().endsWith(".xlsx")) {
            customLaneRates = excelService.readExcelSLSXFileAndSaveData(file);
        }

        //2. read the xml file and save the data to DB
        if (file.getOriginalFilename() != null && file.getOriginalFilename().endsWith(".xml")) {
            customLaneRates = xmlService.readXMLFileAndSaveData(file);
        }

        // save the data to DB
        customLaneRateRepository.saveAll(customLaneRates);

    }

    public File getLaneRatesXMLFile() throws IOException {
        return xmlService.getLaneRatesXMLFile(customLaneRateRepository.findAll());
    }




}
