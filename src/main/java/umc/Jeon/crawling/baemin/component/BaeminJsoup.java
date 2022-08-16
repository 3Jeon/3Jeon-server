package umc.Jeon.crawling.baemin.component;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import umc.Jeon.crawling.baemin.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static umc.Jeon.config.secret.Secret.BAEMIN_USER_AGENT;

@Component
public class BaeminJsoup {

    // 주변 레스토랑 불러오기
    public List<BRestaurant> getBRestaurant(double lat, double lng, String category, String sort) {
        String url = "https://shopdp-api.baemin.com/v1/BAEMIN/curations";
        Connection conn = Jsoup.connect(url)
                .header("Host", "shopdp-api.baemin.com")
                .header("User-Agent", BAEMIN_USER_AGENT)
                .header("USER-BAEDAL", "baemin")
                .data("latitude", Double.toString(lat))
                .data("longitude", Double.toString(lng))
                .data("displayCategory", category)
                .data("sort", sort)
                .data("sort", "SORT__DISTANCE")
                .data("distance", "2")
                .timeout(10000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        List<BRestaurant> bRestaurants = new ArrayList<>();
        try {
            doc = conn.get();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            jsonObject = (JSONObject) jsonObject.get("data");
            JSONArray jsonArray = (JSONArray) jsonObject.get("shops");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject cur = (JSONObject) jsonArray.get(i);
                bRestaurants.add(getShopShort(cur));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return bRestaurants;
    }

    // 매장 정보 및 메뉴 불러오기
    public BRestaurantInfo getBRestaurantInfo(long id, double lat, double lng) {
        String url = String.format("https://shopdp-api.baemin.com/v8/shop/%s/detail", String.valueOf(id));
        Connection conn = Jsoup.connect(url)
                .header("Host", "shopdp-api.baemin.com")
                .header("User-Agent", BAEMIN_USER_AGENT)
                .data("lat", Double.toString(lat))
                .data("lng", Double.toString(lng))
                .timeout(10000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            jsonObject = (JSONObject) jsonObject.get("data");

            JSONObject shopInfo = (JSONObject) jsonObject.get("shop_info");
            JSONObject shopMenu = (JSONObject) jsonObject.get("shop_menu");
            JSONObject menu_info = (JSONObject) shopMenu.get("menu_info");

            // 매장 정보 생성
            BRestaurantDetail bRestaurantDetail = getShopInfo(shopInfo, menu_info);

            // 매장 메뉴 조회
            JSONObject menu_ord = (JSONObject) shopMenu.get("menu_ord");
            JSONArray soloMenus = (JSONArray) menu_ord.get("soloMenus");
            JSONArray representationMenus = (JSONArray) menu_ord.get("representationMenus");
            JSONArray setMenus = (JSONArray) menu_ord.get("setMenus");
            JSONArray groupMenus = (JSONArray) menu_ord.get("groupMenus");

            List<BMenu> representationMenuList = new ArrayList<>();
            // 대표 메뉴
            for (int i = 0; i < representationMenus.size(); i++) {
                JSONObject cur = (JSONObject) representationMenus.get(i);
                BMenu bMenu = getMenuInfo(cur);
                representationMenuList.add(bMenu);
            }
            /*
            List<BGroupMenu> groupMenuList = new ArrayList<>();
            // 그룹 메뉴
            for (int i = 0; i < groupMenus.size(); i++) {
                JSONObject cur = (JSONObject) groupMenus.get(i);
                JSONArray menus = (JSONArray) cur.get("menus");
                List<BMenu> bMenuList = new ArrayList<>();
                for (int j = 0; j < menus.size(); j++) {
                    JSONObject tmp = (JSONObject) menus.get(j);
                    bMenuList.add(getMenuInfo(tmp));
                }
                BGroupMenu bGroupMenu = new BGroupMenu(
                        (Long) cur.get("menuGroupId"),
                        (String) cur.get("name"),
                        (String) cur.get("description"),
                        bMenuList
                );

                groupMenuList.add(bGroupMenu);
            }

             */

            BRestaurantInfo bRestaurantInfo = new BRestaurantInfo(
                    bRestaurantDetail,
                    representationMenuList
//                    groupMenuList
            );

            return bRestaurantInfo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public List<BSRestaurant> getBSearchRestaurants(double lat, double lng, String search, String sort, int items) {
        String url = String.format("https://search-gateway.baemin.com/v1/search?appver=11.26.1&currentTab=BAEMIN_DELIVERY&isBmartRegion=0&isFirstRequest=1");
        Connection conn = Jsoup.connect(url)
                .header("USER-BAEDAL", "baemin")
                .header("User-Agent", BAEMIN_USER_AGENT)
                .data("latitude", Double.toString(lat))
                .data("longitude", Double.toString(lng))
                .data("keyword", search)
                .data("baeminDeliverySort", sort)
                .data("limit", Integer.toString(items))
                .timeout(10000)
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc = null;
        try {
            doc = conn.get();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            System.out.println(jsonObject.toString());
            // shop data access
            jsonObject = (JSONObject) jsonObject.get("data");
            jsonObject = (JSONObject) ((JSONArray) jsonObject.get("list")).get(0);
            jsonObject = (JSONObject) jsonObject.get("result");
            JSONArray shops = (JSONArray) jsonObject.get("shops");

            List<BSRestaurant> bsRestaurants = new ArrayList<>();
            for (int i = 0; i < shops.size(); i++) {
                JSONObject cur = (JSONObject) shops.get(i);
                bsRestaurants.add(getSShopShort(cur));
            }

            return bsRestaurants;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private BRestaurantDetail getShopInfo(JSONObject shopInfo, JSONObject menu_info) {
        // 매장 헤드 이미지
        JSONArray headImages = (JSONArray) menu_info.get("Shop_Header_Img");
        List<String> headImageList = new ArrayList<>();
        String Shop_Header_Img_Host = (String) menu_info.get("Shop_Header_Img_Host");
        for (int i = 0; i < headImages.size(); i++) {
            JSONObject im = (JSONObject) headImages.get(i);
            String Shop_Header_Img_Path = (String) im.get("Shop_Header_Img_Path");
            String Shop_Header_Img_File = (String) im.get("Shop_Header_Img_File");
            String imgUrl = String.format("%s/%s/%s", Shop_Header_Img_Host, Shop_Header_Img_Path, Shop_Header_Img_File);
            headImageList.add(imgUrl);
        }

        BRestaurantDetail bRestaurantDetail = new BRestaurantDetail(
                Long.parseLong((String) shopInfo.get("Shop_No")),
                (String) shopInfo.get("Shop_Nm"),
                (String) shopInfo.get("Vel_No"),
                headImageList,
                (Double) shopInfo.get("Loc_Pnt_Lat"),
                (Double) shopInfo.get("Loc_Pnt_Lng"),
                (String) shopInfo.get("Business_Location"),
                Double.parseDouble((String) shopInfo.get("Star_Pnt_Avg")),
                Long.parseLong((String) shopInfo.get("Review_Cnt")),
                Long.parseLong((String) shopInfo.get("Favorite_Cnt")),
                (String) shopInfo.get("orderCountText"),
                Long.parseLong((String) menu_info.get("Min_Ord_Price")),
                (Boolean) shopInfo.get("deliveryInOperation"),
                (Boolean) shopInfo.get("useDelivery"),
                (Boolean) shopInfo.get("useTakeout"),
                (Long) shopInfo.get("takeoutDiscountPrice"),
                (Long) shopInfo.get("takeoutDiscountRate"),
                (String) shopInfo.get("Ct_Cd_Nm"),
                (String) shopInfo.get("Ct_Cd_Nm_En"),
                (String) shopInfo.get("Dlvry_Tm"),
                (String) shopInfo.get("shopStatus")
        );
        return bRestaurantDetail;
    }

    private BMenu getMenuInfo(JSONObject jsonObject) {
        JSONArray images = (JSONArray) jsonObject.get("images");
        List<String> imageList = new ArrayList<>();
        for (int j = 0; j < images.size(); j++) {
            JSONObject tmp = (JSONObject) images.get(j);
            String imageUrl = (String) tmp.get("url");
            imageList.add(imageUrl);
        }
        BMenu bMenu = new BMenu(
                (Long) jsonObject.get("menuId"),
                (String) jsonObject.get("name"),
                (String) jsonObject.get("description"),
                imageList,
                (String) ((JSONObject) ((JSONArray) jsonObject.get("menuPrices")).get(0)).get("price"),
                (Boolean) jsonObject.get("soldOut")
        );
        return bMenu;
    }

    private BRestaurant getShopShort(JSONObject cur) {
        // 매장 정보
        JSONObject shopInfo = (JSONObject) cur.get("shopInfo");
        // 썸네일
        JSONArray thumbnails = (JSONArray) shopInfo.get("thumbnails");
        Iterator<String> iterator = thumbnails.iterator();
        List<String> thumbnailList = new ArrayList<>();
        while (iterator.hasNext())
            thumbnailList.add(iterator.next().trim());
        // 매장 상태
        JSONObject inOperation = (JSONObject) cur.get("shopStatus");
        // 매장 점수
        JSONObject shopStatistics = (JSONObject) cur.get("shopStatistics");
        // 기타 정보
        JSONObject logInfo = (JSONObject) cur.get("logInfo");
        JSONArray deliveryTips = (JSONArray) logInfo.get("deliveryTips");
        List<Long> deliveryTip = new ArrayList<>();
        Iterator<Long> iterator1 = deliveryTips.iterator();
        while (iterator1.hasNext())
            deliveryTip.add(iterator1.next());
        // 배달 예상 시간
        JSONArray expectedDeliveryTimes = (JSONArray) logInfo.get("expectedDeliveryTimes");
        List<Long> expectedDeliveryTime = new ArrayList<>();
        Iterator<Long> iterator2 = expectedDeliveryTimes.iterator();
        while (iterator2.hasNext())
            expectedDeliveryTime.add(iterator2.next());

        BRestaurant bRestaurant = new BRestaurant(
                Long.parseLong(String.valueOf(shopInfo.get("shopNumber"))),
                (String) shopInfo.get("shopName"),
                thumbnailList,
                (Boolean) inOperation.get("inOperation"),
                (Double) shopStatistics.get("averageStarScore"),
                deliveryTip,
                expectedDeliveryTime
        );

        return bRestaurant;
    }

    private BSRestaurant getSShopShort(JSONObject cur) {
        JSONObject shopInfo = (JSONObject) cur.get("shopInfo");
        JSONObject shopStatus = (JSONObject) cur.get("shopStatus");
        JSONObject deliveryInfo = (JSONObject) cur.get("deliveryInfo");
        JSONObject shopStatistics = (JSONObject) cur.get("shopStatistics");
        JSONObject logInfo = (JSONObject) cur.get("logInfo");

        // 배달팁
        JSONArray deliveryTips = (JSONArray) logInfo.get("deliveryTips");
        List<Long> deliveryTip = new ArrayList<>();
        Iterator<Long> iterator = deliveryTips.iterator();
        while (iterator.hasNext())
            deliveryTip.add(iterator.next());

        BSRestaurant bsRestaurant = new BSRestaurant(
                (Long) shopInfo.get("shopNumber"),
                (String) shopInfo.get("shopName"),
                (String) shopInfo.get("telNumber"),
                (String) shopInfo.get("address"),
                (String) shopInfo.get("logoUrl"),
                (String) shopInfo.get("closeDayText"),
                (String) shopInfo.get("virtualTelNumber"),
                (Boolean) shopStatus.get("inOperation"),
                (Long) deliveryInfo.get("minimumOrderPrice"),
                (Double) shopStatistics.get("averageStarScore"),
                deliveryTip);
        return bsRestaurant;
    }
}