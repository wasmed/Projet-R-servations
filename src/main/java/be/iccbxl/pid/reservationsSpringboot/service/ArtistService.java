package be.iccbxl.pid.reservationsSpringboot.service;


import be.iccbxl.pid.reservationsSpringboot.model.Artist;
import be.iccbxl.pid.reservationsSpringboot.model.Type;
import be.iccbxl.pid.reservationsSpringboot.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    public List<Artist> getAllArtists() {
        List<Artist> artists = new ArrayList<>();

        artistRepository.findAll().forEach(artists::add);

        return artists;
    }

    public Artist getArtist(long id) {


        return artistRepository.findById(id);
    }
    public List<Artist> findByType(Type type) {
        return artistRepository.findByTypesContains(type);
    }
    public void addArtist(Artist artist) {
        artistRepository.save(artist);
    }

    public void updateArtist(long id, Artist artist) {
        artistRepository.save(artist);
    }

    public void deleteArtist(long id) {

        artistRepository.deleteById(id);
    }

    public long countArtists() {
        return artistRepository.count();
    }

    public List<Artist> findAllById(List<Long> ids) {
        Iterable<Artist> iterable = artistRepository.findAllById(ids);
        List<Artist> artists = new ArrayList<>();
        iterable.forEach(artists::add);
        return artists;
    }
}

