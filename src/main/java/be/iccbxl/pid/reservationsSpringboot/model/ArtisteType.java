package be.iccbxl.pid.reservationsSpringboot.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="artist_type")
public class ArtisteType {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable=false)
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable=false)
    private Type type;

    @ManyToMany(targetEntity=Show.class)
    @JoinTable(
            name = "artist_type_show",
            joinColumns = @JoinColumn(name = "artist_type_id"),
            inverseJoinColumns = @JoinColumn(name = "show_id"))
    private List<Show> shows = new ArrayList<>();

    protected ArtisteType() { }

    public ArtisteType(Artist artist, Type type, List<Show> shows) {
        this.artist = artist;
        this.type = type;
        this.shows = shows;
    }


    public List<Show> getShows() {
        return shows;
    }

    public ArtisteType addShow(Show show) {
        if(!this.shows.contains(show)) {
            this.shows.add(show);
            show.addArtistType(this);
        }

        return this;
    }

    public ArtisteType removeShow(Show show) {
        if(this.shows.contains(show)) {
            this.shows.remove(show);
            show.getArtistTypes().remove(this);
        }

        return this;
    }

    @Override
    public String toString() {
        return "ArtistType [id=" + id + ", artist=" + artist + ", type=" + type
                + ", shows=" + shows + "]";
    }

}
