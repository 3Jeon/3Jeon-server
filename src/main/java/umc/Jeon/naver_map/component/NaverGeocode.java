package umc.Jeon.naver_map.component;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import umc.Jeon.config.secret.Secret;
import umc.Jeon.database.location.LocationDao;
import umc.Jeon.database.location.model.Location;
import umc.Jeon.naver_map.model.GeoStats;

import java.io.IOException;
import java.util.List;

@Component
public class NaverGeocode {
    final LocationDao locationDao;
    public NaverGeocode(LocationDao locationDao){
        this.locationDao = locationDao;
    }
    public GeoStats getNaverGeocode(long userId, String query) {
        Location userLocation = locationDao.getUserDefaultLocation(userId);
        String coordinate = String.format("%s, %s", String.valueOf(userLocation.getLng()), String.valueOf(userLocation.getLat()));
//        String coordinate = "127.06088821638144, 37.547511130279254";
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
        Connection conn = Jsoup.connect(url)
                .header("X-NCP-APIGW-API-KEY-ID", Secret.NMAP_API_KEY_ID)
                .header("X-NCP-APIGW-API-KEY", Secret.NMAP_API_KEY)
                .data("query", query)
                .data("coordinate", coordinate)
                .ignoreContentType(true)
                .method(Connection.Method.GET);
        Document doc = null;
        try {
            doc = conn.get();
            System.out.println(doc.text());
            JSONParser jsonParse = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParse.parse(doc.text());
            JSONArray geoAddresses = (JSONArray) jsonObject.get("addresses");
            JSONObject addresses = (JSONObject) geoAddresses.get(0);

            GeoStats result = new GeoStats(
                    (String) addresses.get("roadAddress"),
                    (String) addresses.get("jibunAddress"),
                    (Double) addresses.get("distance"),
                    Double.parseDouble((String) addresses.get("x")),
                    Double.parseDouble((String) addresses.get("y"))
            );
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
