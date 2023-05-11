package com.example.demo.csv;


import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/csv-data")
//@CrossOrigin(origins = "http://localhost:4200")
public class CSVController {

    @Autowired
    private CsvService csvService ;

    @GetMapping
    public List<CsvData> getAllCsvData() {
        return csvService.getAllCsvData();
    }
    @PostMapping("/import")
    public void importCsvData() {
        try {
            csvService.importCsvData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
