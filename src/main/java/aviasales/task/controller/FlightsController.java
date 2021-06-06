package aviasales.task.controller;

import aviasales.task.exceptions.FlightNotFoundException;
import aviasales.task.model.dto.FlightDto;
import aviasales.task.service.FlightsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
public class FlightsController {
    private final FlightsService flightsService;

    public FlightsController(FlightsService flightsService) {
        this.flightsService = flightsService;
    }

    @GetMapping("/{id}")
    public FlightDto getFlightById(@PathVariable String id) {
        return flightsService.getFlightById(id);
    }

    @ExceptionHandler(FlightNotFoundException.class)
    public ResponseEntity<String> flightNotFoundExceptionHandler(FlightNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
