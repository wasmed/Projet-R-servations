package be.iccbxl.pid.reservationsSpringboot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name="prices")
public class Price {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "The type must not be null.")
    @Size(min=2, max=30, message = "The type must be between 2 and 30 characters long.")
    private String type;

    @NotNull(message = "The price must not be null.")
    private int price;

    @ManyToOne
    @JoinColumn(name="show_id", nullable=false)
    private Show show;



    public Price() {}

    public Price(String type, int price, Show show) {
        this.type = type;
        this.price = price;
        this.show = show;
    }


}
