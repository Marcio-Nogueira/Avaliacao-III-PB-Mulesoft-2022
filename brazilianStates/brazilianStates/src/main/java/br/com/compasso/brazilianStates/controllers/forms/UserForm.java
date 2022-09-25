package br.com.compasso.brazilianStates.controllers.forms;

import br.com.compasso.brazilianStates.models.User;
import br.com.compasso.brazilianStates.repository.UserRepository;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class UserForm {

    @NotNull  @NotEmpty @Length(min = 2)
    private String name;
    @NotNull  @NotEmpty @Length(min = 10)
    private String email;
    @NotNull  @NotEmpty @Length(min = 6)
    private String password;

    public User convert() {
        return new User(name, email, password);
    }

    public User update(Long id, UserRepository userRepository) {
        User user = userRepository.getReferenceById(id);
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);

        return user;
    }

}
