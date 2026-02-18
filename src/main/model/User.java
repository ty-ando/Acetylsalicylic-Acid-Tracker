package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a user with a name, weight, acetylsalicylic intake,
// and list of medication.
// User is the person who will use the ASATracker application.
public class User implements Writable {
    private String name;
    private int weight;
    private int asaAmount;
    private ArrayList<Medication> medications;
    private Status status;
    
    // Constructs a User
    // REQUIRES: weight > 0
	// EFFECTS: User with given name, weight, 0 ASAintake, and empty medication list.
    public User(String name, int weight) {
        this.name = name;
        this.weight = weight;
        asaAmount = 0;
        medications = new ArrayList<>();
        status = new Status();
    }

    // MODIFIES: this
    // EFFECTS: adds given medication to user medication list and to event log
    public void addMedication(Medication medication) {
        this.medications.add(medication);
        asaAmount += medication.getAsaAmount();
        status.updateStatus(calculateIntake());
        EventLog.getInstance().logEvent(
					new Event(medication.getName() 
                    + " (" + Integer.toString(medication.getAsaAmount())
                    + " mg), Added to " + this.getName() + "."));
    }

    // EFFECTS: calculates the ASAintake with respect to the User's body weight
    public double calculateIntake() {
        double calculation = (double) asaAmount / weight;
        return Math.round(calculation * 100.0) / 100.0;
    }

    // MODIFIES: this
    // EFFECTS: resets ASAintake to 0 and medication to empty list and clears event log
    public void reset() {
        asaAmount = 0;
        medications = new ArrayList<>();
        EventLog.getInstance().clear();
        EventLog.getInstance().logEvent(
                    new Event(this.getName() + "'s Medication List Cleared."));
    }

    // EFFECTS: returns the list of all the medication and ASA
    //          amount of each medication the User has
    public String medicationList() {
        String list = "";

        if (medications.size() == 0) {
            return "No Medication Added";
        } else {
            for (int i = 0; i < medications.size(); i++) {
                Medication med = medications.get(i);
                if (i != 0) {
                    list = list.concat(",\n");
                }
                list = list.concat(med.getName());
                list = list.concat(" ");
                list = list.concat(Integer.toString(med.getAsaAmount()));
                list = list.concat("mg");
            }

            return list.concat(".");
        }
    }

    // EFFECTS: changes user to JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("weight", weight);
        json.put("medications", medicationsToJson());

        return json;
    }

    // EFFECTS: returns medication in this user as a JSON array
    private JSONArray medicationsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Medication med : medications) {
            jsonArray.put(med.toJson());
        }
        
        return jsonArray;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public int getAsaAmount() {
        return asaAmount;
    }

    public ArrayList<Medication> getMedications() {
        return medications;
    }

    public Status getStatus() {
        return status;
    }
}
