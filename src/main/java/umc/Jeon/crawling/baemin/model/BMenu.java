package umc.Jeon.crawling.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BMenu {
    long menuId;
    String name;
    String description;
    List<String> images;
    String price;
    boolean soldOut;
}
