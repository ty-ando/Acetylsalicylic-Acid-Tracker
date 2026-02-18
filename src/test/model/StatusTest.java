package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {
    private Status test;

    @BeforeEach
    public void setUp() {
        test = new Status();
    }

    @Test
    public void testStatusConstructor() {
        assertEquals("Safe", test.getName());
    }

    @Test
    public void testUpdateStatus() {
        test.updateStatus(99);

        assertEquals("Safe", test.getName());

        test.updateStatus(100);

        assertEquals("Caution", test.getName());

        test.updateStatus(101);

        assertEquals("Caution", test.getName());

        test.updateStatus(199);

        assertEquals("Caution", test.getName());

        test.updateStatus(200);

        assertEquals("Danger", test.getName());

        test.updateStatus(201);

        assertEquals("Danger", test.getName());
    }
}
