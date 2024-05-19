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

    @Column(unique=true)
    private String slug;

    private String title;

    @Column(name="poster_url")
    private String posterUrl;

    private boolean bookable;
    private double price;
    private String description;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="location_id", nullable=true)
    private Location location;

    @OneToMany(targetEntity=Representation.class, mappedBy="show")
    private List<Representation> representations = new ArrayList<>();

    @ManyToMany(mappedBy = "shows")
    private List<ArtisteType> artistTypes = new ArrayList<>();

    @OneToMany(targetEntity=Price.class, mappedBy="show")
    private List<Price> prices = new ArrayList<>();

    public Show() { }

    public Show(String title, String description,String posterUrl,Location location, boolean bookable,
                double price) {
        Slugify slg = new Slugify();
        this.slug = slg.slugify(title);
        this.title = title;
        this.description = description;
        this.posterUrl = posterUrl;
        this.location = location;
        this.bookable = bookable;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }
    public void setTitle(String title) {
        this.title = title;

        Slugify slg = new Slugify();

        this.setSlug(slg.slugify(title));
    }

    public boolean isBookable() {
        return bookable;
    }

    public void setBookable(boolean bookable) {
        this.bookable = bookable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Representation> getRepresentations() {
        return representations;
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

    @Transient
    public List<Artist> getAuthors() {
        List<Artist> authors = new ArrayList<>();

        for (ArtisteType artistType : artistTypes) {
            if (artistType.getType().getType().equals("scénographe")) {
                authors.add(artistType.getArtist());
            }
        }

        return authors;
    }

}
