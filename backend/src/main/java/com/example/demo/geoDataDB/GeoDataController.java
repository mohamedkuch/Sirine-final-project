package com.example.demo.geoDataDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/geoData")
@CrossOrigin(origins = "http://localhost:4200")
public class GeoDataController {

    @Autowired
    private GeoDataService geoDataService;

    //TODO: Namen mitgeben
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) {
        if (geoDataService.saveNewGeoData(file)) {
            return ResponseEntity.ok("angekommen");
        } else {
            return ResponseEntity.badRequest().body("Failure");
        }
    }

}
