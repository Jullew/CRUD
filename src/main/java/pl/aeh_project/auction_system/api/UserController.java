package pl.aeh_project.auction_system.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.aeh_project.auction_system.domain.entity.User;
import pl.aeh_project.auction_system.exceptions.ExpiredSessionException;
import pl.aeh_project.auction_system.exceptions.LoggedUserException;
import pl.aeh_project.auction_system.exceptions.NoUserException;
import pl.aeh_project.auction_system.exceptions.UnloggedUserException;
import pl.aeh_project.auction_system.logic.UserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor

/* Controller - klasa, która obsługuje zapytania wysyłane przez przeglądarkę od użytkownika */
public class UserController {

    @Autowired
    private final UserService userService;

    /* Autentykacja */
    @PostMapping(path = "/authentication")
    public HashMap<String, String> auth(@RequestBody User user)
    {
        Optional<User> userLogin = userService.auth(user.getLogin(), user.getPassword());
        HashMap<String, String> map = new HashMap<>();

        if (userLogin.isPresent()) {
            User loggedUser = userLogin.get();
            UUID sessionKey = UUID.randomUUID();
            String sessionK = sessionKey.toString().replaceAll("_", "");
            LocalDateTime sessionEnd = LocalDateTime.now().plusMinutes(30L);

            loggedUser.setSessionKey(sessionK);
            loggedUser.setSessionEnd(sessionEnd);
            userService.update(loggedUser);

            map.put("token", sessionK);
        } else {
            map.put("Error", "Error login");
        }

        return map;
    }

    /* Pobieranie użytkownika */
    @PostMapping("/get")
    public User getUser(@RequestBody UserDTO userDTO) {
        Optional<User> checkSession = userService.checkSession(userDTO.getLogin(), userDTO.getSessionKey());
        if (checkSession.isEmpty())  {
            throw new NoUserException();
        }
        return checkSession.get();
    }

    /* Pobieranie wszystkich użytkowników */
    @PostMapping("/getAll")
    public List<User> getAllUsers(@RequestBody UserDTO userDTO) {
        Optional<User> user = userService.checkSession(userDTO.getLogin(), userDTO.getSessionKey());
        if(user.isEmpty()){
            throw new NoUserException();
        }
        validateUser(user.get());
        return userService.getAll();
    }


    /* Dodawanie użytkownika */
    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        userService.update(user);
        return "Add new user";
    }

    /* Modyfikowanie użytkownika */
    @PutMapping("/update")
    public String updateUser(@RequestBody User user) {
        validateUser(user);
        userService.update(user);
        return "Update user";
    }

    /* Usuwanie użytkownika */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    /* Walidacja zalogowania, metoda wykorzystywana w powyższych metodach */
    private void validateUser(User user) {
        Optional<User> checkSession = userService.checkSession(user.getLogin(), user.getSessionKey());
        if(checkSession.isEmpty())  {
            throw new ExpiredSessionException();
        }
        if(userService.getUserByLogin(user.getLogin()).isEmpty()) {
            throw new UnloggedUserException();
        }
    }

}
