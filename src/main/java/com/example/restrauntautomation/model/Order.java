package com.example.restrauntautomation.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represents a customer order placed at a specific table by a wait staff member.
 * Contains a list of items ordered and tracks the order status.
 */
public class Order {

    // Simple sequence generator for unique order IDs
    private static final AtomicLong idCounter = new AtomicLong(1);

    private final long orderId;
    private final int tableNumber; // Reference to the Table
    private final String waitStaffId; // Reference to the WaitStaff
    private final List<OrderItem> items;
    private final LocalDateTime orderTime;
    private OrderStatus status;

    /**
     * Constructs a new Order.
     *
     * @param tableNumber The number of the table placing the order.
     * @param waitStaffId The ID of the wait staff member taking the order.
     */
    public Order(int tableNumber, String waitStaffId) {
         if (tableNumber <= 0) {
            throw new IllegalArgumentException("Table number must be positive.");
        }
         if (waitStaffId == null || waitStaffId.trim().isEmpty()) {
            throw new IllegalArgumentException("Wait staff ID cannot be null or empty.");
        }
        this.orderId = idCounter.getAndIncrement();
        this.tableNumber = tableNumber;
        this.waitStaffId = waitStaffId.trim();
        this.items = new ArrayList<>();
        this.orderTime = LocalDateTime.now();
        this.status = OrderStatus.PLACED; // Initial status
    }

    /**
     * Gets the unique ID of the order.
     *
     * @return The order ID.
     */
    public long getOrderId() {
        return orderId;
    }

    /**
     * Gets the table number associated with this order.
     *
     * @return The table number.
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Gets the ID of the wait staff member who took the order.
     *
     * @return The wait staff ID.
     */
    public String getWaitStaffId() {
        return waitStaffId;
    }

    /**
     * Gets the time the order was placed.
     *
     * @return The order timestamp.
     */
    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    /**
     * Gets the current status of the order.
     *
     * @return The order status.
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     *
     * @param status The new status.
     */
    public void setStatus(OrderStatus status) {
        this.status = Objects.requireNonNull(status, "Order status cannot be null.");
    }

    /**
     * Gets an unmodifiable list of items in this order.
     *
     * @return The list of OrderItems.
     */
    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Adds an item to the order. If the same menu item already exists,
     * it increases the quantity; otherwise, it adds a new OrderItem.
     *
     * @param menuItem The MenuItem to add.
     * @param quantity The quantity to add (must be positive).
     */
    public void addItem(MenuItem menuItem, int quantity) {
        Objects.requireNonNull(menuItem, "Cannot add a null MenuItem to the order.");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }

        // Check if item already exists in the order
        for (OrderItem existingItem : items) {
            if (existingItem.getMenuItem().equals(menuItem)) {
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                return; // Item found and quantity updated
            }
        }
        // Item not found, add as a new OrderItem
        items.add(new OrderItem(menuItem, quantity));
    }

     /**
     * Removes an item completely from the order.
     *
     * @param menuItem The MenuItem to remove.
     * @return true if the item was found and removed, false otherwise.
     */
    public boolean removeItem(MenuItem menuItem) {
        Objects.requireNonNull(menuItem, "Cannot remove a null MenuItem from the order.");
        return items.removeIf(orderItem -> orderItem.getMenuItem().equals(menuItem));
    }

    /**
     * Calculates the total price of the order by summing the prices of all OrderItems.
     *
     * @return The total order price.
     */
    public BigDecimal getTotalPrice() {
        return items.stream()
                    .map(OrderItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Returns a string representation of the order.
     *
     * @return A string describing the order.
     */
    @Override
    public String toString() {
        return "Order{" +
               "orderId=" + orderId +
               ", tableNumber=" + tableNumber +
               ", waitStaffId='" + waitStaffId + '\'' +
               ", orderTime=" + orderTime +
               ", status=" + status +
               ", totalItems=" + items.size() +
               ", totalPrice=" + getTotalPrice() +
               '}';
    }

    /**
     * Checks if this Order is equal to another object.
     * Equality is based on the unique orderId.
     *
     * @param o The object to compare with.
     * @return true if the objects have the same orderId, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId;
    }

    /**
     * Generates a hash code for the Order.
     * Based on the unique orderId.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    /**
     * Represents the possible statuses of a customer order.
     */
    public enum OrderStatus {
        PLACED,     // Order taken, sent to kitchen/bar
        PREPARING,  // Actively being prepared
        READY,      // Ready for pickup/delivery to table
        SERVED,     // Delivered to the table
        PAID,       // Bill settled
        CANCELLED   // Order cancelled
    }
}
