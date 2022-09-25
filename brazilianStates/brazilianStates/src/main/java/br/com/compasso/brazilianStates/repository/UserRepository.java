package br.com.compasso.brazilianStates.repository;

import br.com.compasso.brazilianStates.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);
}
