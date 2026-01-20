import java.util.ArrayList;
import java.util.List;

public class EdgeCaseTestLoader {

    public static void load(OrderService orderService) {

        System.out.println("Loading EDGE CASE test data...\n");

        // EDGE CASE 1: Order with multiple items
        List<OrderItem> multiItems = new ArrayList<>();
        multiItems.add(new OrderItem("Pen", 10));
        multiItems.add(new OrderItem("Notebook", 5));
        multiItems.add(new OrderItem("Bag", 1));

        Order multiItemOrder = orderService.createOrder(
                "MultiItemUser",
                multiItems,
                1500
        );

        // EDGE CASE 2: Cancel from CONFIRMED (valid cancellation)
        List<OrderItem> cancelItems = new ArrayList<>();
        cancelItems.add(new OrderItem("Mousepad", 1));

        Order cancelConfirmed = orderService.createOrder(
                "CancelAtConfirmed",
                cancelItems,
                300
        );
        orderService.updateStatus(cancelConfirmed.getOrderId(), OrderStatus.CONFIRMED);
        orderService.updateStatus(cancelConfirmed.getOrderId(), OrderStatus.CANCELLED);

        // EDGE CASE 3: Delivered → Returned with reason
        List<OrderItem> returnItems = new ArrayList<>();
        returnItems.add(new OrderItem("Shoes", 1));

        Order returnedOrder = orderService.createOrder(
                "ReturnUser",
                returnItems,
                2500
        );
        orderService.updateStatus(returnedOrder.getOrderId(), OrderStatus.CONFIRMED);
        orderService.updateStatus(returnedOrder.getOrderId(), OrderStatus.PACKED);
        orderService.updateStatus(returnedOrder.getOrderId(), OrderStatus.SHIPPED);
        orderService.updateStatus(returnedOrder.getOrderId(), OrderStatus.DELIVERED);

        // set return reason BEFORE marking as RETURNED
        returnedOrder.setReturnReason("Customer did not accept delivery");
        orderService.updateStatus(returnedOrder.getOrderId(), OrderStatus.RETURNED);

        // EDGE CASE 4: Already CANCELLED order (final state)
        List<OrderItem> cancelledItems = new ArrayList<>();
        cancelledItems.add(new OrderItem("Cable", 1));

        Order alreadyCancelled = orderService.createOrder(
                "AlreadyCancelledUser",
                cancelledItems,
                200
        );
        orderService.updateStatus(alreadyCancelled.getOrderId(), OrderStatus.CANCELLED);

        System.out.println("EDGE CASE test data loaded successfully.\n");
    }
}