package umc.Jeon.crawling.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BRestaurantDetail {
    Long Shop_No;
    String Shop_Nm;
    String Vel_No;
    List<String> images;
    Double Loc_Pnt_Lat;
    Double Loc_Pnt_Lng;
    String Business_Location;
    Double Star_Pnt_Avg;
    Long Review_Cnt;
    Long Favorite_Cnt;
    String orderCountText;
    Long Min_Ord_Price;
    Boolean deliveryInOperation;
    Boolean useDelivery;
    Boolean useTakeout;
    Long takeoutDiscountPrice;
    Long takeoutDiscountRate;
    String Ct_Cd_Nm;
    String Ct_Cd_Nm_En;
    String Dlvry_Tm;
    String shopStatus;
}
