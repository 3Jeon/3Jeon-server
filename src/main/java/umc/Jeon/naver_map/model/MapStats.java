package umc.Jeon.naver_map.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@ToString
@Builder
@Getter
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