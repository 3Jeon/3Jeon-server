package umc.Jeon.config.database.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUserLocationReq {
    double lat;
    double lng;
    String address;
}
