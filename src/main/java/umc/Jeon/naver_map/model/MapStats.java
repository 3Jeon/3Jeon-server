package umc.Jeon.naver_map.model;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class MapStats {
    private List<Double> start;
    private List<Double> goal;
    private Date currentDateTime;
    private Date departureTime;
    private long distance;
    private long duration;
    private long tollFare;
    private long taxiFare;
    private long fuelPrice;
}
