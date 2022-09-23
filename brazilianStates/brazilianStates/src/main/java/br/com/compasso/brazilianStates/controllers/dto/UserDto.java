package br.com.compasso.brazilianStates.controllers.dto;

import br.com.compasso.brazilianStates.models.User;

public class UserDto {

    private String name;
    private String email;
    private String password;

    public UserDto(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
