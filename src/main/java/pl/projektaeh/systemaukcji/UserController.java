package pl.projektaeh.systemaukcji;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("")
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") int id) {
        return userRepository.getById(id);
    }

//    @PostMapping("")
//    public int add(@RequestBody List<User> users) {
//        return userRepository.save(users);
//    }

//    @PutMapping("/{id}")
//    public int update(@PathVariable("id") int id, @RequestBody User updatedUser) {
//        User user = userRepository.getById(id);
//
//        if (user != null) {
//            user.setName(updatedUser.getName());
//            user.setRating(updatedUser.getRating());
//
//            userRepository.update(user);
//
//            return 1;
//        } else {
//            return -1;
//        }
//    }
//
//    @PatchMapping("/{id}")
//    public int partiallyUpdate(@PathVariable("id") int id, @RequestBody User updatedUser) {
//        User user = userRepository.getById(id);
//
//        if (user != null) {
//            if (updatedUser.getLogin() != null) user.setLogin(updatedUser.getName());
//            if (updatedUser.getRating() > 0) user.setRating(updatedUser.getRating());
//
//            userRepository.update(user);
//
//            return 1;
//        } else {
//            return -1;
//        }
//    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable("id") int id) {
        return userRepository.delete(id);
    }

}
