package be.iccbxl.pid.reservationsSpringboot.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="artiste_type")
public class ArtisteType {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable=false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable=false)
    private Type types;
}
