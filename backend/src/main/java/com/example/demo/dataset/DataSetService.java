package com.example.demo.dataset;

import com.example.demo.dto.DataSetDTO;
import com.example.demo.user.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataSetService {

    private final DataSetRepository dataSetRepository;

    private final static List<DataSet> DATA_SET_LIST = Arrays.asList(
            DataSet.builder().id(1L).name("Vornamen von Neugeborenen in der Stadt Aachen 2021")
                    .fileName("aachenvornamen.csv").build(),
            DataSet.builder().id(2L).name("Anzahl der Arbeitssuchenden in der Städteregion Aachen")
                    .fileName("ArbeitsSuchendeAachen.csv").build(),
            DataSet.builder().id(3L).name("Anzahl der Arbeitslosen in der Städteregion Aachen")
                    .fileName("ArbeitsLose22.csv").build(),
            DataSet.builder().id(4L).name("Straßenliste Stadt Aachen")
                    .fileName("strassennamen.csv").build(),
            DataSet.builder().id(5L).name("Sterbefälle der Stadt Aachen nach Monat, 2015-2021")
                    .fileName("sterbefalle-monatlich-2015-2022.csv").build(),
            DataSet.builder().id(6L).name("Geburten der Stadt Aachen nach Monat, 2015-2021")
                    .fileName("geburten-monatlich-2015_2022.csv").build(),
            DataSet.builder().id(7L).name("Mittlere Jahresbevölkerung nach Geschlecht - kreisfreieStädte und Kreise")
                    .fileName("Mittlere_Jahresbevölkerung_nach_Geschlecht_kreisfreieStädte_und_Kreise.xml").build()

    );

    @PostConstruct
    public void initData() {
        dataSetRepository.saveAll(DATA_SET_LIST);
    }

    public DataSet getDataSetById(Long id) {
        // Assuming you have a repository to fetch DataSet
        return dataSetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("DataSet not found with id " + id));
    }

    public List<DataSet> getAllDatasets() {
        return dataSetRepository.findAll();
    }

    public DataSetDTO convertToDTO(DataSet dataset, User user) {
        DataSetDTO datasetDTO = new DataSetDTO();
        datasetDTO.setId(dataset.getId());
        datasetDTO.setName(dataset.getName());
        datasetDTO.setFavorite(user.getFavoriteDatasets().contains(dataset));
        return datasetDTO;
    }

    public List<Map<String, String>> readCSV(Resource resource) throws IOException {

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

        return iterator.readAll();
    }

    public  Map<String, Object> readXML(Resource resource) throws IOException {
        // Create xml mapper
        XmlMapper xmlMapper = new XmlMapper();

        // Read XML
        JsonNode node = xmlMapper.readTree(resource.getInputStream());

        // Convert to JSON
        ObjectMapper jsonMapper = new ObjectMapper();

        // Convert JsonNode to JSON string
        String jsonString = jsonMapper.writeValueAsString(node);

        // Convert JSON string to Map
        Map<String, Object> resultMap = jsonMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {});

        return resultMap;

    }

    public String getFileExtension(Path path) {
        String filename = path.getFileName().toString();
        int dotIndex = filename.lastIndexOf('.');
        if(dotIndex > 0 && dotIndex < filename.length() - 2) {
            return filename.substring(dotIndex + 1);
        }
        return "";
    }
}
