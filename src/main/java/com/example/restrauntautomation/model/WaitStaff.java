package com.example.restrauntautomation.model;

import java.util.Objects;

/**
 * Represents a member of the wait staff.
 * Each staff member has a unique ID and a name.
 */
public class WaitStaff {

    private final String staffId;
    private final String name;

    /**
     * Constructs a new WaitStaff object.
     *
     * @param staffId The unique identifier for the staff member (must not be null or empty).
     * @param name    The name of the staff member (must not be null or empty).
     */
    public WaitStaff(String staffId, String name) {
        if (staffId == null || staffId.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Staff name cannot be null or empty.");
        }
        this.staffId = staffId.trim();
        this.name = name.trim();
    }

    /**
     * Gets the unique ID of the wait staff member.
     *
     * @return The staff ID.
     */
    public String getStaffId() {
        return staffId;
    }

    /**
     * Gets the name of the wait staff member.
     *
     * @return The staff name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a string representation of the wait staff member, typically their name.
     * Useful for display in UI elements like ComboBoxes.
     *
     * @return The name of the wait staff member.
     */
    @Override
    public String toString() {
        // Often overridden to display the name in UI components
        return name;
    }

    /**
     * Checks if this WaitStaff object is equal to another object.
     * Equality is based on the unique staffId.
     *
     * @param o The object to compare with.
     * @return true if the objects have the same staffId, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaitStaff waitStaff = (WaitStaff) o;
        return staffId.equals(waitStaff.staffId);
    }

    /**
     * Generates a hash code for the WaitStaff object.
     * Based on the unique staffId.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(staffId);
    }
}
