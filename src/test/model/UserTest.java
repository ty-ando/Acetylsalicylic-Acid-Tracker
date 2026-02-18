package model;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User testUser1;
    private User testUser2;
    private Medication testMedication1;
    private Medication testMedication2;
    
    @BeforeEach
    public void setUp() {
        testUser1 = new User("test1", 100);
        testUser2 = new User("test2", 50);
        testMedication1 = new Medication("testMed1", 30);
        testMedication2 = new Medication("testMed2", 80);
    }

    @Test
    public void testUserConstructor() {
        assertEquals(100, testUser1.getWeight());
        assertEquals(50, testUser2.getWeight());
        assertEquals(0, testUser1.getAsaAmount());
        assertEquals(0, testUser2.getAsaAmount());
        assertEquals("test1", testUser1.getName());
        assertEquals("test2", testUser2.getName());
        assertTrue(testUser1.getMedications().isEmpty());
        assertTrue(testUser2.getMedications().isEmpty());
        assertEquals("Safe", testUser1.getStatus().getName());
    }

    @Test
    public void testUserAddMedication() {
        testUser1.addMedication(testMedication1);

        ArrayList<Medication> medications = testUser1.getMedications();

        assertEquals(1, medications.size());
        assertEquals(30, testUser1.getAsaAmount());
        assertEquals(testMedication1, medications.get(0));

        testUser1.addMedication(testMedication2);

        assertEquals(2, medications.size());
        assertEquals(110, testUser1.getAsaAmount());
        assertEquals(testMedication1, medications.get(0));
        assertEquals(testMedication2, medications.get(1));

        testUser1.reset();

        assertTrue(testUser1.getMedications().isEmpty());
        assertEquals(0, testUser1.getAsaAmount());
    }

    @Test
    public void testCalculateIntake() {
        testUser1.addMedication(testMedication1);

        assertEquals(0.3, testUser1.calculateIntake());

        testUser1.addMedication(testMedication2);

        assertEquals(1.10, testUser1.calculateIntake());
    }

    @Test
    public void testMedicationList() {
        assertEquals("No Medication Added", testUser1.medicationList());

        testUser1.addMedication(testMedication1);

        assertEquals("testMed1 30mg.", testUser1.medicationList());

        testUser1.addMedication(testMedication2);

        assertEquals("testMed1 30mg,\ntestMed2 80mg.", testUser1.medicationList());
    }
}
