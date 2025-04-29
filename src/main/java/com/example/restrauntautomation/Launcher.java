package com.example.restrauntautomation;

/**
 * A launcher class to work around issues with JavaFX modules in shaded JARs.
 */
public class Launcher {
    public static void main(String[] args) {
        // Call the main method of the actual JavaFX application class
        HelloApplication.main(args);
    }
}
