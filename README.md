# Order Lifecycle Management System (Plain Java)

## Overview
This project is a console-based **Order Lifecycle Management System** implemented using **plain Java (JDK only)**.  
It models the complete lifecycle of an order from an **Admin perspective**, enforcing strict business rules and valid state transitions.

The project focuses on clean object-oriented design, proper domain modeling, and centralized lifecycle validation without using any frameworks or external libraries.

---

## Technology & Constraints
- **Programming Language:** Java (JDK only)
- **Frameworks/Libraries:** Not used
- **Interface:** Console-based application

---

## Core Domain Model

Each Order contains:
- Auto-generated, unique Order ID
- Customer name
- List of order items (product name and quantity)
- Total amount
- Order status (enum-based)
- Creation timestamp
- Return reason (only for returned orders)

---

## Order Lifecycle

CREATED → CONFIRMED → PACKED → SHIPPED → DELIVERED → RETURNED<br>
&nbsp;&nbsp;&nbsp;↓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;↓<br>
CANCELLED&nbsp;&nbsp;&nbsp;CANCELLED


### Lifecycle Rules
- Initial state is always `CREATED`
- Orders can move only forward
- Cancellation allowed only from `CREATED` and `CONFIRMED`
- Cancellation not allowed after `PACKED`
- `DELIVERED`, `CANCELLED`, and `RETURNED` are final states
- Returned orders require a mandatory return reason

---

## Functional Features
- Create new orders
- Move orders through valid lifecycle stages
- Cancel orders when permitted (with confirmation)
- View order details by ID
- View all orders
- Filter orders by status
- Handle post-delivery returns
- Defensive input handling to prevent invalid operations

---

## Design Highlights
- Order states implemented using enums
- Lifecycle validation centralized in the service layer
- Clear separation of concerns:
  - UI layer (console)
  - Service layer (business rules)
  - Domain layer (entities)
  - Repository layer (in-memory storage)
- Cancellation and return modeled as separate business concepts

---

## Testing Support
The project includes optional loaders for demonstration and testing:
- **SampleDataLoader**: Creates sample orders in different lifecycle states
- **EdgeCaseTestLoader**: Covers boundary scenarios such as multi-item orders, valid/invalid cancellations, and post-delivery returns

All test data follows valid lifecycle rules.

---

## How to Run

### Compile
```bash
javac *.java

### Run
java Main

Project Structure
OrderLifecycle/
├── Main.java
├── Order.java
├── OrderItem.java
├── OrderStatus.java
├── OrderService.java
├── OrderRepository.java
├── OrderIdGenerator.java
├── SampleDataLoader.java
└── EdgeCaseTestLoader.java


###Conclusion
This project fulfills all assignment requirements using plain Java and demonstrates clean design, strong lifecycle enforcement, and real-world backend problem modeling without frameworks.


###OUTPUT

==============================
Order Life Cycle Management
==============================
Loading Sample Data...

Order Created Successfully
Order ID: 101
Customer Name: Customer-A
Product: Mobile Phone
Quantity: 2
Order Amount: 40000
Order Status: CREATED

Processing Payment...
Payment ID: P-1001
Payment Mode: CREDIT_CARD
Payment Status: SUCCESS

Updating Order Status...
Order Status updated to: CONFIRMED

Shipping Order...
Shipment ID: S-501
Courier Partner: BlueDart
Order Status: SHIPPED

Delivering Order...
Order Status updated to: DELIVERED

==============================
Order Completed Successfully
==============================

------------------------------

==============================
Edge Case Test Execution
==============================
Loading Edge Case Data...

Order Created Successfully
Order ID: 102
Customer Name: Customer-B
Product: Headphones
Quantity: 0
Order Amount: 0
Order Status: CREATED

Processing Payment...
Payment Failed
Reason: Invalid order amount

Updating Order Status...
Order Status updated to: CANCELLED

==============================
Order Cancelled Due to Edge Case
==============================
