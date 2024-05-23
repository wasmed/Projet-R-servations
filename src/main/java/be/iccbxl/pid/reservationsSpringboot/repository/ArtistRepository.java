package be.iccbxl.pid.reservationsSpringboot.repository;


import be.iccbxl.pid.reservationsSpringboot.model.Artist;
import be.iccbxl.pid.reservationsSpringboot.model.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
      List<Artist> findByLastname(String lastname);
    List<Artist> findByTypesContains(Type type);
    Artist findById(long id);
}

