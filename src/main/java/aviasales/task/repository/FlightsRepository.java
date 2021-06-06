package aviasales.task.repository;

import aviasales.task.model.db.Flight;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FlightsRepository extends CrudRepository<Flight, String> {

}
