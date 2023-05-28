package com.example.demo.dataset;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataSetService {

    private final DataSetRepository dataSetRepository;

    private final static List<DataSet> DATA_SET_LIST = Arrays.asList(
            DataSet.builder().id(1L).name("Aachen Vornamen").fileName("aachenvornamen.csv").build(),
            DataSet.builder().id(2L).name("Arbeitslose erwerbsfaehige Leistungsberechtigte").fileName("ArbeitsLose22.csv").build()
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

}
