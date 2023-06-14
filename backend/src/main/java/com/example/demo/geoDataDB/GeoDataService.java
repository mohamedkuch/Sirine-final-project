package com.example.demo.geoDataDB;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GeoDataService {

    public boolean saveNewGeoData(MultipartFile file){
        String filename = file.getOriginalFilename();

        try {
            String assetsFolderPath = Paths.get(".").toAbsolutePath().normalize().toString() + "/src/main/java/com/example/demo/geoDataDB/assets/";

            GeoData newGeoData = new GeoData();
            newGeoData.setFilename(filename);
//          TODO: Hinzufügen namens String


            BufferedWriter writer = new BufferedWriter(new FileWriter(assetsFolderPath + newGeoData.getFilename()));
            System.out.println("Datei erstellt");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
