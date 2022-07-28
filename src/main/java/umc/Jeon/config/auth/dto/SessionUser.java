package umc.Jeon.config.auth.dto;


import lombok.Getter;
import umc.Jeon.domain.user.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;

    private String nickname;

    private String age;

    private String phone;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.phone = user.getPhone();
    }
}
