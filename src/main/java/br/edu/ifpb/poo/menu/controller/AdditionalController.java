package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.additional.AdditionalNotFoundException;
import br.edu.ifpb.poo.menu.model.Additional;
import br.edu.ifpb.poo.menu.model.Views;
import br.edu.ifpb.poo.menu.service.AdditionalService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/additional")
public class AdditionalController {

    @Autowired
    private AdditionalService additionalService;

    /**
     * Retorna um adicional pelo ID.
     */
    @JsonView(Views.SimpleView.class)
    @GetMapping("/{id}")
    public ResponseEntity<Additional> findById(@PathVariable Long id) throws AdditionalNotFoundException {
        Additional additional = additionalService.getAdditionalById(id);
        return ResponseEntity.ok(additional); // Retorna 200 OK com o adicional
    }

    /**
     * Cria um novo adicional.
     */
    @JsonView(Views.SimpleView.class)
    @PostMapping
    public ResponseEntity<Additional> createAdditional(@RequestBody Additional additional) throws InvalidFieldException {
        Additional createdAdditional = additionalService.createAdditional(additional);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdditional);
    }

    /**
     * Atualiza um adicional existente.
     */
    @JsonView(Views.SimpleView.class)
    @PutMapping("/{additionalId}")
    public ResponseEntity<Additional> updateAdditional(
            @PathVariable Long additionalId,
            @RequestBody Additional updatedAdditional
    ) throws AdditionalNotFoundException, InvalidFieldException {
        Additional updatedAdditionalResult = additionalService.updateAdditional(additionalId, updatedAdditional);
        return ResponseEntity.ok(updatedAdditionalResult);
    }

    /**
     * Deleta um adicional pelo ID.
     */
    @JsonView(Views.SimpleView.class)
    @DeleteMapping("/{additionalId}")
    public ResponseEntity<Void> deleteAdditional(@PathVariable Long additionalId) throws AdditionalNotFoundException {
        additionalService.deleteAdditional(additionalId);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content
    }

    /**
     * Retorna todos os adicionais.
     */
    @JsonView(Views.SimpleView.class)
    @GetMapping
    public ResponseEntity<List<Additional>> findAll() {
        List<Additional> additionals = additionalService.getAllAdditionals();
        return ResponseEntity.ok(additionals);
    }
}
