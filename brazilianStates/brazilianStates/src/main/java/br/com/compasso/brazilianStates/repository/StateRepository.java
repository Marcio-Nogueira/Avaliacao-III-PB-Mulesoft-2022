package br.com.compasso.brazilianStates.repository;

import br.com.compasso.brazilianStates.models.Region;
import br.com.compasso.brazilianStates.models.State;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateRepository extends JpaRepository<State, Long> {

    List<State> findByRegion(Region region, Sort sort);

}
