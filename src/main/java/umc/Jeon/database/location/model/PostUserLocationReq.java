package umc.Jeon.database.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUserLocationReq {
    double lat;
    double lng;
    String address;
}
