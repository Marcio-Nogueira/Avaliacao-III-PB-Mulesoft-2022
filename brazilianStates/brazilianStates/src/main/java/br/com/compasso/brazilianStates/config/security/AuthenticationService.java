package br.com.compasso.brazilianStates.config.security;



import br.com.compasso.brazilianStates.models.User;
import br.com.compasso.brazilianStates.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = repository.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }
}