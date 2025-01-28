package br.edu.ifpb.poo.menu.service;

import br.edu.ifpb.poo.menu.exception.InvalidFieldException;
import br.edu.ifpb.poo.menu.exception.cart.CartNotFoundException;
import br.edu.ifpb.poo.menu.exception.client.ClientNotFoundException;
import br.edu.ifpb.poo.menu.exception.order.InvalidStatusOrderException;
import br.edu.ifpb.poo.menu.exception.order.OrderNotFoundException;
import br.edu.ifpb.poo.menu.exception.user.UserNotFoundException;
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

    public Order getOrderById(Long id) throws UserNotFoundException {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> findOrdersByClientId(Client client) throws ClientNotFoundException, OrderNotFoundException {
        if (client == null) {
            throw new ClientNotFoundException("Cliente não encontrado.");
        }

        List<Order> orderFind = orderRepository.findOrdersByClientId(client.getId());
        if (orderFind == null) {
            throw new OrderNotFoundException("Pedido inexistente.");
        }
        return orderFind;
    }

    public Order findOrderWithDetails(Order order) throws OrderNotFoundException, OrderNotFoundException {
        if (order == null) {
            throw new OrderNotFoundException("Carrinho não encontrado.");
        }

        Order orderFind = orderRepository.findOrderItemsAndProductsAndAdditional(order.getId());
        if (orderFind == null) {
            throw new OrderNotFoundException("Pedido inexistente.");
        }

        return orderFind;
    }

    public Order createOrder(Order order) throws InvalidFieldException {
        validateOrderData(order);

        Order newOrder = new Order();
        newOrder.setClient(order.getClient());
        newOrder.setUser(order.getUser());
        newOrder.setObservations(order.getObservations());
        newOrder.setStatus(OrderStatus.PENDING.toString());

        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            for (OrderItem orderItemData : order.getOrderItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(newOrder);
                orderItem.setProduct(orderItemData.getProduct());
                orderItem.setQuantity(orderItemData.getQuantity());
                orderItem.setPrice(orderItemData.getPrice() != null
                        ? orderItemData.getPrice()
                        : BigDecimal.ZERO);

                if (orderItemData.getOrderItemAdditional() != null) {
                    for (OrderItemAdditional additionalData : orderItemData.getOrderItemAdditional()) {
                        OrderItemAdditional orderItemAdditional = new OrderItemAdditional();
                        orderItemAdditional.setOrderItem(orderItem);
                        orderItemAdditional.setOrderItem(orderItem);
                        orderItemAdditional.setAdditional(additionalData.getAdditional());
                        orderItemAdditional.setQuantity(additionalData.getQuantity());
                        orderItemAdditional.setPrice(additionalData.getPrice() != null
                                ? additionalData.getPrice()
                                : BigDecimal.ZERO);
                        orderItem.getOrderItemAdditional().add(orderItemAdditional);
                    }
                }

                newOrder.getOrderItems().add(orderItem);
            }
        }

//        System.out.println("NEW ORDER: "+newOrder.toStringWithItems());

        // Calcula e define o total do pedido
        newOrder.setTotal(calculateOrderTotal(newOrder));

        try {
            return saveOrder(newOrder);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public Order createOrderWithCart(Cart cart, User user) throws CartNotFoundException {
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

    private void validateOrderData(Order order) throws InvalidFieldException {
        if (order == null) {
            throw new InvalidFieldException("Pedido não pode ser nulo.");
        }
        if (order.getClient() == null) {
            throw new InvalidFieldException("Cliente é obrigatório.");
        }
        if (order.getUser() == null) {
            throw new InvalidFieldException("Usuário é obrigatório.");
        }
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new InvalidFieldException("O pedido deve conter ao menos um item.");
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

        if (newStatus == null) {
            throw new InvalidStatusOrderException("O status não pode ser vazio.");
        }

        Order orderFind = this.findOrderWithDetails(order);
        if (orderFind == null) {
            throw new OrderNotFoundException("Pedido inexistente.");
        }
        orderFind.setStatus(newStatus);

        // Salvar a ordem com o novo status
        return saveOrder(orderFind);
    }

    private BigDecimal calculateOrderTotal(Order order) {
        BigDecimal total = BigDecimal.ZERO;

        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
//                System.out.println("Item total before additional: " + itemTotal); // DEBUG

                // Adiciona o total dos adicionais (se houver)
                if (item.getOrderItemAdditional() != null) {
                    for (OrderItemAdditional additional : item.getOrderItemAdditional()) {
                        BigDecimal additionalTotal = additional.getPrice().multiply(BigDecimal.valueOf(additional.getQuantity()));
//                        System.out.println("Additional total: " + additionalTotal);
                        itemTotal = itemTotal.add(additionalTotal);
                    }
                }

//                System.out.println("Item total after additional: " + itemTotal); DEBUG
                total = total.add(itemTotal);
            }
        }

//        System.out.println("TOTAL ORDER: " + total); // DEBUG
        return total;
    }

    public Order updateOrder(Long orderId, Order updatedOrderData) throws InvalidFieldException, OrderNotFoundException {
        // Verifica se o pedido existe no banco de dados
        Order existingOrder = orderRepository.findOrderItemsAndProductsAndAdditional(orderId);

        // Valida os dados do pedido atualizado
        validateOrderData(updatedOrderData);

        // Atualiza os campos principais do pedido
        existingOrder.setClient(updatedOrderData.getClient());
        existingOrder.setUser(updatedOrderData.getUser());
        existingOrder.setObservations(updatedOrderData.getObservations());
        existingOrder.setStatus(updatedOrderData.getStatus());

        // Remove os itens antigos
        if (existingOrder.getOrderItems() != null && !existingOrder.getOrderItems().isEmpty()) {
            for (OrderItem oldItem : existingOrder.getOrderItems()) {
                // Remove os adicionais associados ao item
                if (oldItem.getOrderItemAdditional() != null) {
                    oldItem.getOrderItemAdditional().clear();
                }
            }
            existingOrder.getOrderItems().clear(); // Limpa a lista de itens do pedido
        }

        // Atualiza os itens do pedido
        if (updatedOrderData.getOrderItems() != null && !updatedOrderData.getOrderItems().isEmpty()) {
            for (OrderItem updatedOrderItem : updatedOrderData.getOrderItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(existingOrder);
                orderItem.setProduct(updatedOrderItem.getProduct());
                orderItem.setQuantity(updatedOrderItem.getQuantity());
                orderItem.setPrice(updatedOrderItem.getPrice() != null
                        ? updatedOrderItem.getPrice()
                        : BigDecimal.ZERO);

                // Atualiza os adicionais do item, se houver
                if (updatedOrderItem.getOrderItemAdditional() != null) {
                    for (OrderItemAdditional updatedAdditional : updatedOrderItem.getOrderItemAdditional()) {
                        OrderItemAdditional orderItemAdditional = new OrderItemAdditional();
                        orderItemAdditional.setOrderItem(orderItem);
                        orderItemAdditional.setAdditional(updatedAdditional.getAdditional());
                        orderItemAdditional.setQuantity(updatedAdditional.getQuantity());
                        orderItemAdditional.setPrice(updatedAdditional.getPrice() != null
                                ? updatedAdditional.getPrice()
                                : BigDecimal.ZERO);
                        orderItem.getOrderItemAdditional().add(orderItemAdditional);
                    }
                }

                existingOrder.getOrderItems().add(orderItem);
            }
        }

        // Recalcula o total do pedido
        existingOrder.setTotal(calculateOrderTotal(existingOrder));

        try {
            // Salva as alterações no banco de dados
            return saveOrder(existingOrder);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }

    public void deleteOrder(Long orderId) throws OrderNotFoundException {
        // Verifica se o pedido existe
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            throw new OrderNotFoundException("Pedido com ID " + orderId + " não encontrado.");
        }

        try {
            // Exclui o pedido
            orderRepository.delete(order);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionDatabaseService.handleDatabaseViolation(ex);
        }
    }
}