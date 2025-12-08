package com.msfilm.doe;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.msfilm.MSFilmApplication;

@SpringBootTest(classes = MSFilmApplication.class)
public class DOEconnectionTest {
    @Autowired
    private DOE instance;

    @Test
    @DisplayName("Empty URL")
    void testEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            instance.testConnection("");
        });
    }

    @Test
    @DisplayName("Null URL")
    void testNull() {
        assertThrows(Exception.class, () -> {
            instance.testConnection(null);
        });
    }

    @DisplayName("Simple connection")
    @Test
    void testConnection() {
        assertTrue(instance.testConnection("https://www.google.fr"));
        assertTrue(instance.testConnection("https://www.amazon.fr"));
        assertTrue(instance.testConnection("https://www.microsoft.fr"));
    }
}
