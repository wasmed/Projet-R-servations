package be.iccbxl.pid.reservationsSpringboot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="shows")
public class Show {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    private String slug;

    private String title;

    @Column(name="poster_url")
    private String poster_url;

    private boolean bookable;

    private double price;
    private String description;

    @Column(name="created_at")
    private LocalDateTime created_at;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "shows")
    private List<ArtisteType> artistTypes = new ArrayList<>();


    public Show() { }

    public Show(String title, String description,String slug, String posterUrl, boolean bookable,
                double price) {

        this.slug=slug;
        this.title = title;
        this.description = description;
        this.poster_url = posterUrl;

        this.bookable = bookable;
        this.price = price;
        this.created_at = LocalDateTime.now();
        this.updatedAt = null;
    }

}
