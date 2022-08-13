package umc.Jeon.database.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetUserLocationRes {
    private long userId;
    private double lat;
    private double lng;
    private String address;
}
