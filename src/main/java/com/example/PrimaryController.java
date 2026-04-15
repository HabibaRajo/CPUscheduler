package com.example;

import com.example.Logic.Process;
import com.example.Logic.SimulationManager;
import com.example.Logic.Algorithms.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    // Input Fields
    @FXML private Spinner<Integer> arrivalSpinner;
    @FXML private Spinner<Integer> burstSpinner;
    @FXML private Spinner<Integer> prioritySpinner;
    @FXML private ChoiceBox<String> algoChoiceBox;
    @FXML private ChoiceBox<String> modeChoiceBox;

    // Table
    @FXML private TableView<Process> processTable;
    @FXML private TableColumn<Process, Integer> idColumn;
    @FXML private TableColumn<Process, Integer> arrivalColumn;
    @FXML private TableColumn<Process, Integer> burstColumn;
    @FXML private TableColumn<Process, Integer> remainingTimeColumn;

    // Observable List for TableView (It automatically updates the UI when table is modified)
    private ObservableList<Process> processList = FXCollections.observableArrayList();
    private SimulationManager simulationManager = new SimulationManager();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Fill Selection Boxes with Options
        algoChoiceBox.setItems(FXCollections.observableArrayList(
            "Choose Algorithm...", "FCFS", "SJF (Non Preemptive)", "SRJF (Preemptive)", 
            "Priority (Non Preemptive)", "Priority (Preemptive)", "Round Robin"
        ));
        algoChoiceBox.setValue("Choose Algorithm..."); // Default prompt

        modeChoiceBox.setItems(FXCollections.observableArrayList(
            "Select Mode...", "Dynamic (Live 1s/unit)", "Static (Instant)"
        ));
        modeChoiceBox.setValue("Select Mode...");

        // 2. Link Table Columns (Matches getters in Process class)
        // PropertyValueFactory looks for getId(), getArrivalTime(), etc. in the Process class
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        arrivalColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        remainingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("remainingTime"));

        processTable.setItems(processList); // Connect the ObservableList to the TableView

        // 3. Initialize Spinners
        arrivalSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));
        burstSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1));
        prioritySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0));

        // 4. Algorithm Selection Listener 
        algoChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.equals("Choose Algorithm...")) {
                boolean needsPriority = newVal.contains("Priority");
                prioritySpinner.setDisable(!needsPriority); 
                handleAlgorithmStrategy(newVal);
            }
        });
    }

    private void handleAlgorithmStrategy(String algo) {
        switch (algo) {
            case "FCFS": simulationManager.setAlgorithm(new FCFS()); break;
            case "Round Robin": simulationManager.setAlgorithm(new RoundRobin()); break; // 3s quantum 
            case "SJF (Preemptive)": simulationManager.setAlgorithm(new SRJF()); break;
            case "Priority (Preemptive)": simulationManager.setAlgorithm(new PreemptivePriority()); break;
        }
    }

    @FXML
    private void handleAddProcess() {
        // 1. Collect inputs from UI spinners
        int arrival = arrivalSpinner.getValue();
        int burst = burstSpinner.getValue();
        
        // Requirement #11: Only use priority if the algorithm requires it [cite: 1, 11]
        int priority = prioritySpinner.isDisabled() ? 0 : prioritySpinner.getValue();

        // 2. Create the new process object
        Process newP = new Process(burst, arrival, priority);

        // 3. Add to UI List (Updates the TableView automatically)
        processList.add(newP);

        // 4. Add to Logic Manager (Makes it available for the scheduler)
        simulationManager.addProcess(newP); 
        
        System.out.println("Process " + newP.getId() + " added successfully.");
    }
    @FXML
    private void handleRemoveProcess() {
        Process selected = processTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            processList.remove(selected);
            // simulationManager.removeProcess(selected); 
        }
    }

    @FXML
    private void handleClear() {
        processList.clear();
        // simulationManager.clearAll();
    }

    @FXML
    private void handleStart() {
        // Condition: At least 2 processes required
        if (processList.size() < 2) {
            showError("You must add at least 2 processes before starting.");
            return;
        }

        if (algoChoiceBox.getValue().equals("Choose Algorithm...") || 
            modeChoiceBox.getValue().equals("Select Mode...")) {
            showError("Please select both an Algorithm and a Simulation Mode.");
            return;
        }

        // Trigger Live or Static Simulation [cite: 13, 14]
        if (modeChoiceBox.getValue().contains("Dynamic")) {
            // Start your JavaFX Timeline or Timer here (1s = 1 unit) [cite: 13]
        } else {
            // Run Static Loop [cite: 14]
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText(message);
        alert.showAndWait();
    }
}