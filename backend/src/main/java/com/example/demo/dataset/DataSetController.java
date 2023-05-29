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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
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

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Map<String, Object>>> getDataSet(@PathVariable Long id) throws IOException {
        DataSet dataSet = dataSetService.getDataSetById(id);
        Resource resource = resourceLoader.getResource("classpath:data/" + dataSet.getFileName());

        String filename = resource.getFilename();
        assert filename != null;
        Path path = Paths.get(filename);
        String fileExtension = dataSetService.getFileExtension(path);

        List<Map<String, Object>> data = new ArrayList<>();
        if ("csv".equalsIgnoreCase(fileExtension)) {
            List<Map<String, String>> result = dataSetService.readCSV(resource);
            for (Map<String, String> item : result) {
                Map<String, Object> temp = new HashMap<String, Object>(item);
                data.add(temp);
            }

        } else if ("xml".equalsIgnoreCase(fileExtension)) {
            boolean add = data.add(dataSetService.readXML(resource));
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + fileExtension);
        }

        return ResponseEntity.ok(data);
    }
}
