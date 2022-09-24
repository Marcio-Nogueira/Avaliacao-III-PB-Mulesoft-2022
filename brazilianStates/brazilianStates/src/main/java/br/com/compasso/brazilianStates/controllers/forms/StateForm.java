package br.com.compasso.brazilianStates.controllers.forms;

import br.com.compasso.brazilianStates.models.Region;
import br.com.compasso.brazilianStates.models.State;
import br.com.compasso.brazilianStates.repository.StateRepository;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StateForm {

    @NotNull @NotEmpty @Length(min = 3)
    private String name;
    @NotNull @NotEmpty
    private Region region;
    @NotNull @NotEmpty
    private Long population;
    @NotNull @NotEmpty @Length(min=3)
    private String capital;
    @NotNull @NotEmpty
    private double area;

    public State convert() {
        return new State(name, region, population, capital, area);
    }

    public State update(Long id, StateRepository stateRepository) {
        State state = stateRepository.getReferenceById(id);
        state.setName(this.name);
        state.setRegion(this.region);
        state.setPopulation(this.population);
        state.setCapital(this.capital);
        state.setArea(this.area);

        return state;
    }

}
