package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents an medication with a name, and amount of acetylsalicylic acid in milligrams
public class Medication implements Writable {
    private String name;
    private int asaAmount;

    // Constructs a Medication
    // REQUIRES: ASAamount >= 0
	// EFFECTS: Medication with given name, ASAamount
    public Medication(String name, int asaAmount) {
        this.name = name;
        this.asaAmount = asaAmount;
    }

    // EFFECTS: changes Medication to JSON Object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("ASA", asaAmount);
        return json;
    }

    public String getName() {
        return name;
    }

    public int getAsaAmount() {
        return asaAmount;
    }
}
