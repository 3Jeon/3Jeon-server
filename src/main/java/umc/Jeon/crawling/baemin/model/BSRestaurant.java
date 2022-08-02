package umc.Jeon.crawling.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BSRestaurant {
    Long shopNumber;
    String shopName;
    String telNumber;
    String address;
    String logoUrl;
    String closeDayText;
    String virtualTelNumber;
    Boolean inOperation;
    Long minimumOrderPrice;
    Double averageStarScore;
    List<Long> deliveryTip;
}
