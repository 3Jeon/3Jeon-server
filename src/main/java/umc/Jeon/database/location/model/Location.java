package umc.Jeon.database.location.model;

import lombok.*;
import umc.Jeon.database.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "Location")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private long userId;
    @Column(nullable = false)
    private double lat;
    @Column(nullable = false)
    private double lng;
    @Column(nullable = false)
    private String address;
    private boolean defaultAddress;

    @Builder
    public Location(long userId, double lat, double lng, String address, boolean defaultAddress, boolean status){
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.defaultAddress = defaultAddress;
        this.status = status;
    }
}
