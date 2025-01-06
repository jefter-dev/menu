package br.edu.ifpb.poo.menu.exception.database;

public class ForeignKeyViolationException extends RuntimeException {
    public ForeignKeyViolationException(String message) {
        super(message);
    }
}