package pl.aeh_project.auction_system.login;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public void login(@RequestBody LoginDto loginDto) {

    }
}
