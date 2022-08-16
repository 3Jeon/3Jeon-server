package umc.Jeon.database.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import umc.Jeon.database.user.model.User;
import umc.Jeon.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    // 유저가 존재하는지 확인
    public boolean checkUserExist(long userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) return true;
        else return false;
    }
}
