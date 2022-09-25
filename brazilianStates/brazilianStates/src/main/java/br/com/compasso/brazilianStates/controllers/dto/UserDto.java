package br.com.compasso.brazilianStates.controllers.dto;

import br.com.compasso.brazilianStates.models.User;
import org.springframework.data.domain.Page;

public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;

    public UserDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public static Page<UserDto> convert(Page<User> users) {
        return users.map(UserDto::new);
    }
}
