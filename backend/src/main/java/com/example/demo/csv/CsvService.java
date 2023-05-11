/*TODO: Schnittstelle funktioniert, im frontend werden jedoch keine Daten angezeigt
 könnte auch am Frontend liegen, Fehler noch unbekannt*/

package com.example.demo.csv;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class CsvService {
    @Autowired
    private CsvRepository repository;

    public void importCsvData() throws IOException, CsvValidationException {
        ClassLoader classLoader = getClass().getClassLoader();
        String filename = "C:\\Users\\mefte\\IdeaProjects\\gruppe-i\\backend\\src\\main\\resources\\anzahl-der-arbeitslosen-in-der-stadteregion-aachen.csv" ;
        File file = new File(classLoader.getResource(filename).getFile());

        CSVReader csvReader = new CSVReader(new FileReader(file));
        String[] nextLine;

        while ((nextLine = csvReader.readNext()) != null) {
            CsvData csvData = new CsvData(nextLine[0], nextLine[1], nextLine[2]);
            repository.save(csvData);
        }
    }

    public List<CsvData> getAllCsvData() {
        return repository.findAll();
    }

//    public void printCsvDataToConsole() {
//        List<CsvData> dataList = repository.findAll();
//        for (CsvData data : dataList) {
//            System.out.println(data.getColumn1() + ", " + data.getColumn2() + ", " + data.getColumn3());
//        }
//    }



}
