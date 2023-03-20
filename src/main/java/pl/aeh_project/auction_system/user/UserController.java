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

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")

    @PostMapping(path = "/auth")
    public ResponseEntity<Object> auth(@RequestBody User user)
    {
        User userLogin = userRepository.auth(user.getLogin(), user.getPassword());
        if (userLogin != null)
        {
            String sessionKey = "test";
            userRepository.updateSessionKey(user.getLogin(), sessionKey);
            JSObject sessionKeyJson = new JSObject();

            return sessionKey;
        } else {
            return "false";
        }
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") int id) {
        return userRepository.getById(id);
    }

    @PostMapping("")
    public int add(@RequestBody List<User> users) {
        return userRepository.save(users);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody User updatedUser) {
        User user = userRepository.getById(id);

        if (user != null) {

            userRepository.update(updatedUser);

        } else {
            throw new IllegalArgumentException("User is null");
        }
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return userRepository.delete(id);
    }

}
