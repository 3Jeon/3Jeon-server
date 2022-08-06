package umc.Jeon.crawling.coupang.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CMenu {
    Long id;
    Long storeId;
    String name;
    String description;
    List<CDish> Dishes;
}
