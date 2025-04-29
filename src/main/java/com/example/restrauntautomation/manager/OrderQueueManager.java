package com.example.restrauntautomation.manager;

import com.example.restrauntautomation.model.Order;
import com.example.restrauntautomation.model.Order;
import com.example.restrauntautomation.model.OrderItem;

import java.util.ArrayList; // Added
import java.util.Collections; // Added
import java.util.LinkedList;
import java.util.List; // Added
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Manages the queue of pending restaurant orders.
 * Provides functionality to add orders and display the queue.
 */
public class OrderQueueManager {

    private static final Logger LOGGER = Logger.getLogger(OrderQueueManager.class.getName());
    private final Queue<Order> orderQueue;

    /**
     * Constructs a new OrderQueueManager with an empty order queue.
     */
    public OrderQueueManager() {
        // LinkedList is a common choice for Queue implementations
        this.orderQueue = new LinkedList<>();
        LOGGER.info("Order Queue Manager initialized.");
    }

    /**
     * Adds a new order to the end of the queue.
     *
     * @param order The Order object to add (must not be null).
     * @return true if the order was successfully added, false otherwise (though Queue.offer usually returns true for LinkedList).
     */
    public boolean addOrder(Order order) {
        if (order == null) {
            LOGGER.warning("Attempted to add a null order to the queue.");
            return false;
        }
        // offer is generally preferred over add for bounded queues
        boolean added = orderQueue.offer(order);
        if (added) {
            LOGGER.log(Level.INFO, "Order {0} added to the queue.", order.getOrderId());
            // Console display is removed, GUI controller will handle updates
            // displayQueueToConsole();
        } else {
            LOGGER.log(Level.SEVERE, "Failed to add order {0} to the queue.", order.getOrderId());
        }
        return added;
    }

    /**
     * Retrieves and removes the order at the head of the queue.
     * Returns null if the queue is empty.
     *
     * @return The next Order in the queue, or null if empty.
     */
    public Order processNextOrder() {
        Order nextOrder = orderQueue.poll(); // poll returns null if queue is empty
        if (nextOrder != null) {
            LOGGER.log(Level.INFO, "Processing order {0} from the queue.", nextOrder.getOrderId());
            // Optionally display queue after processing
            // displayQueueToConsole();
        } else {
            LOGGER.info("Order queue is empty. No order to process.");
        }
        return nextOrder;
    }

    /**
     * Retrieves, but does not remove, the order at the head of the queue.
     * Returns null if the queue is empty.
     *
     * @return The next Order in the queue without removing it, or null if empty.
     */
    public Order peekNextOrder() {
        return orderQueue.peek(); // peek returns null if queue is empty
    }

    /**
     * Gets the current number of orders in the queue.
     *
     * @return The size of the order queue.
     */
    public int getQueueSize() {
        return orderQueue.size();
    }

    /**
     * Checks if the order queue is empty.
     *
     * @return true if the queue contains no orders, false otherwise.
     */
    public boolean isEmpty() {
        return orderQueue.isEmpty();
    }

     /**
     * Returns an unmodifiable list view of the orders currently in the queue.
     * Allows external components (like the GUI) to display the queue without modifying it directly.
     *
     * @return An unmodifiable list of Orders.
     */
    public List<Order> getOrders() {
        // Return an unmodifiable list to prevent external modification
        return Collections.unmodifiableList(new ArrayList<>(orderQueue));
    }

     /**
     * Removes a specific order from the queue, identified by its Order object.
     *
     * @param order The Order to remove.
     * @return true if the order was found and removed, false otherwise.
     */
    public boolean removeOrder(Order order) {
        if (order == null) {
            return false;
        }
        boolean removed = orderQueue.remove(order); // remove() uses equals() which is based on orderId
        if (removed) {
            LOGGER.log(Level.INFO, "Order {0} removed from the queue.", order.getOrderId());
        } else {
            LOGGER.log(Level.WARNING, "Attempted to remove Order {0}, but it was not found in the queue.", order.getOrderId());
        }
        return removed;
    }

    /**
     * Displays the current contents of the order queue to the system console.
     * Kept for debugging or alternative display, but primary display is now GUI.
     */
    public void displayQueueToConsole() {
        System.out.println("\n--- Current Order Queue ---");
        if (orderQueue.isEmpty()) {
            System.out.println("Queue is empty.");
        } else {
            int position = 1;
            for (Order order : orderQueue) {
                System.out.println("Position " + position + ":");
                System.out.println("  Order ID: " + order.getOrderId());
                System.out.println("  Table: " + order.getTableNumber());
                System.out.println("  Wait Staff: " + order.getWaitStaffId());
                System.out.println("  Status: " + order.getStatus());
                System.out.println("  Items:");
                for (OrderItem item : order.getItems()) {
                    System.out.println("    - " + item.getMenuItem().getName() + " x " + item.getQuantity());
                }
                System.out.println("  Total Price: $" + order.getTotalPrice());
                System.out.println("---------------------------");
                position++;
            }
        }
        System.out.println("--- End of Order Queue ---\n");
    }

    /**
     * Clears all orders from the queue.
     * Use with caution.
     */
    public void clearQueue() {
        orderQueue.clear();
        LOGGER.warning("Order queue has been cleared.");
        displayQueueToConsole(); // Show the empty queue
    }
}
