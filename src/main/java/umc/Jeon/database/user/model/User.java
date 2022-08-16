package umc.Jeon.database.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.Jeon.database.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String nickname;

    @Column(length = 30)
    private String phone;

    //연령대
    private String age;

    private String email;

    @Builder
    public User(String name, String email, String phone, String nickname, String age, boolean status) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.nickname = nickname;
        this.age = age;
        this.status = status;
    }
}
