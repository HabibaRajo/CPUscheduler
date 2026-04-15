module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    // Open the main package for FXML (PrimaryController is likely here)
    opens com.example to javafx.fxml;
    
    // Export your logic packages so other parts of the app can see them
    exports com.example;
    exports com.example.Logic;
    exports com.example.Logic.Algorithms;
}