package persistence;

import model.User;
import model.Medication;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads user from JSON data stored in file
// Class refers to content from JsonSerializationDemo repository
public class JsonReader {
    private String source;

    // Construct JsonReader
    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads user from file and returns it:
    //          throws IOException if an error occurs readiung data from file
    public User read() throws IOException {
        String jsonData = readSource(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file and returns it as string
    private String readSource(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses user from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int weight = jsonObject.getInt("weight");
        User user = new User(name, weight);
        addMedications(user, jsonObject);
        
        return user;
    }

    // MODIFIES: user
    // EFFECTS: parses medications from JSON object and adds them to user
    private void addMedications(User user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("medications");
        for (Object json : jsonArray) {
            JSONObject nextMed = (JSONObject) json;
            addMedication(user, nextMed);
        }
    }

    // MODIFIES: user
    // EFFECTS: parses medication from JSON object and adds it to user
    private void addMedication(User user, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int asaAmount = jsonObject.getInt("ASA");
        Medication medication = new Medication(name, asaAmount);
        user.addMedication(medication);
    }
}
