package umc.Jeon.crawling.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BRestaurantInfo {
    BRestaurantDetail bRestaurantDetail;
    List<BMenu> bMenus;
//    List<BGroupMenu> bGroupMenus; // 20220804 아크 요청으로 삭제
}
