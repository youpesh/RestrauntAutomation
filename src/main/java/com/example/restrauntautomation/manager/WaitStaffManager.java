package com.example.restrauntautomation.manager;

import com.example.restrauntautomation.model.WaitStaff;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Manages loading wait staff data from a file.
 */
public class WaitStaffManager {

    private static final Logger LOGGER = Logger.getLogger(WaitStaffManager.class.getName());
    private static final String DEFAULT_FILE_PATH = "waitstaff.csv";

    /**
     * Loads wait staff data from the default file path ("waitstaff.csv").
     *
     * @return A list of WaitStaff objects loaded from the file, or an empty list if an error occurs or the file doesn't exist.
     */
    public List<WaitStaff> loadWaitStaff() {
        return loadWaitStaff(DEFAULT_FILE_PATH);
    }

    /**
     * Loads wait staff data from the specified file path.
     * Expects a CSV format with each line containing "staffId,name".
     *
     * @param filePath The path to the CSV file containing wait staff data.
     * @return A list of WaitStaff objects loaded from the file, or an empty list if an error occurs.
     */
    public List<WaitStaff> loadWaitStaff(String filePath) {
        List<WaitStaff> staffList = new ArrayList<>();
        if (!Files.exists(Paths.get(filePath))) {
             LOGGER.log(Level.WARNING, "Wait staff file not found at: {0}", filePath);
             return Collections.emptyList(); // Return empty list if file doesn't exist
        }

        // Using try-with-resources for automatic closing of the reader
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim whitespace
                if (line.isEmpty() || line.startsWith("#")) { // Skip empty lines or comments
                    continue;
                }
                String[] parts = line.split(",", 2); // Split into exactly two parts
                if (parts.length == 2) {
                    String staffId = parts[0].trim();
                    String name = parts[1].trim();
                    if (!staffId.isEmpty() && !name.isEmpty()) {
                        staffList.add(new WaitStaff(staffId, name));
                    } else {
                        LOGGER.log(Level.WARNING, "Skipping invalid line in wait staff file: {0}", line);
                    }
                } else {
                    LOGGER.log(Level.WARNING, "Skipping malformed line in wait staff file: {0}", line);
                }
            }
            LOGGER.log(Level.INFO, "Successfully loaded {0} wait staff members from {1}", new Object[]{staffList.size(), filePath});
        } catch (IOException e) {
            // Log the exception details
            LOGGER.log(Level.SEVERE, "Error reading wait staff file: " + filePath, e);
            // Depending on requirements, you might re-throw, return empty list, or handle differently
            return Collections.emptyList(); // Return empty list on error
        } catch (IllegalArgumentException e) {
             // Catch potential errors from WaitStaff constructor
             LOGGER.log(Level.SEVERE, "Error creating WaitStaff object from file: " + filePath, e);

        }

        return staffList;
    }

    // Potential future methods:
    // public void saveWaitStaff(List<WaitStaff> staffList, String filePath) { ... }
    // public void addWaitStaffMember(WaitStaff member, String filePath) { ... }
}
