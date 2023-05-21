package pl.aeh_project.auction_system.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import pl.aeh_project.auction_system.api.dto.userDto.*;
import pl.aeh_project.auction_system.logic.UserService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor

/* Controller - klasa, która obsługuje zapytania wysyłane przez przeglądarkę od użytkownika */
public class UserController {

    private final UserService userService;

    /**
     * Autentykacja
     * WYMAGANE DANE:
     * String login;
     * String password;
     */
    @PostMapping(path = "/authentication")
    public HashMap<String, String> authentication(@RequestBody @NonNull UserAuthenticationDto user){
        return userService.authentication(user.getLogin(), user.getPassword());
    }

    /**
     * Pobieranie użytkownika po id
     * WYMAGANE DANE:
     * Long id;
     */
    @GetMapping("/get/{id}")
    public UserDto get(@PathVariable("id") Long id){
        return userService.get(id);
    }

    /**
     * Pobieranie wszystkich produktów
     * WYMAGANE DANE:
     * brak;
     */
    @PostMapping("/getAll")
    public List<UserDto> getAll() {
        return userService.getAll();
    }


    /**
     * Dodawanie użytkownika
     * WYMAGANE DANE:
     * String login;
     * String password;
     * String firstName;
     * String lastName;
     */
    @PostMapping("/add")
    public void add(@RequestBody @NonNull AddUserDto user) {
        userService.add(user);
    }

    /**
     * Modyfikacja użytkownika
     * WYMAGANE DANE:
     * Long id;
     * String login;
     * String password;
     * String firstName;
     * String lastName;
     * String sessionKey;
     */
    @PutMapping("/modify")
    public void update(@RequestBody @NonNull ModifiedUserDto user) {
        userService.update(user);
    }

    /**
     * Usuwanie użytkownika
     * WYMAGANE DANE:
     * Long id;
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }
}
