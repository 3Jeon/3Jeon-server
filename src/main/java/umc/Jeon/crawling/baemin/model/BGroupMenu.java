package umc.Jeon.crawling.baemin.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BGroupMenu {
    Long menuGroupId;
    String name;
    String description;
    List<BMenu> bMenus;
}
