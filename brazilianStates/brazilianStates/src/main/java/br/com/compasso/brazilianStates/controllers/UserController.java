package br.com.compasso.brazilianStates.controllers;

import br.com.compasso.brazilianStates.controllers.dto.UserDto;
import br.com.compasso.brazilianStates.controllers.forms.UserForm;
import br.com.compasso.brazilianStates.models.User;
import br.com.compasso.brazilianStates.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<UserDto> cadastrar(@RequestBody @Valid UserForm form, UriComponentsBuilder uriBuilder) {

        User user = form.convert();
        userRepository.save(user);

        URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDto(user));
    }

    @GetMapping
    public Page<UserDto> list(@PageableDefault(sort = "id", direction = DESC, page = 0, size = 10) Pageable paginacao) {

            Page<User> users = userRepository.findAll(paginacao);
            return UserDto.convert(users);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody @Valid UserForm form ) {
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            User user = form.update(id, userRepository);
            return ResponseEntity.ok(new UserDto(user));
        }
        return ResponseEntity.notFound().build();
    }

}
