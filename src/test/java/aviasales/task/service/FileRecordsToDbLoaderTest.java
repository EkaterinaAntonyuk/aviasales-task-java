package aviasales.task.service;

import aviasales.task.model.db.Flight;
import aviasales.task.repository.FlightsRepository;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FileRecordsToDbLoaderTest {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");

    @InjectMocks
    FileRecordsToDbLoader fileRecordsToDbLoader;
    @Mock
    FlightsRecordSupplier flightsRecordSupplier;
    @Mock
    FlightsRepository flightRepository;
    @Captor
    ArgumentCaptor<Flight> flightCaptor;

    @Test
    public void testLoadDataToDB() throws ParseException {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
        ArrayList<CSVRecord> records = new ArrayList<>();
        records.add(createCSVRecord("1",
                "SVO",
                "BKK",
                "20210701",
                "2010",
                "20210702",
                "1115",
                "SU-275"));
        records.add(createCSVRecord("2",
                "SVO",
                "BKK",
                "20210702",
                "2010",
                "20210703",
                "1115",
                "SU-275"));
        when(flightsRecordSupplier.fetch()).thenReturn(records);

        fileRecordsToDbLoader.loadDataToDB();
        verify(flightRepository, times(2)).save(flightCaptor.capture());
        List<Flight> flights = flightCaptor.getAllValues();

        Flight firstFlight = flights.get(0);
        assertEquals(firstFlight.getId(), "1");
        assertEquals(firstFlight.getOrigin(), "SVO");
        assertEquals(firstFlight.getDestination(), "BKK");
        assertEquals(firstFlight.getDepartureTime(), DATE_FORMAT.parse("202107012010"));
        assertEquals(firstFlight.getArrivalTime(), DATE_FORMAT.parse("202107021115"));
        assertEquals(firstFlight.getNumber(), "SU-275");

        Flight secondFlight = flights.get(1);
        assertEquals(secondFlight.getId(), "2");
        assertEquals(secondFlight.getOrigin(), "SVO");
        assertEquals(secondFlight.getDestination(), "BKK");
        assertEquals(firstFlight.getDestination(), "BKK");
        assertEquals(secondFlight.getDepartureTime(), DATE_FORMAT.parse("202107022010"));
        assertEquals(secondFlight.getArrivalTime(), DATE_FORMAT.parse("202107031115"));
        assertEquals(secondFlight.getNumber(), "SU-275");
    }

    private CSVRecord createCSVRecord(String id, String origin, String destination, String departureDate,
                                      String departureTime, String arrivalDate, String arrivalTime, String number) {
        CSVRecord csvRecord = mock(CSVRecord.class);
        when(csvRecord.get(eq("Id"))).thenReturn(id);
        when(csvRecord.get(eq("Origin"))).thenReturn(origin);
        when(csvRecord.get(eq("Destination"))).thenReturn(destination);
        when(csvRecord.get(eq("DepartureDate"))).thenReturn(departureDate);
        when(csvRecord.get(eq("DepartureTime"))).thenReturn(departureTime);
        when(csvRecord.get(eq("ArrivalDate"))).thenReturn(arrivalDate);
        when(csvRecord.get(eq("ArrivalTime"))).thenReturn(arrivalTime);
        when(csvRecord.get(eq("Number"))).thenReturn(number);
        return csvRecord;
    }
}
