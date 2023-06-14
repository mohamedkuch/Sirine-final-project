//package Data;
//
//import com.example.demo.csv1.CsvService;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//@Service
//
//public class DAtensatz {
//    @Autowired
//    private CsvService csvService ;
//
//    @PostConstruct
//    public void senddata() throws IOException {
//        csvService.readAndSaveCsv(getFileFromResourceAsStream("Files/ArbeitsLose22.csv"));
//
//    }
//    private InputStream getFileFromResourceAsStream(String fileName) {
//
//        // The class loader that loaded the class
//        ClassLoader classLoader = getClass().getClassLoader();
//        InputStream inputStream = classLoader.getResourceAsStream(fileName);
//
//        // the stream holding the file content
//        if (inputStream == null) {
//            throw new IllegalArgumentException("file not found! " + fileName);
//        } else {
//            return inputStream;
//        }
//
//    }
//}
