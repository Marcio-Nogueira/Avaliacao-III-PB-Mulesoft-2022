package br.com.compasso.brazilianStates.controllers.dto;

import br.com.compasso.brazilianStates.models.Region;
import br.com.compasso.brazilianStates.models.State;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StateDto {

    private String name;
    private Region region;
    private long population;
    private String capital;
    private double area;

    public StateDto(State state) {
        this.name = state.getName();
        this.region = state.getRegion();
        this.population = state.getPopulation();
        this.capital = state.getCapital();
        this.area = state.getArea();
    }

    public static List<StateDto> convert(List<State> states) {
        return states.stream().map(StateDto::new).collect(Collectors.toList());
    }
}
