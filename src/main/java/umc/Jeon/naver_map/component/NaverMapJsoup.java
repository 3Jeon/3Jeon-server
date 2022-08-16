package umc.Jeon.naver_map.component;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import org.jsoup.nodes.Document;
import umc.Jeon.config.secret.Secret;
import umc.Jeon.naver_map.model.MapStats;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class NaverMapJsoup {

    public MapStats getNaverMapStats(String start, String goal) {
        String url = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";
        Connection conn = Jsoup.connect(url)
                .header("X-NCP-APIGW-API-KEY-ID", Secret.NMAP_API_KEY_ID)
                .header("X-NCP-APIGW-API-KEY", Secret.NMAP_API_KEY)
                .data("start", start)
                .data("goal", goal)
                .data("option", "trafast")
                .ignoreContentType(true)
                .method(Connection.Method.GET);

        Document doc;
        MapStats mapStats;
        try {
            doc = conn.get();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
            JSONObject cStart = ((JSONObject) ((JSONObject) ((JSONArray) ((JSONObject) jsonObject.get("route")).get("trafast")).get(0)).get("summary"));

            JSONArray startPoint = (JSONArray) ((JSONObject) cStart.get("start")).get("location");
            Iterator<Double> iterator = startPoint.iterator();
            List<Double> starting = new ArrayList<>();
            while(iterator.hasNext())
                starting.add(iterator.next());

            JSONArray goalPoint = (JSONArray) ((JSONObject) cStart.get("goal")).get("location");
            iterator = goalPoint.iterator();
            List<Double> goaling = new ArrayList<>();
            while(iterator.hasNext())
                goaling.add(iterator.next());

            String currentDateTime = (String) jsonObject.get("currentDateTime");
            String departureTime = (String) cStart.get("departureTime");
            currentDateTime = currentDateTime.replace("T", " ");
            departureTime = departureTime.replace("T", " ");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            Date currentTime = formatter.parse(currentDateTime);
            Date departTime = formatter.parse(departureTime);
            mapStats = new MapStats(
                    starting,
                    goaling,
                    currentTime,
                    departTime,
                    (long) cStart.get("distance"),
                    (long) cStart.get("duration"),
                    (long) cStart.get("tollFare"),
                    (long) cStart.get("taxiFare"),
                    (long) cStart.get("fuelPrice")
                    );
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (java.text.ParseException e) {
            throw new RuntimeException(e);
        }

        return mapStats;
    }
}