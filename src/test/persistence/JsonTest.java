
package persistence;

import model.Medication;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonTest {
    protected void checkMedication(String name, int ASAamount, Medication med) {
        assertEquals(name, med.getName());
        assertEquals(ASAamount, med.getAsaAmount());
    }
}
