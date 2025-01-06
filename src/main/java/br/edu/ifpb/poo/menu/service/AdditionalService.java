package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.model.Additional;
import br.edu.ifpb.poo.menu.repository.AdditionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdditionalService {
    @Autowired
    private AdditionalRepository additionalRepository;

    public List<Additional> findByName(String name) {
        return additionalRepository.findByName(name);
    }
}
