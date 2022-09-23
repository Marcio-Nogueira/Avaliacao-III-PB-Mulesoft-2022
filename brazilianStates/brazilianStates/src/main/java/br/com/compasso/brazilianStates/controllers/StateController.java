package br.com.compasso.brazilianStates.controllers;

import br.com.compasso.brazilianStates.controllers.dto.StateDto;
import br.com.compasso.brazilianStates.models.Region;
import br.com.compasso.brazilianStates.models.State;
import br.com.compasso.brazilianStates.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/api/v1/estados")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @GetMapping
    @Cacheable
    public List<StateDto> StateFilter(@RequestParam(required = false) Region region,
                                      @RequestParam(required = false) String orderBy) {

        Sort sort = Sort.by(Sort.DEFAULT_DIRECTION, "id");

        if (orderBy != null) {
            if (orderBy.equalsIgnoreCase("population")) {
                sort = Sort.by(Sort.Direction.DESC, "population");
            } else if (orderBy.equalsIgnoreCase("area"));
        }

        if (region != null) {
            List<State> states = stateRepository.findByRegion(region, sort);
            return StateDto.convert(states);
        } else {
            List<State> states = stateRepository.findAll(sort);
            return StateDto.convert(states);
        }

    }
}
