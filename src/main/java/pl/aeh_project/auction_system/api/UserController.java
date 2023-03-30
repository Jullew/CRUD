package pl.aeh_project.auction_system.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.aeh_project.auction_system.domain.entity.User;
import pl.aeh_project.auction_system.exceptions.ExpiredSessionException;
import pl.aeh_project.auction_system.exceptions.LoggedUserException;
import pl.aeh_project.auction_system.exceptions.UnloggedUserException;
import pl.aeh_project.auction_system.logic.UserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor

/* Controller - klasa, która obsługuje zapytania wysyłane przez przeglądarkę do użytkownika */
public class UserController {

    private final UserService userService;

    /* Autentykacja */
    @PostMapping(path = "/auth")
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
    @PostMapping("/getUser")
    public User getUser(@RequestBody User user) {
        Optional<User> checkSession = userService.checkSession(user.getLogin(), user.getSessionKey());
        if (checkSession.isEmpty())  {
            throw new IllegalArgumentException("Error");
        }
        return checkSession.get();
    }

    /* Pobieranie wszystkich użytkowników */
    @PostMapping("/getAllUsers")
    public List<User> getAllUsers(@RequestBody User user) {
        validateUser(user);
        return userService.getAll();
    }


    /* Dodawanie użytkownika */
    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        userService.update(user);
        return "Add new user";
    }

    /* Modyfikowanie użytkownika */
    @PutMapping("/updateUser")
    public String updateUser(@RequestBody User user) {
        validateUser(user);
        userService.update(user);
        return "Update user";
    }

    /* Usuwanie użytkownika */
    @DeleteMapping("/{id}")
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
