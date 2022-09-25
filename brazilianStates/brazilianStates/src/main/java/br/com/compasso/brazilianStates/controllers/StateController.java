package br.com.compasso.brazilianStates.controllers;

import br.com.compasso.brazilianStates.controllers.dto.StateDto;
import br.com.compasso.brazilianStates.controllers.forms.StateForm;
import br.com.compasso.brazilianStates.models.Region;
import br.com.compasso.brazilianStates.models.State;
import br.com.compasso.brazilianStates.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/estados")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @GetMapping
    public List<StateDto> StateFilter(@RequestParam(required = false) Region region,
                                      @RequestParam(required = false) String orderBy) {

        Sort sort = Sort.by(Sort.DEFAULT_DIRECTION, "id");

        if (orderBy != null) {
            if (orderBy.equals("population")) {
                sort = Sort.by(Sort.Direction.DESC, "population");
            } else if (orderBy.equals("area")) {
                sort = Sort.by(Sort.Direction.DESC, "area");
            }
        }

        if (region != null) {
            List<State> states = stateRepository.findByRegion(region, sort);
            return StateDto.convert(states);
        } else {
            List<State> states = stateRepository.findAll(sort);
            return StateDto.convert(states);
        }

    }

    @PostMapping
    @Transactional
    public ResponseEntity<StateDto> register(@RequestBody @Valid StateForm stateForm, UriComponentsBuilder uriBuilder) {

        State state = stateForm.convert();
        stateRepository.save(state);

        URI uri = uriBuilder.path("/estados/{id}").buildAndExpand(state.getId()).toUri();
        return ResponseEntity.created(uri).body(new StateDto(state));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StateDto> getState(@PathVariable Long id) {
        Optional<State> state = stateRepository.findById(id);
        if (state.isPresent()) {
            return ResponseEntity.ok(new StateDto(state.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<StateDto> updateState(@PathVariable Long id, @RequestBody @Valid StateForm form ) {
        Optional<State> optional = stateRepository.findById(id);
        if (optional.isPresent()) {
            State state = form.update(id, stateRepository);
            return ResponseEntity.ok(new StateDto(state));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<State> optional = stateRepository.findById(id);
        if (optional.isPresent()) {
            stateRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
