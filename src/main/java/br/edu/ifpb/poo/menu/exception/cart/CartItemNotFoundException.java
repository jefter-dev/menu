package br.edu.ifpb.poo.menu.exception.cart;

public class CartItemNotFoundException extends Exception {
    public CartItemNotFoundException(String message) {
        super(message);
    }
}