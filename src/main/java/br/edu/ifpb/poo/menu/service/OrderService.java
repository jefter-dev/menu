package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.cart.CartNotFoundException;
import br.edu.ifpb.poo.menu.exception.client.ClientNotFoundException;
import br.edu.ifpb.poo.menu.exception.order.InvalidStatusOrderException;
import br.edu.ifpb.poo.menu.exception.order.OrderNotFoundException;
import br.edu.ifpb.poo.menu.model.*;
import br.edu.ifpb.poo.menu.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
    private static final OrderStatus STATUS_INITIAL = OrderStatus.PENDING;

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;

    public List<Order> findOrdersByClientId(Client client) throws ClientNotFoundException, OrderNotFoundException {
        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado.");
        }

        List<Order> orderFind = orderRepository.findOrdersByClientId(client.getId());
        if(orderFind == null) {
            throw new OrderNotFoundException("Pedido inexistente.");
        }
        return orderFind;
    }

    public Order findOrderWithDetails(Order order) throws OrderNotFoundException, OrderNotFoundException {
        if (order == null) {
            throw new OrderNotFoundException("Carrinho não encontrado.");
        }

        Order orderFind = orderRepository.findOrderItemsAndProductsAndAdditional(order.getId());
        if(orderFind == null) {
            throw new OrderNotFoundException("Pedido inexistente.");
        }

        return orderFind;
    }

    public Order createOrder(Cart cart, User user) throws CartNotFoundException {
        validateCart(cart);

        Cart cartFind = cartService.findCartWithDetails(cart);
        validateCart(cartFind);

        Order newOrder = createOrderFromCart(cartFind, user);
        return saveOrder(newOrder);
    }

    private void validateCart(Cart cart) throws CartNotFoundException {
        if (cart == null) {
            throw new CartNotFoundException("Carrinho não encontrado.");
        }
    }

    private Order createOrderFromCart(Cart cart, User user) {
        Order newOrder = new Order();
        newOrder.setClient(cart.getClient());
        newOrder.setTotal(cart.getTotal());
        newOrder.setUser(user);
        newOrder.setStatus("Pending");

        if (cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
            for (CartItem cartItem : cart.getCartItems()) {
                OrderItem orderItem = convertCartItemToOrderItem(cartItem, newOrder);
                newOrder.getOrderItems().add(orderItem);
            }
        }
        return newOrder;
    }

    private OrderItem convertCartItemToOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getProduct() != null && cartItem.getProduct().getPrice() != null
                ? cartItem.getProduct().getPrice()
                : BigDecimal.ZERO);

        if (cartItem.getCartItemAdditionals() != null && !cartItem.getCartItemAdditionals().isEmpty()) {
            for (CartItemAdditional cartItemAdditional : cartItem.getCartItemAdditionals()) {
                OrderItemAdditional orderItemAdditional = convertCartItemAdditionalToOrderItemAdditional(cartItemAdditional, orderItem);
                orderItem.getOrderItemAdditional().add(orderItemAdditional);
            }
        }
        return orderItem;
    }

    private OrderItemAdditional convertCartItemAdditionalToOrderItemAdditional(CartItemAdditional cartItemAdditional, OrderItem orderItem) {
        OrderItemAdditional orderItemAdditional = new OrderItemAdditional();
        orderItemAdditional.setOrderItem(orderItem);
        orderItemAdditional.setAdditional(cartItemAdditional.getAdditional());
        orderItemAdditional.setQuantity(cartItemAdditional.getQuantity());
        orderItemAdditional.setPrice(cartItemAdditional.getAdditional() != null && cartItemAdditional.getAdditional().getPrice() != null
                ? cartItemAdditional.getAdditional().getPrice()
                : BigDecimal.ZERO);
        return orderItemAdditional;
    }

    private Order saveOrder(Order order) {
        try {
            return orderRepository.save(order);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public Order updateOrderStatus(Order order, String newStatus) throws OrderNotFoundException, InvalidStatusOrderException {
        if (order == null) {
            throw new OrderNotFoundException("O status não pode ser vazio.");
        }

        if(newStatus == null) {
            throw new InvalidStatusOrderException("O status não pode ser vazio.");
        }

        Order orderFind = this.findOrderWithDetails(order);
        if(orderFind == null) {
            throw new OrderNotFoundException("Pedido inexistente.");
        }
        orderFind.setStatus(newStatus);

        // Salvar a ordem com o novo status
        return saveOrder(orderFind);
    }

}
