package com.example.restrauntautomation.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a single item available on the restaurant menu.
 * Each item has a name, description, price, and belongs to a category.
 */
public class MenuItem {

    private final String name;
    private final String description;
    private final BigDecimal price;
    private final String categoryName; // Link to MenuCategory

    /**
     * Constructs a new MenuItem.
     *
     * @param name         The name of the menu item (must not be null or empty).
     * @param description  A brief description of the item.
     * @param price        The price of the item (must be non-negative).
     * @param categoryName The name of the category this item belongs to (must not be null or empty).
     */
    public MenuItem(String name, String description, BigDecimal price, String categoryName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Menu item name cannot be null or empty.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Menu item price cannot be null or negative.");
        }
         if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }

        this.name = name.trim();
        this.description = description != null ? description : ""; // Allow null description, default to empty
        this.price = price;
        this.categoryName = categoryName.trim();
    }

    /**
     * Gets the name of the menu item.
     *
     * @return The item name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the menu item.
     *
     * @return The item description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the price of the menu item.
     *
     * @return The item price.
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Gets the name of the category this item belongs to.
     *
     * @return The category name.
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Returns a string representation of the menu item.
     *
     * @return A string describing the menu item.
     */
    @Override
    public String toString() {
        return "MenuItem{" +
               "name='" + name + '\'' +
               ", description='" + description + '\'' +
               ", price=" + price +
               ", categoryName='" + categoryName + '\'' +
               '}';
    }

    /**
     * Checks if this MenuItem is equal to another object.
     * Equality is based on the item name and category name.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        // Primarily checking name and category for uniqueness within a menu context
        return name.equals(menuItem.name) && categoryName.equals(menuItem.categoryName);
    }

    /**
     * Generates a hash code for the MenuItem.
     * Based on the item name and category name.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, categoryName);
    }
}
