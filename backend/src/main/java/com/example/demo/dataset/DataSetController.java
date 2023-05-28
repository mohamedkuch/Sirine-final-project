package com.example.demo.dataset;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/datasets")
@RequiredArgsConstructor
public class DataSetController {

    private final DataSetService dataSetService;
    private final ResourceLoader resourceLoader;

    @GetMapping
    public ResponseEntity<List<DataSet>> getAllDatasets() {
        List<DataSet> dataSets = dataSetService.getAllDatasets();
        return new ResponseEntity<>(dataSets, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Map<String, String>>> getDataSet(@PathVariable Long id) throws IOException {
        DataSet dataSet = dataSetService.getDataSetById(id);
        // Loading classpath resource
        Resource resource = resourceLoader.getResource("classpath:data/" + dataSet.getFileName());

        List<Map<String, String>> csvDataList = dataSetService.readCSV(resource);

        return ResponseEntity.ok(csvDataList);
    }
}
