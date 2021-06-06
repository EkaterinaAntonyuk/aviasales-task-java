package aviasales.task.service;

import aviasales.task.exceptions.FlightNotFoundException;
import aviasales.task.model.db.Flight;
import aviasales.task.model.dto.FlightDto;
import aviasales.task.repository.FlightsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FlightServiceTest {

    @InjectMocks
    FlightsService flightsService;

    @Mock
    FlightsRepository flightsRepository;

    @Test
    public void testGetFlightById() {
        Flight flight = new Flight(
                "10",
                "SVO",
                "BKK",
                "20210708",
                "2010",
                "20210709",
                "1115",
                "SU-275"
        );
        when(flightsRepository.findById("10")).thenReturn(Optional.of(flight));

        FlightDto expected = new FlightDto("2010",
                "1115",
                "SU-275");

        FlightDto actual = flightsService.getFlightById("10");

        assertEquals(expected.getArrivalTime(), actual.getArrivalTime());
        assertEquals(expected.getDepartureTime(), actual.getDepartureTime());
        assertEquals(expected.getNumber(), actual.getNumber());
    }

    @Test
    public void ifFlightWasNotFoundThenExceptionMustBeThrown() {
        when(flightsRepository.findById("20")).thenReturn(Optional.empty());

        assertThrows(FlightNotFoundException.class, () -> flightsService.getFlightById("20"));
    }
}
