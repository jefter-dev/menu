package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.cart.CartItemAdditionalNotFoundException;
import br.edu.ifpb.poo.menu.exception.cart.CartNotFoundException;
import br.edu.ifpb.poo.menu.exception.client.ClientNotFoundException;
import br.edu.ifpb.poo.menu.model.Cart;
import br.edu.ifpb.poo.menu.model.Client;
import br.edu.ifpb.poo.menu.repository.CartItemRepository;
import br.edu.ifpb.poo.menu.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public Cart createCart(Cart cart, Client client) throws CartItemAdditionalNotFoundException, ClientNotFoundException {
        if (cart == null) {
            throw new CartItemAdditionalNotFoundException("Carrinho não encontrado.");
        }

        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado.");
        }

        try {
            cart.setClient(client);
            return cartRepository.save(cart);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public Cart findCartWithDetails(Cart cart) throws CartNotFoundException {
        if (cart == null) {
            throw new CartNotFoundException("Carrinho não encontrado.");
        }

        Cart cartFind = cartRepository.findCartWithItemsAndProductsAndAdditional(cart.getId());
        if(cartFind == null) {
            throw new CartNotFoundException("Carrinho inexistente.");
        }
        return cartFind;
    }

    public void emptyCart(Cart cart) throws CartNotFoundException {
        if (cart == null) {
            throw new CartNotFoundException("Carrinho não encontrado.");
        }

        Cart cartFind = this.findCartWithDetails(cart);
        if (cartFind == null) {
            throw new CartNotFoundException("Carrinho não existe.");
        }

        // Limpar os itens do carrinho
        cartFind.getCartItems().clear();

        try {
            // Salvar o carrinho atualizado
            cartRepository.save(cartFind);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    // Método para buscar o cart de um client
    public Cart findCartByClient(Client client) throws CartNotFoundException, ClientNotFoundException {
        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado.");
        }

        Cart cart = cartRepository.findCartWithItemsAndProductsAndAdditionalByClient(client);
        if (cart == null) {
            throw new CartNotFoundException("Carrinho não encontrado para este cliente.");
        }

        return cart;
    }
}
