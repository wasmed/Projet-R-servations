package be.iccbxl.pid.reservationsSpringboot.repository;

import be.iccbxl.pid.reservationsSpringboot.model.ArtisteType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtisteTypeRepository extends CrudRepository<ArtisteType, Long> {
    List<ArtisteType> findByArtistId(Long artistId);
}
