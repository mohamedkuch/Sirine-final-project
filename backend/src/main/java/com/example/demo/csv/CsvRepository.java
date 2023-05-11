package com.example.demo.csv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CsvRepository extends JpaRepository<CsvData,Long> {
    List<CsvData> findAll();
}
