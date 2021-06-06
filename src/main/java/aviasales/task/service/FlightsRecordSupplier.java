package aviasales.task.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Service
public class FlightsRecordSupplier {
    private final String filename;

    public FlightsRecordSupplier(@Value("${flights.records.file.path}") String filename) {
        this.filename = filename;
    }

    public Iterable<CSVRecord> fetch() {
        try {
            Reader reader = new FileReader(filename);
            return CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read records from file", e);
        }
    }
}
