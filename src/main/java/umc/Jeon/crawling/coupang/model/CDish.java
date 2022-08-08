package umc.Jeon.crawling.coupang.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CDish {
    Long id;
    Long storeId;
    String name;
    String description;
    Double salePrice;
    String displayStatusText;
    List<String> images;
}
