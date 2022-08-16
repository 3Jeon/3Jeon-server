package umc.Jeon.naver_map.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class GeoStats {
    private String roadAddress;
    private String jibunAddress;
    private Double distance;
    private Double x;
    private Double y;
}
