package com.example.restrauntautomation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.io.InputStream;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Load FXML to get the preferred size
        // Remove fixed size (320, 240) from Scene constructor
        Scene scene = new Scene(fxmlLoader.load());

        // Apply the dark theme CSS
        String cssPath = Objects.requireNonNull(getClass().getResource("dark-theme.css")).toExternalForm();
        scene.getStylesheets().add(cssPath);

        stage.setTitle("Restaurant Automation Dashboard"); // Updated title
        stage.setScene(scene);

        // Log icon loading
        System.out.println("Attempting to load application icon...");

        // Load and set the application icon
        try {
            // Load icon from relative path
            InputStream iconStream = getClass().getResourceAsStream("app_icon.png");
            if (iconStream == null) {
                System.err.println("Icon resource stream is null: app_icon.png not found relative to HelloApplication class.");
            } else {
                Image icon = new Image(iconStream);
                stage.getIcons().add(icon);
                // Log success
                System.out.println("Application icon loaded successfully.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Error loading application icon: Invalid image format or other issue. " + e.getMessage());
        }

        // Maximize the window
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
