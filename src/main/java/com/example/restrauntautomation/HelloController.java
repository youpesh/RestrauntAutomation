package com.example.restrauntautomation;

import com.example.restrauntautomation.manager.MenuManager;
import com.example.restrauntautomation.manager.OrderQueueManager;
import com.example.restrauntautomation.manager.WaitStaffManager;
// Specific imports instead of wildcard to avoid MenuItem conflict
import com.example.restrauntautomation.model.MenuCategory;
import com.example.restrauntautomation.model.Order;
import com.example.restrauntautomation.model.OrderItem;
import com.example.restrauntautomation.model.Table;
import com.example.restrauntautomation.model.WaitStaff;
// com.example.restrauntautomation.model.MenuItem will be fully qualified

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap; // Added for map
import java.util.List;
import java.util.Map; // Added for map
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the main restaurant dashboard view (hello-view.fxml).
 * Handles UI interactions, manages application state, and interacts with manager classes.
 */
public class HelloController {

    private static final Logger LOGGER = Logger.getLogger(HelloController.class.getName());
    private static final int NUMBER_OF_TABLES = 30;
    private static final int SEATS_PER_TABLE = 4;

    // --- FXML Injected Fields ---
    @FXML private TilePane tableGridPane;
    @FXML private Label selectedTableLabel;
    @FXML private ComboBox<WaitStaff> waitStaffComboBox;
    @FXML private Accordion menuAccordion;
    @FXML private ListView<OrderItem> currentOrderListView;
    @FXML private Label orderTotalLabel;
    @FXML private Button submitOrderButton;
    @FXML private Button clearOrderButton;
    @FXML private ListView<Order> orderQueueListView; // Added for queue display
    @FXML private Button completeOrderButton; // Added for completion

    // --- Managers ---
    private WaitStaffManager waitStaffManager;
    private MenuManager menuManager;
    private OrderQueueManager orderQueueManager;

    // --- Application State ---
    private List<Table> restaurantTables; // Holds the data model for tables
    private Map<Integer, Button> tableButtonMap; // Maps table number to its button
    private Table selectedTable = null; // Currently selected table data object
    private Button selectedTableButton = null; // Currently selected table button UI element
    private ObservableList<OrderItem> currentOrderItems = FXCollections.observableArrayList();
    private ObservableList<Order> queuedOrders = FXCollections.observableArrayList(); // Added for queue display

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded. Sets up managers, loads data,
     * and populates UI elements.
     */
    @FXML
    public void initialize() {
        LOGGER.info("Initializing HelloController...");

        // Initialize Managers
        waitStaffManager = new WaitStaffManager();
        menuManager = new MenuManager(); // Menu is hardcoded inside
        orderQueueManager = new OrderQueueManager();
        tableButtonMap = new HashMap<>(); // Initialize the map

        // Setup UI Components
        setupWaitStaffComboBox();
        setupTableGrid();
        setupMenuAccordion();
        setupCurrentOrderListView();
        setupOrderQueueListView(); // Added setup call

        // Initial state for buttons
        submitOrderButton.setDisable(true); // Disable until table, staff, and items are selected
        completeOrderButton.setDisable(true); // Disable initially

        LOGGER.info("HelloController initialization complete.");
    }

    /**
     * Sets up the Wait Staff ComboBox: loads data and configures display.
     */
    private void setupWaitStaffComboBox() {
        List<WaitStaff> staff = waitStaffManager.loadWaitStaff();
        waitStaffComboBox.setItems(FXCollections.observableArrayList(staff));

        // How to display WaitStaff objects in the ComboBox
        waitStaffComboBox.setConverter(new StringConverter<WaitStaff>() {
            @Override
            public String toString(WaitStaff staff) {
                return staff == null ? null : staff.getName() + " (" + staff.getStaffId() + ")";
            }

            @Override
            public WaitStaff fromString(String string) {
                // Not needed for non-editable ComboBox
                return null;
            }
        });

        // Add listener to enable submit button when staff is selected (along with other conditions)
         waitStaffComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> checkSubmitButtonState());

        LOGGER.info("Wait Staff ComboBox setup complete. Loaded " + staff.size() + " staff members.");
    }

    /**
     * Creates and populates the grid of tables.
     */
    private void setupTableGrid() {
        restaurantTables = new ArrayList<>();
        tableButtonMap.clear(); // Clear map before repopulating
        tableGridPane.getChildren().clear(); // Clear any existing UI children
        for (int i = 1; i <= NUMBER_OF_TABLES; i++) {
            Table table = new Table(i, SEATS_PER_TABLE); // Default status is VACANT
            restaurantTables.add(table);
            Button tableButton = createTableButton(table);
            tableButtonMap.put(table.getTableNumber(), tableButton); // Store button reference
            tableGridPane.getChildren().add(tableButton);
        }
        LOGGER.info("Table grid setup complete. Created " + NUMBER_OF_TABLES + " tables.");
    }

    /**
     * Creates a Button representation for a given Table.
     *
     * @param table The Table object.
     * @return A Button configured to represent the table.
     */
    private Button createTableButton(Table table) {
        Button button = new Button("Table " + table.getTableNumber() + "\n(" + table.getStatus().name() + ")");
        button.setPrefSize(100, 60); // Set preferred size for uniformity
        button.setWrapText(true);
        button.setAlignment(Pos.CENTER);
        updateTableButtonAppearance(button, table); // Set initial appearance

        // Set action to handle table selection
        button.setOnAction(event -> handleTableSelection(table, button));
        return button;
    }

    /**
     * Handles the selection of a table button.
     *
     * @param table The selected Table object.
     * @param button The button that was clicked.
     */
    private void handleTableSelection(Table table, Button button) {
        // Basic selection logic: update label and internal state
        // More complex logic could involve checking table status, etc.

        // Reset style of previously selected button (if any)
        if (selectedTableButton != null && selectedTable != null) {
             updateTableButtonAppearance(selectedTableButton, selectedTable); // Reset to its actual status color
        }

        selectedTable = table;
        selectedTableButton = button; // Store reference to the clicked button
        selectedTableLabel.setText(String.valueOf(table.getTableNumber()));
        LOGGER.info("Table selected: " + table.getTableNumber());

        // Highlight the newly selected button by adding a specific style class
        // First remove any existing selection style, then add it to the new button
        if (selectedTableButton != null) {
            selectedTableButton.getStyleClass().remove("table-button-selected");
        }
        button.getStyleClass().add("table-button-selected");


        checkSubmitButtonState();
    }

    /**
     * Updates the text and style of a table button based on the table's status.
     *
     * @param button The Button UI element.
     * @param table The Table data object.
     */
    private void updateTableButtonAppearance(Button button, Table table) {
        if (button == null || table == null) return;
        button.setText("Table " + table.getTableNumber() + "\n(" + table.getStatus().name() + ")");

        // Remove all potential status style classes first
        button.getStyleClass().removeAll("table-button-vacant", "table-button-occupied", "table-button-reserved", "table-button-needs-cleaning");
        // Remove selection style if present
        button.getStyleClass().remove("table-button-selected");

        // Add the correct style class based on the current status
        button.getStyleClass().add(getStyleClassForStatus(table.getStatus()));
    }

     /**
     * Gets the CSS style class name for a given table status.
     *
     * @param status The TableStatus.
     * @return A CSS style class name string.
     */
    private String getStyleClassForStatus(Table.TableStatus status) {
        switch (status) {
            case OCCUPIED:
                return "table-button-occupied";
            case RESERVED:
                return "table-button-reserved";
            case NEEDS_CLEANING:
                return "table-button-needs-cleaning";
            case VACANT:
            default:
                return "table-button-vacant";
        }
    }


    /**
     * Sets up the Menu Accordion with categories and items.
     */
    private void setupMenuAccordion() {
        menuAccordion.getPanes().clear();
        List<MenuCategory> categories = menuManager.getMenuCategories();

        for (MenuCategory category : categories) {
            VBox itemsVBox = new VBox(5); // VBox to hold buttons for items in this category
            itemsVBox.setPadding(new javafx.geometry.Insets(10));
            // Use fully qualified name for the loop variable to resolve ambiguity
            for (com.example.restrauntautomation.model.MenuItem item : category.getItems()) {
                Button itemButton = new Button(item.getName() + " ($" + item.getPrice() + ")");
                itemButton.setMaxWidth(Double.MAX_VALUE); // Make button fill width
                // Ensure the correct 'item' (our model item) is captured by the lambda
                itemButton.setOnAction(event -> handleAddItemToOrder(item));
                itemsVBox.getChildren().add(itemButton);
            }
            ScrollPane scrollPane = new ScrollPane(itemsVBox); // Make items scrollable if many
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefHeight(150); // Limit height

            TitledPane categoryPane = new TitledPane(category.getName(), scrollPane);
            menuAccordion.getPanes().add(categoryPane);
        }
        LOGGER.info("Menu Accordion setup complete. Added " + categories.size() + " categories.");
    }

     /**
     * Sets up the ListView for displaying the current order items.
     */
    private void setupCurrentOrderListView() {
        currentOrderListView.setItems(currentOrderItems);

        // Customize how OrderItems are displayed in the ListView
        currentOrderListView.setCellFactory(lv -> new ListCell<OrderItem>() {
            @Override
            protected void updateItem(OrderItem item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getMenuItem().getName() + " x " + item.getQuantity() + " ($" + item.getTotalPrice() + ")");
                }
            }
        });

        // Add listener to enable submit button when items are added (along with other conditions)
        currentOrderItems.addListener((javafx.collections.ListChangeListener.Change<? extends OrderItem> c) -> {
            updateOrderTotal();
            checkSubmitButtonState();
        });

        LOGGER.info("Current Order ListView setup complete.");
    }

     /**
     * Sets up the ListView for displaying the pending order queue.
     */
    private void setupOrderQueueListView() {
        orderQueueListView.setItems(queuedOrders);

        // Customize how Orders are displayed in the ListView
        orderQueueListView.setCellFactory(lv -> new ListCell<Order>() {
            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (empty || order == null) {
                    setText(null);
                } else {
                    // Customize this string for desired queue display format
                    setText(String.format("ID: %d | Tbl: %d | Staff: %s | Items: %d | Total: $%.2f",
                            order.getOrderId(),
                            order.getTableNumber(),
                            order.getWaitStaffId(), // Consider fetching staff name if needed
                            order.getItems().size(),
                            order.getTotalPrice()));
                }
            }
        });

        // Add listener to enable/disable complete button based on selection
        orderQueueListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            completeOrderButton.setDisable(newSelection == null);
        });

        updateOrderQueueView(); // Initial population
        LOGGER.info("Order Queue ListView setup complete.");
    }

     /**
     * Refreshes the order queue ListView with the current data from the manager.
     */
    private void updateOrderQueueView() {
        queuedOrders.setAll(orderQueueManager.getOrders());
        LOGGER.fine("Order Queue view updated."); // Use fine level for frequent updates
    }


    /**
     * Handles adding a selected MenuItem to the current order list.
     *
     * @param item The MenuItem to add. Use fully qualified name to avoid conflict.
     */
    private void handleAddItemToOrder(com.example.restrauntautomation.model.MenuItem item) {
        if (item == null) return;

        // Check if item already exists in the list
        Optional<OrderItem> existingOrderItem = currentOrderItems.stream()
                .filter(orderItem -> orderItem.getMenuItem().equals(item))
                .findFirst();

        if (existingOrderItem.isPresent()) {
            // Increment quantity
            OrderItem oi = existingOrderItem.get();
            oi.setQuantity(oi.getQuantity() + 1);
            // Refresh the ListView to show updated quantity/price
            currentOrderListView.refresh();
        } else {
            // Add new item with quantity 1
            currentOrderItems.add(new OrderItem(item, 1));
        }
        LOGGER.info("Added item to current order: " + item.getName());
        updateOrderTotal(); // Update total after adding/modifying
        checkSubmitButtonState();
    }

    /**
     * Updates the total price label based on items in the current order list.
     */
    private void updateOrderTotal() {
        BigDecimal total = currentOrderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderTotalLabel.setText(String.format("$%.2f", total));
    }

    /**
     * Checks if the conditions are met to enable the Submit Order button.
     * Conditions: Table selected, Wait Staff selected, and at least one item in the order.
     */
    private void checkSubmitButtonState() {
        boolean tableSelected = (selectedTable != null);
        boolean staffSelected = (waitStaffComboBox.getSelectionModel().getSelectedItem() != null);
        boolean itemsExist = !currentOrderItems.isEmpty();

        submitOrderButton.setDisable(!(tableSelected && staffSelected && itemsExist));
    }


    /**
     * Handles the action of submitting the current order.
     * Creates an Order object and adds it to the OrderQueueManager.
     * Clears the current order UI.
     */
    @FXML
    void handleSubmitOrderAction(ActionEvent event) {
        if (selectedTable == null || waitStaffComboBox.getSelectionModel().getSelectedItem() == null || currentOrderItems.isEmpty()) {
            // Should not happen if button state is managed correctly, but good practice to check
            showAlert(Alert.AlertType.WARNING, "Incomplete Order", "Please select a table, wait staff, and add items before submitting.");
            LOGGER.warning("Submit order attempted with incomplete information.");
            return;
        }

        // Create the order
        WaitStaff staff = waitStaffComboBox.getSelectionModel().getSelectedItem();
        Order newOrder = new Order(selectedTable.getTableNumber(), staff.getStaffId());
        currentOrderItems.forEach(item -> newOrder.addItem(item.getMenuItem(), item.getQuantity())); // Add items from list

        // Add to queue (which also prints to console)
        boolean added = orderQueueManager.addOrder(newOrder);

        if (added) {
            LOGGER.info("Order " + newOrder.getOrderId() + " submitted successfully for table " + selectedTable.getTableNumber());
            showAlert(Alert.AlertType.INFORMATION, "Order Submitted", "Order ID: " + newOrder.getOrderId() + " submitted to the queue.");
            // Update table status and button appearance
            Table orderedTable = findTableByNumber(selectedTable.getTableNumber());
            Button orderedButton = tableButtonMap.get(selectedTable.getTableNumber());
            if (orderedTable != null && orderedButton != null) {
                orderedTable.setStatus(Table.TableStatus.OCCUPIED);
                updateTableButtonAppearance(orderedButton, orderedTable);
                LOGGER.info("Table " + orderedTable.getTableNumber() + " status set to OCCUPIED.");
            } else {
                 LOGGER.warning("Could not find table or button to update status for table number: " + selectedTable.getTableNumber());
            }

            // Clear the UI for the next order
            clearCurrentOrderState();
            // Update the queue display in the GUI
            updateOrderQueueView();
        } else {
             LOGGER.log(Level.SEVERE, "Failed to submit order for table " + selectedTable.getTableNumber());
              showAlert(Alert.AlertType.ERROR, "Submission Failed", "Could not submit the order to the queue.");
        }
    }

     /**
     * Handles the action of completing the selected order from the queue.
     * Removes the order, updates the queue view, and sets the table back to vacant.
     */
    @FXML
    void handleCompleteOrderAction(ActionEvent event) {
        Order selectedOrder = orderQueueListView.getSelectionModel().getSelectedItem();

        if (selectedOrder == null) {
            showAlert(Alert.AlertType.WARNING, "No Order Selected", "Please select an order from the queue to complete.");
            return;
        }

        // Remove from the backend queue
        boolean removed = orderQueueManager.removeOrder(selectedOrder);

        if (removed) {
            // Update the GUI queue view
            updateOrderQueueView();

            // Find the associated table and update its status and button
            Table completedTable = findTableByNumber(selectedOrder.getTableNumber());
            Button completedTableButton = tableButtonMap.get(selectedOrder.getTableNumber());

            if (completedTable != null && completedTableButton != null) {
                completedTable.setStatus(Table.TableStatus.VACANT); // Set table back to vacant
                updateTableButtonAppearance(completedTableButton, completedTable); // Update button appearance
                LOGGER.info("Order " + selectedOrder.getOrderId() + " completed. Table " + completedTable.getTableNumber() + " set to VACANT.");
                showAlert(Alert.AlertType.INFORMATION, "Order Completed", "Order ID: " + selectedOrder.getOrderId() + " completed and removed from queue. Table " + completedTable.getTableNumber() + " is now vacant.");
            } else {
                LOGGER.warning("Could not find table or button to update status after completing order for table number: " + selectedOrder.getTableNumber());
                 showAlert(Alert.AlertType.WARNING, "Order Completed (Table Update Issue)", "Order ID: " + selectedOrder.getOrderId() + " completed and removed from queue, but could not update table status visually.");
            }
        } else {
            // Should not happen if button is enabled correctly, but handle defensively
            showAlert(Alert.AlertType.ERROR, "Completion Failed", "Could not remove the selected order from the queue. It might have already been processed.");
            updateOrderQueueView(); // Refresh view in case of inconsistency
        }
    }


    /**
     * Handles the action of clearing the current order details from the UI.
     */
    @FXML
    void handleClearOrderAction(ActionEvent event) {
        clearCurrentOrderState();
        LOGGER.info("Current order UI cleared by user.");
    }

    /**
     * Resets the UI elements related to the current order.
     */
    private void clearCurrentOrderState() {
        // Reset selection style from the previously selected button, if any
        if (selectedTableButton != null) {
             selectedTableButton.getStyleClass().remove("table-button-selected");
        }
        selectedTable = null;
        selectedTableButton = null;
        selectedTableLabel.setText("None");
        waitStaffComboBox.getSelectionModel().clearSelection();
        currentOrderItems.clear();
        // orderTotalLabel is updated by the listener on currentOrderItems

        LOGGER.info("Current order UI state cleared.");
         checkSubmitButtonState(); // Ensure submit button is disabled
    }

    /**
     * Helper method to show alerts to the user.
     *
     * @param alertType The type of alert (e.g., INFORMATION, WARNING, ERROR).
     * @param title     The title of the alert window.
     * @param message   The message content of the alert.
     */
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message);
        alert.showAndWait();
    }

     /**
     * Finds a Table object in the list by its number.
     *
     * @param tableNumber The number of the table to find.
     * @return The Table object, or null if not found.
     */
    private Table findTableByNumber(int tableNumber) {
        return restaurantTables.stream()
                .filter(t -> t.getTableNumber() == tableNumber)
                .findFirst()
                .orElse(null);
    }

    // --- Placeholder for original button action if needed ---
    // @FXML
    // protected void onHelloButtonClick() {
    //     welcomeText.setText("Welcome to JavaFX Application!");
    // }
}
