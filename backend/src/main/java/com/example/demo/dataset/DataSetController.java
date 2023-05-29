package com.example.demo.dataset;

import com.example.demo.dto.DataSetDTO;
import com.example.demo.user.User;
import com.example.demo.user.UserService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/datasets")
@RequiredArgsConstructor
public class DataSetController {

    private final DataSetService dataSetService;
    private final ResourceLoader resourceLoader;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<DataSetDTO>> getAllDatasets(Principal principal) {
        User user = userService.getUserDetails(principal.getName());
        List<DataSet> datasets = dataSetService.getAllDatasets();
        List<DataSetDTO> datasetDTOs = datasets.stream()
                .map(dataset -> dataSetService.convertToDTO(dataset, user))
                .collect(Collectors.toList());
        return new ResponseEntity<>(datasetDTOs, HttpStatus.OK);
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
