package be.iccbxl.pid.reservationsSpringboot.model;

import jakarta.persistence.*;
import lombok.Data;


import java.util.List;

@Data
@Entity
@Table(name = "localities")
public class Localities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "postal_code")
    private String postalCode;

    private String locality;

    @OneToMany(mappedBy = "localityId")
    private List<Locations> locations;

    // Constructeurs, getters et setters

    // Constructeur par défaut
    public Localities() {
    }

    // Constructeur avec tous les champs
    public Localities(Long id, String postalCode, String locality, List<Locations> locations) {
        this.id = id;
        this.postalCode = postalCode;
        this.locality = locality;
        this.locations = locations;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public List<Locations> getLocations() {
        return locations;
    }

    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }
}
