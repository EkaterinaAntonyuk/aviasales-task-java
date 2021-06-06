package aviasales.task.service;

import aviasales.task.exceptions.FlightNotFoundException;
import aviasales.task.model.db.Flight;
import aviasales.task.model.dto.FlightDto;
import aviasales.task.repository.FlightsRepository;
import org.springframework.stereotype.Service;

@Service
public class FlightsService {
    private final FlightsRepository flightRepository;

    public FlightsService(FlightsRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightDto getFlightById(String id) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException(id));
        return new FlightDto(flight.getDepartureTime(), flight.getArrivalTime(),
                flight.getNumber());
    }
}
