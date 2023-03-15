package pl.aeh_project.auction_system.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/auth")
    public String auth(@RequestParam("login") String postLogin, @RequestParam("password") String postPassword, @RequestParam("api_key") String postApiKey) {
        User userLogin = userRepository.auth(postLogin, postPassword, postApiKey);
        if (userLogin != null) {
            String sessionKey = "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx";
            userRepository.updateSessionKey(postLogin,sessionKey);
            return sessionKey;
        } else {
            return "false";
        }
    }

    @GetMapping("")
    public List<User> getAll() {
        return userRepository.getAll();
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
            user.setLogin(updatedUser.getLogin());
            user.setPassword(updatedUser.getPassword());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setApiKey(updatedUser.getApiKey());
            user.setSessionKey(updatedUser.getSessionKey());
            user.setSessionEnd(updatedUser.getSessionEnd());

            userRepository.update(user);

        } else {
            throw new IllegalArgumentException("User is null");
        }
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return userRepository.delete(id);
    }

}
