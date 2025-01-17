package br.edu.ifpb.poo.menu.exception;

import br.edu.ifpb.poo.menu.exception.cart.CartItemAdditionalException;
import br.edu.ifpb.poo.menu.exception.cart.CartItemAdditionalNotFoundException;
import br.edu.ifpb.poo.menu.exception.cart.CartItemNotFoundException;
import br.edu.ifpb.poo.menu.exception.cart.CartNotFoundException;
import br.edu.ifpb.poo.menu.exception.client.ClientNotFoundException;
import br.edu.ifpb.poo.menu.exception.database.ForeignKeyViolationException;
import br.edu.ifpb.poo.menu.exception.database.UniqueConstraintViolationException;
import br.edu.ifpb.poo.menu.exception.order.OrderNotFoundException;
import br.edu.ifpb.poo.menu.exception.additional.AdditionalNotFoundException;
import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.exception.user.InvalidUserException;
import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Invalid parameter format for ID: " + ex.getValue();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Erro de integridade de dados: " + ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Requisição inválida: o corpo da requisição está ausente ou está em formato incorreto.");
    }

    @ExceptionHandler(ForeignKeyViolationException.class)
    public ResponseEntity<String> handleForeignKeyViolationException(ForeignKeyViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UniqueConstraintViolationException.class)
    public ResponseEntity<String> handleUniqueConstraintViolationException(UniqueConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<String> handleInvalidFieldException(InvalidFieldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CartItemAdditionalException.class)
    public ResponseEntity<String> handleCartItemAdditionalException(CartItemAdditionalException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(CartItemAdditionalNotFoundException.class)
    public ResponseEntity<String> handleCartItemAdditionalNotFoundException(CartItemAdditionalNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<String> handleCartItemNotFoundException(CartItemNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<String> handleCartNotFoundException(CartNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<String> handleClientNotFoundException(ClientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AdditionalNotFoundException.class)
    public ResponseEntity<String> handleAdditionalNotFoundException(AdditionalNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<String> handleInvalidUserException(InvalidUserException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro interno no servidor: " + ex.getMessage());
    }
}
