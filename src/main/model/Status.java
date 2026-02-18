package model;

// Represents a Status of a User based on their intake level
public class Status {
    private static final int HIGHLIMIT = 200;
    private static final int LOWLIMIT = 100;

    private String title;

    // Constructs a Status
	// EFFECTS: Status is set to "Safe"
    public Status() {
        title = "Safe";
    }

    // MODIFIES: this
    // EFFECTS: changes title based on new amount of ASA intake of user
    public void updateStatus(double intake) {
        if (intake >= HIGHLIMIT) {
            title = "Danger";
        } else if (intake >= LOWLIMIT) {
            title = "Caution";
        } else {
            title = "Safe";
        }
    }

    public String getName() {
        return title;
    }
}
