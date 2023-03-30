package pl.aeh_project.auction_system.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import pl.aeh_project.auction_system.domain.entity.User;
import pl.aeh_project.auction_system.domain.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

/* Klasa service to klasa, która oferuje logikę biznesową, która jest wykorzystywana w klasach controller */
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> auth(String login, String password) {
        return userRepository.findUserByLoginAndPassword(login, password);
    }

    public Optional<User> checkSession(String postLogin, String postSessionKey) {
        return userRepository.findUserByLoginAndSessionKeyAndAndSessionEndIsAfter(postLogin, postSessionKey, LocalDateTime.now());
    }

    public Optional<User> getUserByLogin(String postLogin) {
        return userRepository.findByLogin(postLogin);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
