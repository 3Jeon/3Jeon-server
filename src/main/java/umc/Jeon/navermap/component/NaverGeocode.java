package umc.Jeon.navermap.component;

import com.nimbusds.jose.shaded.json.parser.JSONParser;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import umc.Jeon.config.secret.Secret;

import java.io.IOException;
import java.util.List;

public class NaverGeocode {
    public List<NaverGeocode> getNaverGeocode(String query, String coordinate) {
        String url = "https://api.ncloud-docs.com/docs/ai-naver-mapsgeocoding-geocode";
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
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(doc.text());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
