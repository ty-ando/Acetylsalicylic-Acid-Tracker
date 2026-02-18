package ui;

import model.EventLog;
import model.Event;
import model.Medication;
import model.User;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Comparator;

import java.awt.event.WindowEvent;
import javax.swing.JFrame;


// Represents Graphic ui for Tracker App

// Image taken from https://www.shutterstock.com/image-vector/structural-chemical-formula-acetylsalicylic-acid-260nw-188267195.jpg
public class TrackerGUI extends JFrame {
    private static final String STARTMSG = "Welcome to ASA Tracker!";
    private static final String JSON_STORE = "./data/user.json";

    private User user;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private CardLayout cardLayout;
    private JPanel mainPanel;

    private JTextField nameField;
    private JTextField weightField;
    private JTextField medNameField;
    private JTextField asaAmountField;

    private JLabel statusLabel;
    private JTextArea medicationListArea;
    private JLabel intakeLabel;
    private JLabel statusResultLabel;

    // Constructs a Tracker GUI
    // EFFECTS: makes new Tracker GUI
    public TrackerGUI() {
        super("Acetylsalicylic Acid Tracker");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        statusLabel = new JLabel("", SwingConstants.CENTER);

        createWelcomeScreen();
        createUserScreen();
        createWeightScreen();
        createMainMenu();

        add(mainPanel);

        pack();
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: creates a welcome screen and presents the user with 
    //          option to create user or load previously saved user
    private void createWelcomeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel(STARTMSG, SwingConstants.CENTER);


        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        try {
            String imageUrl = "https://www.shutterstock.com/image-vector/structural-chemical-formula-acetylsalicylic-acid-260nw-188267195.jpg";
            ImageIcon icon = new ImageIcon(new java.net.URL(imageUrl));
            imageLabel.setIcon(icon);
        } catch (Exception e) {
            imageLabel.setText("Image failed to load");
        }

        panel.add(welcomeLabel, BorderLayout.NORTH);
        panel.add(imageLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton createUserButton = new JButton("Create User");
        createUserButton.addActionListener(e -> cardLayout.show(mainPanel, "UserScreen"));

        JButton loadUserButton = new JButton("Load User");
        loadUserButton.addActionListener(e -> loadUser());

        buttonPanel.add(createUserButton);
        buttonPanel.add(loadUserButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(panel, "WelcomeScreen");
    }

    // MODIFIES: this 
    // EFFECTS: creates a screen that allows user to input their name
    private void createUserScreen() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        nameField = new JTextField();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> cardLayout.show(mainPanel, "WeightScreen"));
        
        panel.add(new JLabel("Enter Name:"));
        panel.add(nameField);
        panel.add(nextButton);
        
        mainPanel.add(panel, "UserScreen");
    }

    // MODIFIES: this 
    // EFFECTS: creates a screen that allows user to input their weight
    private void createWeightScreen() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        weightField = new JTextField();
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> createUser());
        
        panel.add(new JLabel("Enter Weight (kg):"));
        panel.add(weightField);
        panel.add(nextButton);
        
        mainPanel.add(panel, "WeightScreen");
    }

    // MODIFIES: this
    // EFFECTS: adds user input fields and medication list display to GUI
    private void createMainMenu() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(createInputPanel(), BorderLayout.CENTER);
        panel.add(createButtonPanel(), BorderLayout.SOUTH);
        panel.add(statusLabel, BorderLayout.NORTH);
        
        mainPanel.add(panel, "MainMenu");
    }

    // MODIFIES: this
    // EFFECTS: creates the input panels for the createMainMenu method
    private JPanel createInputPanel() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        medNameField = new JTextField();
        asaAmountField = new JTextField();
        intakeLabel = new JLabel("Current Intake: 0 mg/kg");
        statusResultLabel = new JLabel("Status: N/A");
        medicationListArea = new JTextArea(10, 20);
        medicationListArea.setEditable(false);
        
        inputPanel.add(new JLabel("Medication Name:"));
        inputPanel.add(medNameField);
        inputPanel.add(new JLabel("ASA Amount (mg):"));
        inputPanel.add(asaAmountField);
        inputPanel.add(intakeLabel);
        inputPanel.add(statusResultLabel);
        inputPanel.add(new JLabel("Medication List:"));
        inputPanel.add(new JScrollPane(medicationListArea));
        
        return inputPanel;
    }

    // MODIFIES: this
    // EFFECTS: creates the button panels for the createMainMenu method
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton addMedicationButton = new JButton("Add Medication");
        addMedicationButton.addActionListener(e -> addMedication());

        JButton resetButton = new JButton("Reset Intake");
        resetButton.addActionListener(e -> resetIntake());

        JButton saveButton = new JButton("Save User");
        saveButton.addActionListener(e -> saveUser());

        JButton loadButton = new JButton("Load User");
        loadButton.addActionListener(e -> loadUser());

        JButton sortAscendingButton = new JButton("Sort by Name Length (Ascending)");
        sortAscendingButton.addActionListener(e -> sortMedicationsByNameLength(true));

        JButton sortDescendingButton = new JButton("Sort by Name Length (Descending)");
        sortDescendingButton.addActionListener(e -> sortMedicationsByNameLength(false));

        buttonPanel.add(addMedicationButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(sortAscendingButton);
        buttonPanel.add(sortDescendingButton);

        return buttonPanel;
    }

    // MODIFIES: user
    // EFFECTS: initializes a new user
    private void createUser() {
        String name = nameField.getText().trim();
        String weightText = weightField.getText().trim();
        if (name.isEmpty() || weightText.isEmpty()) {
            statusLabel.setText("Please enter both name and weight.");
            return;
        }
        try {
            int weight = Integer.parseInt(weightText);
            if (weight <= 0) {
                statusLabel.setText("Weight must be greater than zero.");
                return;
            }
            user = new User(name, weight);
            statusLabel.setText("User created successfully!");
            cardLayout.show(mainPanel, "MainMenu");
            updateStatus();
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid weight input.");
        }
    }

    // MODIFIES: user
    // EFFECTS: adds medication to the user's list and updates the GUI
    private void addMedication() {
        if (user == null) {
            statusLabel.setText("Create a user first.");
        } else {
            String medName = medNameField.getText();
            try {
                int asaAmount = Integer.parseInt(asaAmountField.getText());
                Medication medication = new Medication(medName, asaAmount);
                user.addMedication(medication);
                statusLabel.setText(medName + " added.");
                updateStatus();
            } catch (NumberFormatException e) {
                statusLabel.setText("Invalid ASA amount input.");
            }
        }
    }

    // MODIFIES: user
    // REQUIRES: clears the user's medication list and updates the GUI
    private void resetIntake() {
        if (user != null) {
            user.reset();
            statusLabel.setText("Intake and medication list reset.");
            updateStatus();
        }
    }

    // MODIFIES: user
    // EFFECTS: sorts the medication list by name length
    private void sortMedicationsByNameLength(boolean ascending) {
        if (user != null) {
            if (ascending) {
                user.getMedications().sort(Comparator.comparingInt(m -> m.getName().length()));
            } else {
                user.getMedications().sort((m1, m2) -> Integer.compare(m2.getName().length(), m1.getName().length()));
            }
            updateMedicationListDisplay();
        }
    }

    // MODIFIES: user
    // EFFECTS: updates the medication list to display the order
    private void updateMedicationListDisplay() {
        if (user != null) {
            medicationListArea.setText(user.medicationList());
        }
    }

    // MODIFIES: this
    // EFFECTS: writes the user's data to a JSON file
    private void saveUser() {
        if (user != null) {
            try {
                jsonWriter.open();
                jsonWriter.write(user);
                jsonWriter.close();
                statusLabel.setText("Saved " + user.getName() + " to file.");
            } catch (FileNotFoundException e) {
                statusLabel.setText("Unable to save file.");
            }
        }
    }

    // MODIFIES: user
    // EFFECTS: loads user data from a file and updates GUI
    private void loadUser() {
        try {
            user = jsonReader.read();
            if (user != null) {
                nameField.setText(user.getName());
                weightField.setText(String.valueOf(user.getWeight()));
                statusLabel.setText("Loaded " + user.getName() + " from file.");
                cardLayout.show(mainPanel, "MainMenu");
                updateStatus();
            } else {
                statusLabel.setText("No saved user found.");
            }
        } catch (IOException e) {
            statusLabel.setText("Unable to load file.");
        }
    }

    // MODIFIES: this
    // EFFECTS: updates UI with updated user information
    private void updateStatus() {
        if (user != null) {
            intakeLabel.setText("Current Intake: " + user.calculateIntake() + " mg/kg");
            statusResultLabel.setText("Status: " + user.getStatus().getName());
            medicationListArea.setText(user.medicationList());
        }
    }

    // EFFECTS: print event log if window is being closed
    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            printEventLog();
        }
        super.processWindowEvent(e);
    }

    // EFFECTS: prints all logged events to the console
    private void printEventLog() {
        EventLog eventLog = EventLog.getInstance();
        System.out.println("Event Log:");
        for (Event event : eventLog) {
            System.out.println(event.toString());
        }
    }
}

