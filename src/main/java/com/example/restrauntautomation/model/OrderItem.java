package com.example.restrauntautomation.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a single line item within an Order, linking a MenuItem with a quantity.
 */
public class OrderItem {

    private final MenuItem menuItem;
    private int quantity;

    /**
     * Constructs a new OrderItem.
     *
     * @param menuItem The MenuItem being ordered (must not be null).
     * @param quantity The quantity ordered (must be positive).
     */
    public OrderItem(MenuItem menuItem, int quantity) {
        Objects.requireNonNull(menuItem, "MenuItem cannot be null for an OrderItem.");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    /**
     * Gets the MenuItem associated with this order line.
     *
     * @return The MenuItem.
     */
    public MenuItem getMenuItem() {
        return menuItem;
    }

    /**
     * Gets the quantity ordered for this item.
     *
     * @return The quantity.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity for this order item.
     * Used if the quantity needs to be adjusted after creation.
     *
     * @param quantity The new quantity (must be positive).
     */
    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive.");
        }
        this.quantity = quantity;
    }

    /**
     * Calculates the total price for this order item (MenuItem price * quantity).
     *
     * @return The total price for this line item.
     */
    public BigDecimal getTotalPrice() {
        return menuItem.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Returns a string representation of the order item.
     *
     * @return A string describing the order item.
     */
    @Override
    public String toString() {
        return "OrderItem{" +
               "menuItem=" + menuItem.getName() + // Show item name for clarity
               ", quantity=" + quantity +
               ", totalPrice=" + getTotalPrice() +
               '}';
    }

    /**
     * Checks if this OrderItem is equal to another object.
     * Equality is based on the underlying MenuItem. Quantity is not considered
     * for basic equality, as you might want to merge items of the same type.
     *
     * @param o The object to compare with.
     * @return true if the objects represent the same MenuItem, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return menuItem.equals(orderItem.menuItem); // Based on the MenuItem only
    }

    /**
     * Generates a hash code for the OrderItem.
     * Based on the underlying MenuItem.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(menuItem); // Based on the MenuItem only
    }
}
