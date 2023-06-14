package com.example.demo.dataset;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataSetRepository extends JpaRepository<DataSet, Long> {

    Optional<DataSet> findById(Long id);
    Optional<DataSet> findByFileName(String fileName);
}
