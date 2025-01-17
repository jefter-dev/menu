package br.edu.ifpb.poo.menu.controller;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.additional.AdditionalNotFoundException;
import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BaseController<T> {
    @GetMapping("/{id}")
    ResponseEntity<T> findById(@PathVariable Long id) throws ProductNotFoundException;

    @PostMapping
    ResponseEntity<T> create(@RequestBody T entity) throws InvalidFieldException;

    @PutMapping("/{id}")
    ResponseEntity<T> update(@PathVariable Long id, @RequestBody T entity) throws ProductNotFoundException, InvalidFieldException, AdditionalNotFoundException;

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable Long id) throws ProductNotFoundException;

}
