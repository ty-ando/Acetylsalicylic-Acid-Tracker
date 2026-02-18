package persistence;

import org.json.JSONObject;

// Class refers to content from JsonSerializationDemo repository
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
