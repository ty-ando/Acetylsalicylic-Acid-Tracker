package persistence;

import model.Medication;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            User user = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyUser() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyUser.json");
        try {
            User user = reader.read();
            assertEquals("John", user.getName());
            assertEquals(70, user.getWeight());
            assertEquals(0, user.getMedications().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralUser() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralUser.json");
        try {
            User user = reader.read();
            assertEquals("Jim", user.getName());
            List<Medication> medications = user.getMedications();
            assertEquals(2, medications.size());
            checkMedication("Tylenol", 200, medications.get(0));
            checkMedication("Advil", 100, medications.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
