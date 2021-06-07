package aviasales.task.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class FlightDto {
    private final Date departureTime;
    private final Date arrivalTime;
    private final String number;
}
