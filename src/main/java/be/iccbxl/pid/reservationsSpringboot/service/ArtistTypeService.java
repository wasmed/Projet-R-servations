package be.iccbxl.pid.reservationsSpringboot.service;

import be.iccbxl.pid.reservationsSpringboot.model.Artist;
import be.iccbxl.pid.reservationsSpringboot.model.ArtisteType;
import be.iccbxl.pid.reservationsSpringboot.model.Type;
import be.iccbxl.pid.reservationsSpringboot.repository.ArtistRepository;
import be.iccbxl.pid.reservationsSpringboot.repository.ArtisteTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ArtistTypeService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ArtisteTypeRepository repository;

    public ArtisteType findByArtistAndType(Artist artist, Type type) {

      return   repository.findByArtistAndType(artist,type);
    }
}
