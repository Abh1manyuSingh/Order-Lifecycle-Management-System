import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final OrderService orderService = new OrderService();

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println(" Order Lifecycle Management System ");
        System.out.println("====================================");

        SampleDataLoader.load(orderService);// Load sample data
        EdgeCaseTestLoader.load(orderService); // Load edge case data

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        createOrder();
                        break;

                    case 2:
                        updateOrderStatus();
                        break;

                    case 3:
                        viewOrderById();
                        break;

                    case 4:
                        viewAllOrders();
                        break;

                    case 5:
                        filterOrdersByStatus();
                        break;

                    case 0:
                        running = false;
                        System.out.println("Exiting application...");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // ---------------- MENU METHODS ----------------

    private static void printMenu() {
        System.out.println("\n------ MENU ------");
        System.out.println("1. Create Order");
        System.out.println("2. Update Order Status");
        System.out.println("3. View Order by ID");
        System.out.println("4. View All Orders");
        System.out.println("5. Filter Orders by Status");
        System.out.println("0. Exit");
    }

    // ---------------- FEATURE METHODS ----------------

    private static void createOrder() {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        List<OrderItem> items = new ArrayList<>();

        while (true) {
            System.out.print("Enter product name: ");
            String productName = scanner.nextLine();

            int quantity = readInt("Enter quantity: ");
            items.add(new OrderItem(productName, quantity));

            System.out.print("Add another item? (yes/no): ");
            String more = scanner.nextLine();
            if (!more.equalsIgnoreCase("yes")) {
                break;
            }
        }

        double totalAmount = readDouble("Enter total amount: ");

        Order order = orderService.createOrder(customerName, items, totalAmount);
        System.out.println("Order created successfully!");
        System.out.println(order);
    }

    private static void updateOrderStatus() {
        long orderId = readLong("Enter order ID: ");
        Order order = orderService.getOrder(orderId);

        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        if (order.getStatus() == OrderStatus.CANCELLED) {
            System.out.println("This order is already cancelled. No further actions allowed.");
            return;
        }

        OrderStatus currentStatus = order.getStatus();
        System.out.println("Current Status: " + currentStatus);

        List<OrderStatus> validStatuses =
            orderService.getNextValidStatuses(currentStatus);

        if (validStatuses.isEmpty()) {
            System.out.println("No further transitions allowed.");
            return;
        }

        System.out.println("Select next status:");
        for (int i = 0; i < validStatuses.size(); i++) {
            System.out.println((i + 1) + ". " + validStatuses.get(i));
        }

        int choice = readInt("Enter choice: ");

        if (choice < 1 || choice > validStatuses.size()) {
            System.out.println("Invalid selection.");
            return;
    }

        OrderStatus newStatus = validStatuses.get(choice - 1);

        if (newStatus == OrderStatus.RETURNED) {
            System.out.print("Enter return reason: ");
            String reason = scanner.nextLine();

            if (reason.trim().isEmpty()) {
                System.out.println("Return reason cannot be empty.");
                return;
            }

            order.setReturnReason(reason);
        }

        if (newStatus == OrderStatus.CANCELLED) {
            System.out.print("Are you sure you want to cancel this order? (yes/no): ");
            String confirm = scanner.nextLine();

            if (!confirm.equalsIgnoreCase("yes")) {
                System.out.println("Cancellation aborted.");
                return;
            }
        }
        orderService.updateStatus(orderId, newStatus);
        System.out.println("Order status updated to: " + newStatus);

    }

    private static void viewOrderById() {
        long orderId = readLong("Enter order ID: ");
        Order order = orderService.getOrder(orderId);

        if (order == null) {
            System.out.println("Order not found.");
        } else {
            System.out.println(order);
        }
    }

    private static void viewAllOrders() {
        List<Order> orders = orderService.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders available.");
            return;
        }

        for (Order order : orders) {
            System.out.println(order);
        }

        System.out.println("\nTotal Orders: " + orders.size());
    }

    private static void filterOrdersByStatus() {

        OrderStatus[] statuses = OrderStatus.values();

        System.out.println("Select status to filter:");
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i]);
        }

        int choice = readInt("Enter choice: ");

        if (choice < 1 || choice > statuses.length) {
            System.out.println("Invalid selection.");
            return;
        }

        OrderStatus status = statuses[choice - 1];
        List<Order> orders = orderService.getOrdersByStatus(status);

        if (orders.isEmpty()) {
            System.out.println("No orders found with status: " + status);
            return;
        }

        for (Order order : orders) {
            System.out.println(order);
        }

        System.out.println("\nTotal Orders with status " + status + ": " + orders.size());
    }

    // ---------------- INPUT HELPERS ----------------

    private static int readInt(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // clear buffer
        return value;
    }

    private static long readLong(String message) {
        System.out.print(message);
        while (!scanner.hasNextLong()) {
            System.out.print("Invalid input. Enter a valid ID: ");
            scanner.next();
        }
        long value = scanner.nextLong();
        scanner.nextLine();
        return value;
    }

    private static double readDouble(String message) {
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Enter a valid amount: ");
            scanner.next();
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
}