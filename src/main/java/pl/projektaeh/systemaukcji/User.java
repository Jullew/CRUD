package pl.projektaeh.systemaukcji;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id_user;
    private String login;
    private String password;
    private String name;
    private String surname;
    private String key_api;
    private String key_session;
    private Date session_end;
}

