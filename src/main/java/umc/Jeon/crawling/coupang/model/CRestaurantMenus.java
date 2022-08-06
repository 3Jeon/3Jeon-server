package umc.Jeon.crawling.coupang.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CRestaurantMenus {
    Long id;
    String name;
    String telNo;
    String description;
    Double latitude;
    Double longitude;
    String pickupAddress;
    List<String> images;
    Double reviewRating;
    Long reviewCount;
    String openStatus;
    Double minimumOrderPriceForDelivery;
    String estimatedDeliveryTime;
    boolean pickupAvailable;
    boolean supportedPickup;
    String estimatedPickupTime;
//    List<CMenu> Menus;
    List<CDish> dishes;
}
