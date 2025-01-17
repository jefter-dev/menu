package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.database.ForeignKeyViolationException;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ExceptionDatabaseService {

    public static RuntimeException handleDatabaseViolation(DataIntegrityViolationException ex) {
        System.out.println(ex.getMessage());

        Throwable cause = ex.getCause();

        // Tratar violação de chave estrangeira
        if (cause instanceof ConstraintViolationException constraintEx) {
            String constraintName = constraintEx.getConstraintName();
            return new ForeignKeyViolationException("Violação de chave estrangeira detectada: " + constraintName);
        }

        // Tratar violação de restrição NOT NULL
        if (cause instanceof PropertyValueException propertyEx) {
            String propertyName = propertyEx.getPropertyName();
            return new IllegalArgumentException("Violação de restrição NOT NULL. O campo '" + propertyName + "' não pode ser nulo.");
        }

        // Caso não seja uma das exceções esperadas, lançar uma exceção genérica
        return new RuntimeException("Erro desconhecido de violação de integridade de dados. [" + ex.getCause() + "]: " + ex.getMessage());
    }
}
