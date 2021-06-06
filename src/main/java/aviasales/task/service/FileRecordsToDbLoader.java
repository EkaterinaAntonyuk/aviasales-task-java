package aviasales.task.service;

import aviasales.task.model.db.Flight;
import aviasales.task.repository.FlightsRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
public class FileRecordsToDbLoader {
    private final FlightsRepository flightRepository;
    private final FlightsRecordSupplier flightsRecordSupplier;

    public FileRecordsToDbLoader(FlightsRepository flightRepository, FlightsRecordSupplier flightsRecordSupplier) {
        this.flightRepository = flightRepository;
        this.flightsRecordSupplier = flightsRecordSupplier;
    }

    @PostConstruct
    public void loadDataToDB() {
        // TODO test
        Iterable<CSVRecord> records = flightsRecordSupplier.fetch();
        for (CSVRecord record : records) {
            flightRepository.save(new Flight(
                    record.get("Id"),
                    record.get("Origin"),
                    record.get("Destination"),
                    record.get("DepartureDate"),
                    record.get("DepartureTime"),
                    record.get("ArrivalDate"),
                    record.get("ArrivalTime"),
                    record.get("Number")
            ));
        }
    }
}
