package be.iccbxl.pid.reservationsSpringboot.model;

import com.github.slugify.Slugify;
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

    @ManyToOne
    @JoinColumn(name="location_id", nullable=true)
    private Location location;

    @OneToMany(targetEntity=Representation.class, mappedBy="show")
    private List<Representation> representations = new ArrayList<>();

    @ManyToMany(mappedBy = "shows")
    private List<ArtisteType> artistTypes = new ArrayList<>();


    public Show() { }

    public Show(String title, String description,String slug, String posterUrl, boolean bookable,
                double price) {
        Slugify slg = new Slugify();
        this.slug = slg.slugify(title);
        this.title = title;
        this.description = description;
        this.poster_url = posterUrl;

        this.bookable = bookable;
        this.price = price;
        this.created_at = LocalDateTime.now();
        this.updatedAt = null;
    }
    public void setLocation(Location location) {
        this.location.removeShow(this);		//déménager de l’ancien lieu
        this.location = location;
        this.location.addShow(this);		//emménager dans le nouveau lieu
    }
    public Show addArtistType(ArtisteType artistType) {
        if(!this.artistTypes.contains(artistType)) {
            this.artistTypes.add(artistType);
            artistType.addShow(this);
        }

        return this;
    }

    public Show removeArtistType(ArtisteType artistType) {
        if(this.artistTypes.contains(artistType)) {
            this.artistTypes.remove(artistType);
            artistType.getShows().remove(this);
        }

        return this;
    }
    public Show addRepresentation(Representation representation) {
        if(!this.representations.contains(representation)) {
            this.representations.add(representation);
            representation.setShow(this);
        }

        return this;
    }

    public Show removeRepresentation(Representation representation) {
        if(this.representations.contains(representation)) {
            this.representations.remove(representation);
            if(representation.getLocation().equals(this)) {
                representation.setLocation(null);
            }
        }

        return this;
    }

}
