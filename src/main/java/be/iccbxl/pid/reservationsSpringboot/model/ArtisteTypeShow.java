package be.iccbxl.pid.reservationsSpringboot.model;

import jakarta.persistence.*;

@Entity
@Table(name="artists_type_show")
public class ArtisteTypeShow {



    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id", nullable=false)
    private Show show;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artiste_type_id", nullable=false)
    private ArtisteType artisteType;
}
