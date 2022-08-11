package umc.Jeon.navermap.component;


import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.*;

import java.io.IOException;

public class GoogleMapApi {
    private GoogleMapApi CommonUtil;

    public Float[] findGeoPoint(String location) {

        if (location == null)
            return null;

        // setAddress : 변환하려는 주소 (경기도 성남시 분당구 등)
        // setLanguate : 인코딩 설정
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(location)
                .setLanguage("ko")
                .getGeocoderRequest();

        try {
            Geocoder geocoder = new Geocoder();
            GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);

            if (geocoderResponse.getStatus() == GeocoderStatus.OK & !geocoderResponse.getResults().isEmpty()) {
                GeocoderResult geocoderResult = geocoderResponse.getResults().iterator().next();
                LatLng latitudeLongitude = geocoderResult.getGeometry().getLocation();

                Float[] coords = new Float[2];
                coords[0] = latitudeLongitude.getLat().floatValue();
                coords[1] = latitudeLongitude.getLng().floatValue();
                return coords;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void main(String [] args){
        GoogleMapApi googleMapApi = new GoogleMapApi();
        String location = "경기도 성남시 분당구 판교동";
        Float [] geo = googleMapApi.findGeoPoint(location);
        System.out.println(location +": " + geo[0] + ", " + geo[1]);
    }
}
