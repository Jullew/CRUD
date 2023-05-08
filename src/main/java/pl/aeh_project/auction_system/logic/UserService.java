package pl.aeh_project.auction_system.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.aeh_project.auction_system.api.dto.userDto.AddUserDto;
import pl.aeh_project.auction_system.api.dto.userDto.ModifiedUserDto;
import pl.aeh_project.auction_system.domain.entity.User;
import pl.aeh_project.auction_system.domain.repository.UserRepository;
import pl.aeh_project.auction_system.exceptions.NoUserException;
import pl.aeh_project.auction_system.exceptions.UnloggedUserException;
import pl.aeh_project.auction_system.exceptions.WrongLoginException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

/* Klasa service to klasa, która oferuje logikę biznesową, która jest wykorzystywana w klasach controller */
public class UserService {

    private final UserRepository userRepository;

    /* Autentykacja użytkownika */
    public void authentication(String login, String password) {
        User user = checkingExistanceOfUser(login, password);

        String sessionKey = UUID.randomUUID().toString();
        LocalDateTime sessionEnd = LocalDateTime.now().plusMinutes(30L);
        user.setSessionKey(sessionKey);
        user.setSessionEnd(sessionEnd);

        userRepository.save(user);
    }

    /* Metoda pomocnicza */
    /* Sprawdza istnienie użytkownika i go zwraca, jeżeli istnieje */
    private User checkingExistanceOfUser(String login, String password){
        Optional<User> optionalUser = userRepository.findUserByLoginAndPassword(login, password);
        if(optionalUser.isEmpty()){
            throw new NoUserException("There is no such user");
        }
        return optionalUser.get();
    }

    /* ------------------------------------ */

    /* Pobieranie użytkownika po id */
    public User get(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new NoUserException("There is no user with such id");
        }
        return optionalUser.get();
    }

    /* ------------------------------------ */

    /* Pobieranie wszystkich użytkowników w postaci listy */
    public List<User> getAll() {
        return userRepository.findAll();
    }

    /* ------------------------------------ */

    /* Aktualizacja danych użytkownika */
    public void update(ModifiedUserDto modifiedUserDto) {
        loginVerification(modifiedUserDto.getId(), modifiedUserDto.getSessionKey());

        if(userRepository.findByLogin(modifiedUserDto.getLogin()).isPresent()){
            throw new WrongLoginException("This login already exists");
        }

        User user = new User();
        user.setLogin(modifiedUserDto.getLogin());
        user.setPassword(modifiedUserDto.getPassword());
        user.setFirstName(modifiedUserDto.getFirstName());
        user.setLastName(modifiedUserDto.getLastName());

        userRepository.save(user);
    }

    /* ------------------------------------ */

    /* Dodawanie użytkownika */
    public void add(AddUserDto addUserDto){
        User user = new User();
        if(userRepository.findByLogin(addUserDto.getLogin()).isPresent()){
            throw new WrongLoginException("This login already exists");
        }
        user.setLogin(addUserDto.getLogin());
        user.setPassword(addUserDto.getPassword());
        user.setFirstName(addUserDto.getFirstName());
        user.setLastName(addUserDto.getLastName());

        userRepository.save(user);
    }

    /* ------------------------------------ */

    /* Usuwanie użytkownika po id */
    public void delete(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new NoUserException("There is no user with such id");
        }
        User user = optionalUser.get();
        loginVerification(user.getUserId(), user.getSessionKey());
        userRepository.delete(user);
    }

    /* ------------------------------------ */

    /* Weryfikacja zalogowania na potrzeby klasy ProductController */
    public void loginVerification(Long id, String sessionKey){
        Optional<User> optionalUser = userRepository
                .findUserByUserIdAndSessionKeyAndSessionEndIsAfter(id, sessionKey, LocalDateTime.now());
        if (optionalUser.isEmpty())  {
            throw new UnloggedUserException("You are not logged in");
        }
    }

    /* Weryfikacja zalogowania na potrzeby klasy ProductController */
    public void loginVerification(String login, String sessionKey){
        Optional<User> optionalUser = userRepository
                .findUserByLoginAndSessionKeyAndSessionEndIsAfter(login, sessionKey, LocalDateTime.now());
        if (optionalUser.isEmpty())  {
            throw new UnloggedUserException("You are not logged in");
        }
    }

}
