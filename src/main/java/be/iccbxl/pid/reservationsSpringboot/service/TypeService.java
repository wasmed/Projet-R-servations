package be.iccbxl.pid.reservationsSpringboot.service;

import be.iccbxl.pid.reservationsSpringboot.model.ArtisteType;
import be.iccbxl.pid.reservationsSpringboot.model.Type;
import be.iccbxl.pid.reservationsSpringboot.repository.ArtisteTypeRepository;
import be.iccbxl.pid.reservationsSpringboot.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TypeService {


    @Autowired
    private TypeRepository repository;

    @Autowired
    private ArtisteTypeRepository artistTypeRepository;


    public List<Type> getAll() {
        List<Type> types = new ArrayList<>();

        repository.findAll().forEach(types::add);

        return types;
    }

    public Type get(String id) {
        Long indice = (long) Integer.parseInt(id);

        Optional<Type> type = repository.findById(indice);

        return type.isPresent() ? type.get() : null;

    }

    public List<Type> getAllTypesForArtist(Long artistId) {

        List<ArtisteType> artistTypes = artistTypeRepository.findByArtistId(artistId);

        List<Type> types = new ArrayList<>();
        for (ArtisteType artistType : artistTypes) {
            types.add(artistType.getType());
        }
        return types;
    }

    public void add(Type type) {
        repository.save(type);
    }

    public void update(String id, Type type) {
        repository.save(type);
    }

    public void delete(String id) {
        Long indice = (long) Integer.parseInt(id);

        repository.deleteById(indice);
    }

    public Type getTypeByType(String typeName) {
        return repository.findByType(typeName); // Assuming your TypeRepository has this method
    }
}