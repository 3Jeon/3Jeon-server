package umc.Jeon.crawling.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BSRestaurant {
    long shopNumber;
    String shopName;
    String telNumber;
    String address;
    String logoUrl;
    String closeDayText;
    String virtualTelNumber;
    boolean inOperation;
    long minimumOrderPrice;
    double averageStarScore;
    List<Long> deliveryTip;
}
