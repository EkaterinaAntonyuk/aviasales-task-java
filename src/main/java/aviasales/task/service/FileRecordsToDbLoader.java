package aviasales.task.service;

import aviasales.task.model.db.Flight;
import aviasales.task.repository.FlightsRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.TimeZone;

@Service
@Slf4j
public class FileRecordsToDbLoader {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");

    static {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
    }

    private final FlightsRepository flightRepository;
    private final FlightsRecordSupplier flightsRecordSupplier;

    public FileRecordsToDbLoader(FlightsRepository flightRepository, FlightsRecordSupplier flightsRecordSupplier) {
        this.flightRepository = flightRepository;
        this.flightsRecordSupplier = flightsRecordSupplier;
    }

    @SneakyThrows
    @PostConstruct
    public void loadDataToDB() {
        Iterable<CSVRecord> records = flightsRecordSupplier.fetch();
        for (CSVRecord record : records) {
            flightRepository.save(new Flight(
                    record.get("Id"),
                    record.get("Origin"),
                    record.get("Destination"),
                    DATE_FORMAT.parse(record.get("DepartureDate") + record.get("DepartureTime")),
                    DATE_FORMAT.parse(record.get("ArrivalDate") + record.get("ArrivalTime")),
                    record.get("Number")
            ));
        }
    }
}
