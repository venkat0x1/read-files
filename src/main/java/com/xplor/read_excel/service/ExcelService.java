package com.xplor.read_excel.service;

import com.xplor.read_excel.entity.CustomLaneRate;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ExcelService {

    Map<String, Integer> headerNamesAndIndexes = new HashMap<>();


    public List<CustomLaneRate> readExcelSLSXFileAndSaveData(MultipartFile file) throws IOException {

        if (!Objects.requireNonNull(file.getOriginalFilename()).endsWith(".xlsx")) {
            throw new IllegalArgumentException("Please upload a .xlsx file");
        }

        Workbook workbook = new XSSFWorkbook(file.getInputStream());

        Sheet sheet = workbook.getSheetAt(0);

        List<CustomLaneRate> customLaneRates = new ArrayList<>();

        for (Row row : sheet) {

            // skip the first row as it is the header
            if (row.getRowNum() == 0) {
                System.out.println("storing headers and indexes");
                validateAndStoreHeaderNamesAndIndexes(row);
                continue;
            }

            System.out.println("stored the data");

            CustomLaneRate customLaneRate = CustomLaneRate.builder()
                    .originCity(row.getCell(headerNamesAndIndexes.get("origin city")).getStringCellValue())
                    .originState(row.getCell(headerNamesAndIndexes.get("origin state")).getStringCellValue())
                    .destinationCity(row.getCell(headerNamesAndIndexes.get("destination city")).getStringCellValue())
                    .destinationState(row.getCell(headerNamesAndIndexes.get("destination state")).getStringCellValue())
                    .rate(row.getCell(headerNamesAndIndexes.get("rate")).getNumericCellValue())
                    .build();

            customLaneRates.add(customLaneRate);
        }

        return customLaneRates;

    }

    private Set<String> customRatesFileHeaderNames = Set.of("origin city", "origin state", "destination city", "destination state", "rate");

    private boolean isValidHeaderNames(String headerName) {
        return customRatesFileHeaderNames.contains(headerName);
    }

    private void validateAndStoreHeaderNamesAndIndexes(Row row) {


        for (Cell cell : row) {

            String headerName = cell.getStringCellValue();
            if (headerName == null || headerName.isEmpty() || !isValidHeaderNames(headerName)) {
                throw new IllegalArgumentException("Header names are not valid");
            }

            switch (headerName) {
                case "origin city":
                    headerNamesAndIndexes.put( "origin city", cell.getColumnIndex());
                    break;
                case "origin state":
                    headerNamesAndIndexes.put("origin state",cell.getColumnIndex());
                    break;
                case "destination city":
                    headerNamesAndIndexes.put( "destination city",cell.getColumnIndex());
                    break;
                case "destination state":
                    headerNamesAndIndexes.put("destination state",cell.getColumnIndex());
                    break;
                case "rate":
                    headerNamesAndIndexes.put("rate", cell.getColumnIndex());
                    break;
            }
        }
    }


}
