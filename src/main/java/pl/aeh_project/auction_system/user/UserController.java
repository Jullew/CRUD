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
            /*
            List<JSONObject> entities = new ArrayList<JSONObject>();
        for (Entity n : entityList) {
            JSONObject entity = new JSONObject();
            entity.put("aa", "bb");
            entities.add(entity);
        }
        return new ResponseEntity<Object>(entities, HttpStatus.OK);

             */

            String sessionKey = "test";
            userRepository.updateSessionKey(user.getLogin(), sessionKey);
            JSObject sessionKeyJson = new JSObject();
            sessionKeyJson.put("test");

            return sessionKey;
        } else {
            return "false";
        }
    }

    @PostMapping("/getByUsername")
    public User getByUsername(@RequestBody User user) {
        User checkSession = userRepository.checkSession(user.getLogin(), user.getSessionKey());
        if(checkSession != null)  {
            return userRepository.getByUsername(user.getLogin());
        } else {
            throw new IllegalArgumentException("Error");
        }
    }

   /* @PostMapping("/registerNewUser")
    public User registerNewUser(@RequestBody User user) {

    }*/

    @PostMapping("")
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @PostMapping("/add")
    public int add(@RequestBody List<User> users) {
        return userRepository.save(users);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody User updatedUser) {
        User user = userRepository.getById(id);

        userRepository.update(updatedUser);
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
