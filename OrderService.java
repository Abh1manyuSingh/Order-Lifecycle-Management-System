import java.util.ArrayList;
import java.util.List;

public class OrderService {

    private OrderRepository repository = new OrderRepository();

    public Order createOrder(String customerName, List<OrderItem> items, double totalAmount) {
        long id = OrderIdGenerator.generateId();
        Order order = new Order(id, customerName, items, totalAmount);
        repository.save(order);
        return order;
    }

    public void updateStatus(long orderId, OrderStatus newStatus) {
        Order order = repository.findById(orderId);

        if (order == null) {
            throw new IllegalArgumentException("Order not found");
        }

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled orders cannot be modified.");
        }

        if (!isValidTransition(order.getStatus(), newStatus)) {
            throw new IllegalStateException("Invalid state transition");
        }

        order.setStatus(newStatus);
    }

    private boolean isValidTransition(OrderStatus current, OrderStatus next) {

        switch (current) {
            case CREATED:
                return next == OrderStatus.CONFIRMED || next == OrderStatus.CANCELLED;

            case CONFIRMED:
                return next == OrderStatus.PACKED || next == OrderStatus.CANCELLED;

            case PACKED:
                return next == OrderStatus.SHIPPED;

            case SHIPPED:
                return next == OrderStatus.DELIVERED;

            case DELIVERED:
                return next == OrderStatus.RETURNED;

            default:
                return false;
        }
    }

    public Order getOrder(long orderId) {
        return repository.findById(orderId);
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return repository.findByStatus(status);
    }

    public List<OrderStatus> getNextValidStatuses(OrderStatus currentStatus) {
    List<OrderStatus> validStatuses = new ArrayList<>();

    switch (currentStatus) {
        case CREATED:
            validStatuses.add(OrderStatus.CONFIRMED);
            validStatuses.add(OrderStatus.CANCELLED);
            break;

        case CONFIRMED:
            validStatuses.add(OrderStatus.PACKED);
            validStatuses.add(OrderStatus.CANCELLED);
            break;

        case PACKED:
            validStatuses.add(OrderStatus.SHIPPED);
            break;

        case SHIPPED:
            validStatuses.add(OrderStatus.DELIVERED);
            break;

        case DELIVERED:
            validStatuses.add(OrderStatus.RETURNED);
            break;

        default:
            break;
    }
    return validStatuses;
}
}