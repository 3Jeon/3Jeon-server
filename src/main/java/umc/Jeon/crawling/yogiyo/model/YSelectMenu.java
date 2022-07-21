package umc.Jeon.crawling.yogiyo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class YSelectMenu {
    String slug;
    String description;
    long price;
    long id;
    boolean soldout;
    String name;
}
