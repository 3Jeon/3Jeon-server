package umc.Jeon.config.secret;

import org.springframework.stereotype.Component;

@Component
public class Secret {
    // 요기요
    public static String YOGIYO_API_SECRET = "fe5183cc3dea12bd0ce299cf110a75a2";
    public static String YOGIYO_API_KEY = "iphoneap";
    // 배달의 민족
    public static String BAEMIN_USER_AGENT = "iph1_11.26.1";
    // 쿠팡 이츠
    public static String X_EATS_APP_VERSION = "1.3.50";
    // 네이버
    public static final String NMAP_API_KEY = "Pn5RVciG0yTfQ0WNJxnVSoNuGbLQdwfoMkSTCpob";
    public static final String NMAP_API_KEY_ID = "1zqglvqh0h";

    // 임의의 JWT 키 발급
    public static String JWT_SECRET_KEY = "UwKYibQQgkW7g-*k.ap9kje-wxBHb9wdXoBT4vnt4P3sJWt-Nu";
}