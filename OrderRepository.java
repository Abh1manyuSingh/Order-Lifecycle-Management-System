import java.util.*;

public class OrderRepository {

    private Map<Long, Order> orderStore = new HashMap<>();

    public void save(Order order) {
        orderStore.put(order.getOrderId(), order);
    }

    public Order findById(long orderId) {
        return orderStore.get(orderId);
    }

    public List<Order> findAll() {
        return new ArrayList<>(orderStore.values());
    }

    public List<Order> findByStatus(OrderStatus status) {
        List<Order> result = new ArrayList<>();
        for (Order order : orderStore.values()) {
            if (order.getStatus() == status) {
                result.add(order);
            }
        }
        return result;
    }
}