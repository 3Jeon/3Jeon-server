package umc.Jeon.domain.auth.dto;


import lombok.Getter;
import umc.Jeon.domain.user.User;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
