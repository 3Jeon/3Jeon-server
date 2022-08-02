package umc.Jeon.crawling.coupang.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CRestaurant {
    Long id;
    String name;
    String telNo;
    String description;
    Double latitude;
    Double longitude;
    String address;
    List<String> images;
    Double reviewRating;
    Long reviewCount;
    String openStatus;
    Double minimumOrderPriceForDelivery;
    String estimatedDeliveryTime;
    String pickupEstimationFoodTimeText;
    Boolean pickupAvailable;
    Boolean supportedPickup;
}
