package pl.aeh_project.auction_system.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.aeh_project.auction_system.domain.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLoginAndPassword(String login, String password);

    Optional<User> findUserByLoginAndSessionKeyAndAndSessionEndIsAfter(String login, String sessionKey, LocalDateTime sessionEnd);

    Optional<User> findUserByLoginAndSessionKey(String login, String sessionKey);

    Optional<User> findByLogin(String login);
}

