package umc.Jeon.crawling.HashValues;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HashValues {
    // 카테고리
    public static HashMap<String, String> YOGIYO_CATEGORY;
    public static HashMap<String, String> BAEMIN_CATEGORY;
    public static HashMap<String, String> COUPANG_CATEGORY;
    // 정렬
    public static HashMap<String, String> YOGIYO_SORT;
    public static HashMap<String, String> BAEMIN_SORT;
    public static HashMap<String, String> COUPANG_SORT;

    @PostConstruct
    private void setHashValues() {
        // 카테고리 정의
        List<String> main_category = new ArrayList<>(Arrays.asList(
                "1인분", "한식", "일식", "중식",
                "양식", "치킨", "분식", "고기구이",
                "도시락", "야식", "패스트푸드", "디저트",
                "아시안푸드"
        ));
        List<String> yogiyo_category = new ArrayList<>(Arrays.asList(

        ));
        List<String> baemin_category = new ArrayList<>(Arrays.asList(

        ));
        List<String> coupang_category = new ArrayList<>(Arrays.asList(

        ));

        // 정렬 정의
        List<String> main_sort = new ArrayList<>(Arrays.asList(
                "rank", "review_avg", "review_count", "distance"
        ));
        List<String> yogiyo_sort = new ArrayList<>(Arrays.asList(
                "rank", "review_avg", "review_count", "distance"
        ));
        List<String> baemin_sort = new ArrayList<>(Arrays.asList(
                "SORT__DEFAULT", "SORT__STAR", "SORT__ORDER", "SORT__DISTANCE"
        ));
        List<String> coupang_sort = new ArrayList<>(Arrays.asList(
                "score", "highestRate", "manyOrder", "nearby"
        ));
        YOGIYO_SORT = setHashMap(main_sort, yogiyo_sort);
        BAEMIN_SORT = setHashMap(main_sort, baemin_sort);
        COUPANG_SORT = setHashMap(main_sort, coupang_sort);
    }

    private HashMap<String, String> setHashMap(List<String> main, List<String> target){
        HashMap<String, String> ret = new HashMap<>();
        for (int i=0; i<main.size(); i++)
            ret.put(main.get(i), target.get(i));
        return ret;
    }
}
