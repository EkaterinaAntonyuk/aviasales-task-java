package aviasales.task.exceptions;

public class FlightNotFoundException extends RuntimeException {
    public FlightNotFoundException(String id) {
        super("Bad flight id: " + id);
    }
}
