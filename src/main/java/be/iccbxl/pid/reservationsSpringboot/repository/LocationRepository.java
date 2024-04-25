package be.iccbxl.pid.reservationsSpringboot.repository;

import be.iccbxl.pid.reservationsSpringboot.model.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Location findByDesignation(String designation);
    Optional<Location> findById(Long id);
}
