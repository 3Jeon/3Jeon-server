package umc.Jeon.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.Jeon.domain.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String nickname;

    @Column(length = 20)
    private String phone;

    //연령대
    private int age;

    private String email;
}
