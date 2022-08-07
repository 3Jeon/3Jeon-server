package umc.Jeon.crawling.coupang.component;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import umc.Jeon.crawling.User.User;
import umc.Jeon.crawling.coupang.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static umc.Jeon.config.secret.Secret.X_EATS_APP_VERSION;

@Component
public class CoupangJsoup {

    // 주변 레스토랑 불러오기
    public List<CRestaurant> getCRestaurants(double lat, double lng, String category, String sort) {
        String url = "https://api.coupangeats.com/endpoint/store.get_clp";
        String curLocation = String.format("{ \"latitude\" : %s, \"longitude\" : %s }", lat, lng);
        Connection conn = Jsoup.connect(url)
                .header("X-EATS-OS-TYPE", "IOS")
                .header("X-EATS-DEVICE-ID", "com.coupang.coupang-eats")
                .header("X-EATS-PCID", "com.coupang.coupang-eats")
                .header("X-EATS-LOCALE", "ko")
                .header("X-EATS-LOCATION", curLocation)
                .header("X-EATS-APP-VERSION", X_EATS_APP_VERSION)
                .data("categoryId", category)
                .data("sort", sort)
                .timeout(10000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        List<CRestaurant> bRestaurants = new ArrayList<>();
        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            jsonObject = (JSONObject) jsonObject.get("data");
            JSONArray entityList = (JSONArray) jsonObject.get("entityList");

            List<CRestaurant> cRestaurants = new ArrayList<>();
            for (int i = 1; i < entityList.size(); i++) {
                JSONObject cur = (JSONObject) entityList.get(i);
                cRestaurants.add(getCRestaurant(cur));
            }

            return cRestaurants;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    // 매장 정보 및 메뉴 불러오기
    public CRestaurantMenus getCRestaurantMenus(long id) {
        String url = "https://api.coupangeats.com/endpoint/store.get_store_with_menu";
//        String curLocation = String.format("{ \"latitude\" : %s, \"longitude\" : %s }", lat, lng);
        Connection conn = Jsoup.connect(url)
                .header("X-EATS-OS-TYPE", "IOS")
                .header("X-EATS-DEVICE-ID", "com.coupang.coupang-eats")
                .header("X-EATS-PCID", "com.coupang.coupang-eats")
                .header("X-EATS-LOCALE", "ko")
//                .header("X-EATS-LOCATION", curLocation)
                .header("X-EATS-APP-VERSION", X_EATS_APP_VERSION)
                .header("X-EATS-DEVICE-DENSITY", "X2")
                .data("storeId", Long.toString(id))
                .timeout(10000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            jsonObject = (JSONObject) jsonObject.get("data");
            JSONArray jsonArray = (JSONArray) jsonObject.get("menus");
            // 이미지 리스트 생성
            JSONArray images = (JSONArray) jsonObject.get("imagePaths");
            List<String> imageList = new ArrayList<>();
            for (int i = 0; i < images.size(); i++)
                imageList.add((String) images.get(i));

            // 메뉴 리스트 생성
            //List<CMenu> menus = getCMenus(jsonArray);
            List<CDish> dishes = getCMenus(jsonArray);

            // 매장 정보 생성
            CRestaurantMenus cRestaurantMenus = new CRestaurantMenus(
                    (Long) jsonObject.get("id"),
                    (String) jsonObject.get("name"),
                    (String) jsonObject.get("telNo"),
                    (String) jsonObject.get("description"),
                    (Double) jsonObject.get("latitude"),
                    (Double) jsonObject.get("longitude"),
                    (String) jsonObject.get("pickupAddress"),
                    imageList,
                    (Double) jsonObject.get("reviewRating"),
                    (Long) jsonObject.get("reviewCount"),
                    (String) jsonObject.get("openStatus"),
                    (Double) jsonObject.get("minimumOrderPriceForDelivery"),
                    (String) jsonObject.get("estimatedDeliveryTime"),
                    (Boolean) jsonObject.get("pickupAvailable"),
                    (Boolean) jsonObject.get("supportedPickup"),
                    (String) jsonObject.get("estimatedPickupTime"),
//                    menus
                    dishes
            );

            return cRestaurantMenus;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public List<CRestaurant> getCSearchRestaurants(double lat, double lng, String search, String sort) {
        String url = "https://api.coupangeats.com/endpoint/store.get_search";
        String curLocation = String.format("{ \"latitude\" : %s, \"longitude\" : %s }", lat, lng);
        Connection conn = Jsoup.connect(url)
                .header("X-EATS-OS-TYPE", "IOS")
                .header("X-EATS-DEVICE-ID", "com.coupang.coupang-eats")
                .header("X-EATS-PCID", "com.coupang.coupang-eats")
                .header("X-EATS-LOCALE", "ko")
                .header("X-EATS-LOCATION", curLocation)
                .header("X-EATS-APP-VERSION", X_EATS_APP_VERSION)
                .data("keyWord", search)
                .data("sort", sort)
                .timeout(10000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            jsonObject = (JSONObject) jsonObject.get("data");
            JSONArray entityList = (JSONArray) jsonObject.get("entityList");

            List<CRestaurant> cRestaurants = new ArrayList<>();
            for (int i = 1; i < entityList.size(); i++) {
                JSONObject cur = (JSONObject) entityList.get(i);
                cRestaurants.add(getCRestaurant(cur));
            }

            return cRestaurants;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private CRestaurant getCRestaurant(JSONObject cur){
        cur = (JSONObject) ((JSONObject) cur.get("entity")).get("data");
        // imagePaths
        JSONArray imagePaths = (JSONArray) cur.get("imagePaths");
        List<String> imagePathList = new ArrayList<>();
        for (int j = 0; j < imagePaths.size(); j++) {
            imagePathList.add((String) imagePaths.get(j));
        }

        CRestaurant cRestaurant = new CRestaurant(
                (Long) cur.get("id"),
                (String) cur.get("name"),
                (String) cur.get("telNo"),
                (String) cur.get("description"),
                (Double) cur.get("latitude"),
                (Double) cur.get("longitude"),
                String.format("%s %s", (String) cur.get("address"), (String) cur.get("addressDetail")),
                imagePathList,
                (Double) cur.get("reviewRating"),
                (Long) cur.get("reviewCount"),
                (String) cur.get("openStatus"),
                (Double) cur.get("minimumOrderPriceForDelivery"),
                (String) cur.get("estimatedDeliveryTime"),
                (String) cur.get("pickupEstimationFoodTimeText"),
                (Boolean) cur.get("pickupAvailable"),
                (Boolean) cur.get("supportedPickup"));
        return cRestaurant;
    }
    private List<CDish> getCMenus(JSONArray jsonArray) {
//        List<CMenu> cMenus = new ArrayList<>();

        List<CDish> dishList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject cur = (JSONObject) jsonArray.get(i);
            // 메뉴 하나
            JSONArray dishes = (JSONArray) cur.get("dishes");

            // 메뉴 생성
            for (int j = 0; j < dishes.size(); j++) {
                JSONObject dish = (JSONObject) dishes.get(j);
                JSONArray imagePaths = (JSONArray) dish.get("imagePaths");
                // 메뉴 이미지 생성
                List<String> images = new ArrayList<>();
                for (int k = 0; k < imagePaths.size(); k++)
                    images.add((String) imagePaths.get(k));

                CDish cDish = new CDish(
                        (Long) dish.get("id"),
                        (Long) dish.get("storeId"),
                        (String) dish.get("name"),
                        (String) dish.get("description"),
                        (Double) dish.get("salePrice"),
                        (String) dish.get("displayStatusText"),
                        images
                );
                dishList.add(cDish);
            }
//            CMenu cMenu = new CMenu(
//                    (Long) cur.get("id"),
//                    (Long) cur.get("storeId"),
//                    (String) cur.get("name"),
//                    (String) cur.get("description"),
//                    dishList
//            );
//            cMenus.add(cMenu);
        }
        return dishList;
    }
}