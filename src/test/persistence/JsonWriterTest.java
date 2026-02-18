package persistence;

import model.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            User user = new User("Tony", 90);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyUser() {
        try {
            User user = new User("Bob", 40);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            user = reader.read();
            assertEquals("Bob", user.getName());
            assertEquals(0, user.getMedications().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralUser() {
        try {
            User user = new User("Coby", 80);
            user.addMedication(new Medication("Cough Medicine", 150));
            user.addMedication(new Medication("Sore Throat Medicine", 250));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralUser.json");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralUser.json");
            user = reader.read();
            assertEquals("Coby", user.getName());
            List<Medication> medications = user.getMedications();
            assertEquals(2, medications.size());
            checkMedication("Cough Medicine", 150, medications.get(0));
            checkMedication("Sore Throat Medicine", 250, medications.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
