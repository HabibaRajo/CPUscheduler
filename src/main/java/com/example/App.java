package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

   @Override
    public void start(Stage stage) throws IOException {
        // Use the static loadFXML helper
        Scene scene = new Scene(loadFXML("CPUsim2"), 610, 725);
        
        stage.setTitle("CPU Scheduling Simulator 2026"); // Updated for project year [cite: 1]
        stage.setResizable(true); // Changed to true in case your Gantt chart needs more space [cite: 17, 19]

        stage.setScene(scene);
        stage.show();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        // Ensure the path matches your project structure (usually /com/example/CPUsim2.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        // Keep main clean; all logic should now be inside the Controller and SimulationManager
        launch();
    }
}