package umc.Jeon.crawling.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BRestaurant {
    long shopNumber;
    String shopName;
    List<String> thumbnails;
    boolean inOperation;
    double averageStarScore;
    List<Long> deliveryTips;
    List<Long> expectedDeliveryTimes;
}
