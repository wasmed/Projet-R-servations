package be.iccbxl.pid.reservationsSpringboot.service;

import be.iccbxl.pid.reservationsSpringboot.model.Artist;
import be.iccbxl.pid.reservationsSpringboot.model.Location;
import be.iccbxl.pid.reservationsSpringboot.model.Show;
import be.iccbxl.pid.reservationsSpringboot.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository repository;


    public List<Show> getAll() {
        List<Show> shows = new ArrayList<>();

        repository.findAll().forEach(shows::add);

        return shows;
    }

    public Show get(String id) {
        Long indice = (long) Integer.parseInt(id);
        Optional<Show> show = repository.findById(indice);

        return show.isPresent() ? show.get() : null;
    }
    public Show get(long id) {
       return repository.findById(id);

    }
    public void add(Show show) {
        repository.save(show);
    }

    public void update(long id, Show show) {
        Show existingShow = repository.findById(id);
        existingShow.setBookable(show.isBookable());
        existingShow.setLocation(show.getLocation());
        existingShow.setTitle(show.getTitle());
        existingShow.setArtistTypes(show.getArtistTypes());
        existingShow.setDescription(show.getDescription());
        existingShow.setRepresentations(show.getRepresentations());
        existingShow.setPrice(show.getPrice());
        repository.save(show);
    }

    public void delete(long id) {
      //  Long indice = (long) Integer.parseInt(id);

        repository.deleteById(id);
    }

    public List<Show> getFromLocation(Location location) {
        return repository.findByLocation(location);
    }

    public long countShows() {

        return repository.count();
    }





}
