package com.example.restrauntautomation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a category of items on the restaurant menu (e.g., Appetizers, Main Courses).
 * Each category has a name and contains a list of MenuItems.
 */
public class MenuCategory {

    private final String name;
    private final List<MenuItem> items;

    /**
     * Constructs a new MenuCategory.
     *
     * @param name The name of the category (must not be null or empty).
     */
    public MenuCategory(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
        this.name = name.trim();
        this.items = new ArrayList<>();
    }

    /**
     * Gets the name of the category.
     *
     * @return The category name.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a MenuItem to this category.
     * Ensures the item belongs to this category by name.
     *
     * @param item The MenuItem to add (must not be null and must belong to this category).
     */
    public void addItem(MenuItem item) {
        Objects.requireNonNull(item, "Cannot add a null MenuItem.");
        if (!item.getCategoryName().equals(this.name)) {
            throw new IllegalArgumentException("MenuItem '" + item.getName() +
                    "' belongs to category '" + item.getCategoryName() +
                    "' but is being added to category '" + this.name + "'.");
        }
        // Optional: Check if item already exists to prevent duplicates
        if (!this.items.contains(item)) {
            this.items.add(item);
        }
    }

    /**
     * Removes a MenuItem from this category.
     *
     * @param item The MenuItem to remove.
     * @return true if the item was removed, false otherwise.
     */
    public boolean removeItem(MenuItem item) {
        return this.items.remove(item);
    }

    /**
     * Gets an unmodifiable list of MenuItems in this category.
     *
     * @return An unmodifiable list of items.
     */
    public List<MenuItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Returns a string representation of the menu category.
     *
     * @return A string describing the category and its items.
     */
    @Override
    public String toString() {
        return "MenuCategory{" +
               "name='" + name + '\'' +
               ", items=" + items.size() + // Just show count for brevity
               '}';
    }

    /**
     * Checks if this MenuCategory is equal to another object.
     * Equality is based on the category name.
     *
     * @param o The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuCategory that = (MenuCategory) o;
        return name.equals(that.name);
    }

    /**
     * Generates a hash code for the MenuCategory.
     * Based on the category name.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
