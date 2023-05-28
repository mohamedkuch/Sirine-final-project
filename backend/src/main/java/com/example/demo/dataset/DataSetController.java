package com.example.demo.dataset;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/datasets")
@RequiredArgsConstructor
public class DataSetController {

    private final DataSetService dataSetService;
    private final ResourceLoader resourceLoader;


    @GetMapping("/{id}")
    public ResponseEntity<List<Map<String, String>>> getDataSet(@PathVariable Long id) throws IOException {
        DataSet dataSet = dataSetService.getDataSetById(id);
        // Loading classpath resource
        Resource resource = resourceLoader.getResource("classpath:data/" + dataSet.getFileName());

        // Reading first line to determine delimiter
        InputStream inputStream = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String firstLine = reader.readLine();
        reader.close();

        CsvSchema schema;
        if (firstLine != null) {
            char delimiter = firstLine.indexOf(';') > firstLine.indexOf(',') ? ';' : ',';
            schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(delimiter);
        } else {
            throw new RuntimeException("CSV file is empty");
        }

        CsvMapper mapper = new CsvMapper();
        MappingIterator<Map<String, String>> iterator = mapper.readerFor(Map.class)
                .with(schema)
                .readValues(resource.getInputStream());

        List<Map<String, String>> csvDataList = iterator.readAll();

        return ResponseEntity.ok(csvDataList);
    }
}
