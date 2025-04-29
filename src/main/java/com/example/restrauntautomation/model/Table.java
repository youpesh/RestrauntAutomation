package com.example.restrauntautomation.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single table in the restaurant.
 * Each table has a unique number and a fixed number of seats.
 */
public class Table {

    private final int tableNumber;
    private final int capacity; // Number of seats at the table
    private TableStatus status; // e.g., VACANT, OCCUPIED, RESERVED

    /**
     * Constructs a new Table object.
     *
     * @param tableNumber The unique number identifying the table.
     * @param capacity    The number of seats at this table.
     */
    public Table(int tableNumber, int capacity) {
        if (tableNumber <= 0) {
            throw new IllegalArgumentException("Table number must be positive.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Table capacity must be positive.");
        }
        this.tableNumber = tableNumber;
        this.capacity = capacity;
        this.status = TableStatus.VACANT; // Default status
    }

    /**
     * Gets the table number.
     *
     * @return The unique table number.
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Gets the seating capacity of the table.
     *
     * @return The number of seats.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the current status of the table.
     *
     * @return The table status (e.g., VACANT, OCCUPIED).
     */
    public TableStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the table.
     *
     * @param status The new status for the table.
     */
    public void setStatus(TableStatus status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the table.
     *
     * @return A string describing the table.
     */
    @Override
    public String toString() {
        return "Table{" +
               "tableNumber=" + tableNumber +
               ", capacity=" + capacity +
               ", status=" + status +
               '}';
    }

    /**
     * Represents the possible statuses of a restaurant table.
     */
    public enum TableStatus {
        VACANT,
        OCCUPIED,
        RESERVED,
        NEEDS_CLEANING
    }
}
