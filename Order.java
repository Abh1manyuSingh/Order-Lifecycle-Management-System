import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private final long orderId;
    private String customerName;
    private List<OrderItem> items;
    private double totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private String returnReason;

    public Order(long orderId, String customerName, List<OrderItem> items, double totalAmount) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = OrderStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public long getOrderId() {
        return orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getReturnReason() {
        return returnReason;
    }

    void setStatus(OrderStatus status) {
        this.status = status;
    }

    void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n----------------------------------\n");
        sb.append("Order ID      : ").append(orderId).append("\n");
        sb.append("Customer Name: ").append(customerName).append("\n");
        sb.append("Status       : ").append(status).append("\n");

        // ✅ Return reason printed ONLY inside order block
        if (status == OrderStatus.RETURNED && returnReason != null) {
            sb.append("Return Reason: ").append(returnReason).append("\n");
        }

        sb.append("Created At   : ").append(createdAt).append("\n");
        sb.append("Items:\n");

        for (OrderItem item : items) {
            sb.append("  - ")
            .append(item.getProductName())
            .append(" | Qty: ")
            .append(item.getQuantity())
            .append("\n");
        }

        sb.append("Total Amount : ").append(totalAmount).append("\n");
        sb.append("----------------------------------");

        return sb.toString();
    }
}