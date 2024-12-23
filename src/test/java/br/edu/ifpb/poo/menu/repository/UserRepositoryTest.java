package br.edu.ifpb.poo.menu.repository;

import br.edu.ifpb.poo.menu.model.Address;
import br.edu.ifpb.poo.menu.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

//    ALTER SEQUENCE person_seq INCREMENT BY 1;

    @Test
    public void shouldSaveUser() {
        User userRegister = userRepository.findById(302L).orElse(null); // USER ADMIN
        System.out.println(userRegister);

        String newNameUser = "USER TEST 2";

        User user = new User();
        user.setName(newNameUser);
        user.setEmail("user-test-2@menu.poo");
        user.setAdmin(true);
        user.setUserRegister(userRegister);
        Address address = new Address("Rua Cicero Galdino, Res Madrid", "58135-000", "CASA 12", "Esperança");
        user.setAddress(address);

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals(newNameUser, savedUser.getName(), "The user name should match the expected value");
    }
}
