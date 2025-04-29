module com.example.restrauntautomation {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.logging; // Added to allow use of java.util.logging

    opens com.example.restrauntautomation to javafx.fxml;
    exports com.example.restrauntautomation;
    // Also need to open the manager package if it uses reflection/logging internally, though less likely needed
    // opens com.example.restrauntautomation.manager to javafx.fxml; // Uncomment if needed
    // Also need to open the model package if it uses reflection/logging internally, though less likely needed
    // opens com.example.restrauntautomation.model to javafx.fxml; // Uncomment if needed

}
