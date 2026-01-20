import java.util.List;

public class SampleDataLoader {

    public static void load(OrderService orderService) {

        System.out.println("Loading sample test data...\n");

        // CREATED
        Order o1 = orderService.createOrder(
                "Alice",
                List.of(new OrderItem("Book", 2)),
                500
        );

        // CONFIRMED
        Order o2 = orderService.createOrder(
                "Bob",
                List.of(new OrderItem("Mouse", 1)),
                1200
        );
        orderService.updateStatus(o2.getOrderId(), OrderStatus.CONFIRMED);

        // PACKED
        Order o3 = orderService.createOrder(
                "Charlie",
                List.of(new OrderItem("Keyboard", 1)),
                2500
        );
        orderService.updateStatus(o3.getOrderId(), OrderStatus.CONFIRMED);
        orderService.updateStatus(o3.getOrderId(), OrderStatus.PACKED);

        // DELIVERED
        Order o4 = orderService.createOrder(
                "David",
                List.of(new OrderItem("Headphones", 1)),
                4000
        );
        orderService.updateStatus(o4.getOrderId(), OrderStatus.CONFIRMED);
        orderService.updateStatus(o4.getOrderId(), OrderStatus.PACKED);
        orderService.updateStatus(o4.getOrderId(), OrderStatus.SHIPPED);
        orderService.updateStatus(o4.getOrderId(), OrderStatus.DELIVERED);

        // CANCELLED
        Order o5 = orderService.createOrder(
                "Eve",
                List.of(new OrderItem("Charger", 1)),
                800
        );
        orderService.updateStatus(o5.getOrderId(), OrderStatus.CANCELLED);

        System.out.println("Sample data loaded successfully.\n");
    }
}