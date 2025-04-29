package com.example.restrauntautomation.manager;

import com.example.restrauntautomation.model.MenuCategory;
import com.example.restrauntautomation.model.MenuItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Manages the creation and access of the restaurant's menu.
 * For this version, the menu is hardcoded.
 */
public class MenuManager {

    private static final Logger LOGGER = Logger.getLogger(MenuManager.class.getName());
    private final Map<String, MenuCategory> categories;

    /**
     * Constructs a MenuManager and initializes the default menu.
     */
    public MenuManager() {
        this.categories = new HashMap<>();
        createDefaultMenu();
        LOGGER.info("Default menu created successfully.");
    }

    /**
     * Creates the hardcoded default menu with categories and items.
     */
    private void createDefaultMenu() {
        // Define Categories
        String catAppetizers = "Appetizers";
        String catSoups = "Soups";
        String catMains = "Main Courses";
        String catDesserts = "Desserts";
        String catBeverages = "Beverages";

        MenuCategory appetizers = new MenuCategory(catAppetizers);
        MenuCategory soups = new MenuCategory(catSoups);
        MenuCategory mains = new MenuCategory(catMains);
        MenuCategory desserts = new MenuCategory(catDesserts);
        MenuCategory beverages = new MenuCategory(catBeverages);

        // Add Appetizers
        appetizers.addItem(new MenuItem("Spring Rolls", "Crispy vegetable spring rolls", new BigDecimal("6.50"), catAppetizers));
        appetizers.addItem(new MenuItem("Garlic Bread", "Toasted bread with garlic butter", new BigDecimal("4.00"), catAppetizers));
        appetizers.addItem(new MenuItem("Bruschetta", "Grilled bread topped with tomatoes, garlic, basil", new BigDecimal("7.00"), catAppetizers));
        appetizers.addItem(new MenuItem("Calamari Rings", "Fried calamari with dipping sauce", new BigDecimal("9.50"), catAppetizers));

        // Add Soups
        soups.addItem(new MenuItem("Tomato Soup", "Classic creamy tomato soup", new BigDecimal("5.00"), catSoups));
        soups.addItem(new MenuItem("Chicken Noodle Soup", "Hearty chicken and noodle soup", new BigDecimal("5.50"), catSoups));
        soups.addItem(new MenuItem("Lentil Soup", "Vegetarian lentil soup", new BigDecimal("5.00"), catSoups));
        soups.addItem(new MenuItem("French Onion Soup", "Rich onion soup with cheese crouton", new BigDecimal("6.50"), catSoups));

        // Add Main Courses
        mains.addItem(new MenuItem("Grilled Salmon", "Salmon fillet with seasonal vegetables", new BigDecimal("18.00"), catMains));
        mains.addItem(new MenuItem("Steak Frites", "Grilled steak with french fries", new BigDecimal("22.50"), catMains));
        mains.addItem(new MenuItem("Chicken Parmesan", "Breaded chicken with marinara and cheese", new BigDecimal("16.00"), catMains));
        mains.addItem(new MenuItem("Vegetarian Pasta", "Pasta with mixed vegetables in tomato sauce", new BigDecimal("14.00"), catMains));

        // Add Desserts
        desserts.addItem(new MenuItem("Chocolate Cake", "Rich chocolate layer cake", new BigDecimal("7.00"), catDesserts));
        desserts.addItem(new MenuItem("Apple Pie", "Warm apple pie with cinnamon", new BigDecimal("6.50"), catDesserts));
        desserts.addItem(new MenuItem("Ice Cream Sundae", "Vanilla ice cream with toppings", new BigDecimal("6.00"), catDesserts));
        desserts.addItem(new MenuItem("Tiramisu", "Classic Italian coffee-flavored dessert", new BigDecimal("7.50"), catDesserts));

        // Add Beverages
        beverages.addItem(new MenuItem("Cola", "Standard cola soft drink", new BigDecimal("2.50"), catBeverages));
        beverages.addItem(new MenuItem("Lemonade", "Freshly squeezed lemonade", new BigDecimal("3.00"), catBeverages));
        beverages.addItem(new MenuItem("Iced Tea", "Sweetened or unsweetened iced tea", new BigDecimal("2.50"), catBeverages));
        beverages.addItem(new MenuItem("Coffee", "Freshly brewed coffee", new BigDecimal("2.75"), catBeverages));
        beverages.addItem(new MenuItem("Mineral Water", "Sparkling or still mineral water", new BigDecimal("2.00"), catBeverages)); // Added 5th item

        // Store categories
        categories.put(catAppetizers, appetizers);
        categories.put(catSoups, soups);
        categories.put(catMains, mains);
        categories.put(catDesserts, desserts);
        categories.put(catBeverages, beverages);
    }

    /**
     * Gets a list of all menu categories.
     *
     * @return An unmodifiable list of MenuCategory objects.
     */
    public List<MenuCategory> getMenuCategories() {
        return Collections.unmodifiableList(new ArrayList<>(categories.values()));
    }

    /**
     * Gets a specific menu category by its name.
     *
     * @param categoryName The name of the category to retrieve.
     * @return The MenuCategory object, or null if not found.
     */
    public MenuCategory getCategoryByName(String categoryName) {
        return categories.get(categoryName);
    }

     /**
     * Gets a specific menu item by its name and category name.
     *
     * @param categoryName The name of the category.
     * @param itemName     The name of the item.
     * @return The MenuItem object, or null if not found.
     */
    public MenuItem getMenuItemByName(String categoryName, String itemName) {
        MenuCategory category = getCategoryByName(categoryName);
        if (category != null) {
            for (MenuItem item : category.getItems()) {
                if (item.getName().equalsIgnoreCase(itemName)) { // Case-insensitive comparison
                    return item;
                }
            }
        }
        return null; // Item or category not found
    }
}
