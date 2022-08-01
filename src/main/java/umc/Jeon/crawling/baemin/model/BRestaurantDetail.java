package umc.Jeon.crawling.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BRestaurantDetail {
    long Shop_No;
    String Shop_Nm;
    String Vel_No;
    List<String> images;
    double Loc_Pnt_Lat;
    double Loc_Pnt_Lng;
    String Business_Location;
    double Star_Pnt_Avg;
    long Review_Cnt;
    long Favorite_Cnt;
    String orderCountText;
    long Min_Ord_Price;
    boolean deliveryInOperation;
    boolean useDelivery;
    boolean useTakeout;
    long takeoutDiscountPrice;
    long takeoutDiscountRate;
    String Ct_Cd_Nm;
    String Ct_Cd_Nm_En;
    String Dlvry_Tm;
    String shopStatus;
}
