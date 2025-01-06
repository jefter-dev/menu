package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.database.ForeignKeyViolationException;
import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
import br.edu.ifpb.poo.menu.model.User;
import br.edu.ifpb.poo.menu.repository.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) throws UserNotFoundException, InvalidUserException {
        if (id == null) {
            throw new InvalidUserException("O usuário fornecido não encontrado.");
        }

        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Service
    public static class ExceptionDatabaseService {

        public static void handleDataIntegrityViolation(DataIntegrityViolationException ex) throws ForeignKeyViolationException {
            Throwable cause = ex.getCause();
            if (cause instanceof ConstraintViolationException constraintEx) {
                String constraintName = constraintEx.getConstraintName();

                if (constraintName != null && constraintName.contains("cart_item_cart_id_fkey")) {
                    throw new ForeignKeyViolationException("Violação de chave estrangeira: Cart");
                }

                if (constraintName != null && constraintName.contains("cart_item_product_id_fkey")) {
                    throw new ForeignKeyViolationException("Violação de chave estrangeira: Product");
                }
            }

            // Adicione mais regras conforme necessário.
            throw ex; // Rethrow genérico se não for tratado aqui.
        }
    }
}
