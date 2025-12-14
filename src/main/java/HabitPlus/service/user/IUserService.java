package HabitPlus.service.user;

import HabitPlus.model.user.User;
import HabitPlus.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IUserService implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> list() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> retrieve(Long id) {
        return userRepository.findById(id);
    }


}
