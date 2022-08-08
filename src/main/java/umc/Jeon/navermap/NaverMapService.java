package umc.Jeon.navermap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import umc.Jeon.config.exception.BaseException;
import umc.Jeon.navermap.component.NaverMapJsoup;
import umc.Jeon.navermap.model.MapStats;

import static umc.Jeon.config.exception.BaseResponseStatus.*;

@Service
public class NaverMapService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final NaverMapJsoup naverMapJsoup;

    public NaverMapService(NaverMapJsoup naverMapJsoup) {
        this.naverMapJsoup = naverMapJsoup;
    }

    public MapStats getMapStats(String start, String goal) throws BaseException {
        try {
            MapStats mapStats = naverMapJsoup.getNaverMapStats(start, goal);
            return mapStats;
        } catch (Exception exception) {
            throw new BaseException(REQUEST_ERROR);
        }
    }
}
