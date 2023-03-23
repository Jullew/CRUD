package pl.aeh_project.auction_system.user;

import jakarta.servlet.http.HttpServletRequest;
import netscape.javascript.JSObject;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;
import pl.aeh_project.auction_system.exceptions.ExpiredSessionException;
import pl.aeh_project.auction_system.exceptions.LoggedUserException;
import pl.aeh_project.auction_system.exceptions.UnloggedUserException;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping(path = "/auth")
    public HashMap<String, String> auth(@RequestBody User user)
    {
        User userLogin = userRepository.auth(user.getLogin(), user.getPassword());
        HashMap<String, String> map = new HashMap<>();

        if (userLogin != null)
        {

            UUID sessionKey = UUID.randomUUID();
            String sessionK = sessionKey.toString().replaceAll("_", "");

            userRepository.updateSessionKey(user.getLogin(), sessionK);
            map.put("token", sessionK);

        } else {
            map.put("error", "Error login");
        }

        return map;
    }

    @PostMapping("/getUser")
    public User getUser(@RequestBody User user) {
        User checkSession = userRepository.checkSession(user.getLogin(), user.getSessionKey());
        if(checkSession != null)  {
            return userRepository.getUser(user.getLogin());
        } else {
            throw new IllegalArgumentException("Error");
        }
    }

    @PostMapping("/getAllUsers")
    public List<User> getAllUsers(@RequestBody List<User> users, User user) {
        User checkSession = userRepository.checkSession(user.getLogin(), user.getSessionKey());
        if(checkSession == null)  {
            throw new ExpiredSessionException();
        }
        if(userRepository.getUser(user.getLogin()) == null) {
            throw new UnloggedUserException();
        }
        return userRepository.getAll();
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody User user) {
        User checkSession = userRepository.checkSession(user.getLogin(), user.getSessionKey());
        if(checkSession == null)  {
            throw new ExpiredSessionException();
        }
        if(userRepository.getUser(user.getLogin()) != null) {
            throw new LoggedUserException();
        }
        userRepository.save(user);
        return "Add new user";
    }

    @PutMapping("/updateUser")
    public String updateUser(@RequestBody User user) {
        User checkSession = userRepository.checkSession(user.getLogin(), user.getSessionKey());
        if (checkSession == null) {
            throw new ExpiredSessionException();
        }
        if (userRepository.getUser(user.getLogin()) == null) {
            throw new UnloggedUserException();
        }
        userRepository.update(user);
        return "Update user";
    }


    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return userRepository.delete(id);
    }

}
