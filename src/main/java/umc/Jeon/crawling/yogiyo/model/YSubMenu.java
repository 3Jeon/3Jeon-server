package umc.Jeon.crawling.yogiyo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class YSubMenu {
    boolean multiple;
    String name;
    long multiple_count;
    List<YSelectMenu> selectMenus;
    boolean mandatory;
    String slug;
}
