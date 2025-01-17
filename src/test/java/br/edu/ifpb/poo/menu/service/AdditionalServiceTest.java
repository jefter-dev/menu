package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.model.Additional;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AdditionalServiceTest {
    @Autowired
    private AdditionalService additionalService;

    @Test
    void findByName() {
        String nameFind = "Bacon";
        List<Additional> additional = additionalService.findByName(nameFind);

        System.out.println(additional);
    }
}