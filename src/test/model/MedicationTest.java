package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MedicationTest {
    private Medication test1;
    private Medication test2;

    @BeforeEach
    public void setUp() {
        test1 = new Medication("test1", 200);
        test2 = new Medication("test2", 35);
    }

    @Test
    public void testMedicationConstructor() {
        assertEquals("test1", test1.getName());
        assertEquals("test2", test2.getName());
        assertEquals(200, test1.getAsaAmount());
        assertEquals(35, test2.getAsaAmount());
    }

}
