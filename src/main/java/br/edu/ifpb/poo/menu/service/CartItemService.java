package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.cart.CartItemAdditionalNotFoundException;
import br.edu.ifpb.poo.menu.exception.database.ForeignKeyViolationException;
import br.edu.ifpb.poo.menu.exception.product.ProductNotFoundException;
import br.edu.ifpb.poo.menu.model.Cart;
import br.edu.ifpb.poo.menu.model.CartItem;
import br.edu.ifpb.poo.menu.model.Product;
import br.edu.ifpb.poo.menu.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public CartItem addItemCart(Cart cart, Product product)
            throws CartItemAdditionalNotFoundException, ProductNotFoundException, ForeignKeyViolationException {
        if (cart == null) {
            throw new CartItemAdditionalNotFoundException("Carrinho não encontrado.");
        }

        if (product == null) {
            throw new ProductNotFoundException("Produto não encontrado.");
        }

        try {
            CartItem newCartItem = new CartItem(cart, product, 1);
            return cartItemRepository.save(newCartItem);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

}
