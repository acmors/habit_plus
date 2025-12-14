package HabitPlus.service.user;

import HabitPlus.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> list();
    Optional<User> retrieve(Long id);
}
