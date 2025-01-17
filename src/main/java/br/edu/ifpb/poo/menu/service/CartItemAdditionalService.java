package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.cart.CartItemAdditionalException;
import br.edu.ifpb.poo.menu.exception.additional.AdditionalNotFoundException;
import br.edu.ifpb.poo.menu.model.Additional;
import br.edu.ifpb.poo.menu.model.CartItem;
import br.edu.ifpb.poo.menu.model.CartItemAdditional;
import br.edu.ifpb.poo.menu.repository.CartItemAdditionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CartItemAdditionalService {
    @Autowired
    private CartItemAdditionalRepository cartItemAdditionalRepository;

    public CartItemAdditional addItemAdditionalCart(CartItem cartItem, Additional additional, Integer quantity) throws CartItemAdditionalException, AdditionalNotFoundException {
        if (cartItem == null) {
            throw new CartItemAdditionalException("Item do carrinho não encontrado.");
        }

        if (additional == null) {
            throw new AdditionalNotFoundException("Adicional não encontrado.");
        }

        try {
            CartItemAdditional newCartItemAdditional = new CartItemAdditional(cartItem, additional, quantity);
            return cartItemAdditionalRepository.save(newCartItemAdditional);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }
}
