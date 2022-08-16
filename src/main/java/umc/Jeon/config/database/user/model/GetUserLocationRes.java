package umc.Jeon.config.database.user.model;

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
