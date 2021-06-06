package aviasales.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FlightDto {
    private final String departureTime;
    private final String arrivalTime;
    private final String number;
}
