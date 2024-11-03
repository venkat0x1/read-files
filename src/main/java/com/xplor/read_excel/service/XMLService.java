package com.xplor.read_excel.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xplor.read_excel.dto.LaneRatesDTO;
import com.xplor.read_excel.entity.CustomLaneRate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class XMLService {


    public List<CustomLaneRate> readXMLFileAndSaveData(MultipartFile file) throws IOException {

        XmlMapper xmlMapper = new XmlMapper();
        LaneRatesDTO laneRatesDTO = xmlMapper.readValue(file.getInputStream(), LaneRatesDTO.class);
        return laneRatesDTO.getCustomLaneRates();
    }

    public File getLaneRatesXMLFile(List<CustomLaneRate> customLaneRates) throws IOException {

        LaneRatesDTO laneRatesDTO = new LaneRatesDTO();
        laneRatesDTO.setCustomLaneRates(customLaneRates);
        XmlMapper xmlMapper = new XmlMapper();
        String xmlData = xmlMapper.writeValueAsString(laneRatesDTO);

        File file = new File("lane_rates_file.xml");

        FileWriter writer = new FileWriter(file);
        writer.write(xmlData);
        writer.close();
        return file;
    }


}
