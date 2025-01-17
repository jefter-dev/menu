package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.database.ForeignKeyViolationException;
import br.edu.ifpb.poo.menu.exception.database.UniqueConstraintViolationException;
import org.hibernate.PropertyValueException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ExceptionDatabaseService {

    public static RuntimeException handleDatabaseViolation(DataIntegrityViolationException ex) {
        Throwable cause = ex.getCause();

        System.out.println("Causa da exceção: " + (cause != null ? cause.getClass().getName() : "null"));
        System.out.println("Mensagem original: " + ex.getMessage()); // DEBUG

        // Tratar violação de chave única (PostgreSQL, via PSQLException)
        if (cause instanceof org.postgresql.util.PSQLException psqlEx) {
            String serverMessage = psqlEx.getServerErrorMessage().getDetail();
            if (serverMessage != null) {
                // Verificar nomes de restrições para chaves únicas
                if (serverMessage.contains("Key (username)")) {
                    return new UniqueConstraintViolationException("O nome de usuário já está em uso. Escolha outro.");
                } else if (serverMessage.contains("Key (email)")) {
                    return new UniqueConstraintViolationException("O email já está cadastrado. Use outro.");
                } else if (serverMessage.contains("Key (cnpj)")) {
                    return new UniqueConstraintViolationException("O CNPJ já está cadastrado. Use outro.");
                }
            }
        }

        // Tratar violação de chave estrangeira ou outras restrições via ConstraintViolationException
        if (cause instanceof ConstraintViolationException constraintEx) {
            String constraintName = constraintEx.getConstraintName();

            if (constraintName != null) {
                // Mensagens personalizadas para restrições de chave única
                switch (constraintName) {
                    case "unique_username":
                        return new UniqueConstraintViolationException("O nome de usuário já está em uso. Escolha outro.");
                    case "unique_email_user":
                        return new UniqueConstraintViolationException("O email de usuário já está cadastrado. Use outro.");
                    case "unique_email_client":
                        return new UniqueConstraintViolationException("O email de cliente já está cadastrado. Use outro.");
                    case "unique_cnpj":
                        return new UniqueConstraintViolationException("O CNPJ já está cadastrado. Use outro.");
                    case "unique_client":
                        return new UniqueConstraintViolationException("O cliente associado ao carrinho já existe.");
                    case "category_product_category_id_product_id_key":
                        return new UniqueConstraintViolationException("A combinação de categoria e produto já está associada.");
                    case "product_additional_product_id_additional_id_key":
                        return new UniqueConstraintViolationException("A combinação de produto e adicional já está associada.");
                    default:
                        break;
                }

                // Mensagens personalizadas para chaves estrangeiras
                switch (constraintName) {
                    case "additional_user_id_fkey":
                        return new ForeignKeyViolationException("O usuário não pode ser deletado, pois está associado a um adicional.");
                    case "fk_client":
                        return new ForeignKeyViolationException("O cliente não pode ser deletado, pois está associado a um carrinho.");
                    case "cart_item_cart_id_fkey":
                        return new ForeignKeyViolationException("O carrinho não pode ser deletado, pois está associado a um item de carrinho.");
                    case "cart_item_product_id_fkey":
                        return new ForeignKeyViolationException("O produto não pode ser deletado, pois está associado a um item de carrinho.");
                    case "cart_item_additional_additional_id_fkey":
                        return new ForeignKeyViolationException("O adicional não pode ser deletado, pois está associado a um item de carrinho.");
                    case "cart_item_additional_cart_item_id_fkey":
                        return new ForeignKeyViolationException("O item de carrinho não pode ser deletado, pois está associado a um adicional.");
                    case "category_user_id_fkey":
                        return new ForeignKeyViolationException("O usuário não pode ser deletado, pois está associado a uma categoria.");
                    case "category_product_category_id_fkey":
                        return new ForeignKeyViolationException("A categoria não pode ser deletada, pois está associada a um produto.");
                    case "category_product_product_id_fkey":
                        return new ForeignKeyViolationException("O produto não pode ser deletado, pois está associado a uma categoria.");
                    case "client_user_id_fkey":
                        return new ForeignKeyViolationException("O usuário não pode ser deletado, pois está associado a um cliente.");
                    case "fksi3b7wvs7k0ddqntb4mmcfu37":
                        return new ForeignKeyViolationException("O horário de funcionamento não pode ser deletado, pois está associado a um usuário.");
                    case "order_client_id_fkey":
                        return new ForeignKeyViolationException("O cliente não pode ser deletado, pois está associado a um pedido.");
                    case "order_user_id_fkey":
                        return new ForeignKeyViolationException("O usuário não pode ser deletado, pois está associado a um pedido.");
                    case "order_item_order_id_fkey":
                        return new ForeignKeyViolationException("O pedido não pode ser deletado, pois está associado a um item de pedido.");
                    case "order_item_product_id_fkey":
                        return new ForeignKeyViolationException("O produto não pode ser deletado, pois está associado a um item de pedido.");
                    case "order_item_additional_additional_id_fkey":
                        return new ForeignKeyViolationException("O adicional não pode ser deletado, pois está associado a um item de pedido.");
                    case "order_item_additional_order_item_id_fkey":
                        return new ForeignKeyViolationException("O item de pedido não pode ser deletado, pois está associado a um adicional.");
                    case "product_user_id_fkey":
                        return new ForeignKeyViolationException("O usuário não pode ser deletado, pois está associado a um produto.");
                    case "product_additional_additional_id_fkey":
                        return new ForeignKeyViolationException("O adicional não pode ser deletado, pois está associado a um produto.");
                    case "product_additional_product_id_fkey":
                        return new ForeignKeyViolationException("O produto adicional não pode ser deletado, pois está associado a um produto.");
                    case "user_user_register_fkey":
                        return new ForeignKeyViolationException("O registro de usuário não pode ser deletado, pois está associado a um usuário.");
                    default:
                        return new ForeignKeyViolationException("Violação de chave estrangeira detectada: " + constraintName);
                }
            }

            return new ForeignKeyViolationException("Violação de chave estrangeira não identificada.");
        }

        // Tratar violação de restrição NOT NULL
        if (cause instanceof PropertyValueException propertyEx) {
            String propertyName = propertyEx.getPropertyName();
//            return new IllegalArgumentException("Violação de restrição NOT NULL. O campo '" + propertyName + "' não pode ser nulo.");
            return new IllegalArgumentException("O campo '" + propertyName + "' não pode ser nulo.");
        }

        // Caso não seja uma das exceções esperadas, lançar uma exceção genérica
        return new RuntimeException("Erro desconhecido de violação de integridade de dados. [" + ex.getCause() + "]: " + ex.getMessage());
    }
}
