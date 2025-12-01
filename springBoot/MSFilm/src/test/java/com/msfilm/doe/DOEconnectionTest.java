package com.msfilm.doe;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.msfilm.MSFilmApplication;

@SpringBootTest(classes = MSFilmApplication.class)
public class DOEconnectionTest {
    @Autowired
    private static DOE instance;

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
    @MethodSource("data")
    @ParameterizedTest
    void testConnection(String url, boolean expected) throws Exception {
        assertEquals(expected, instance.testConnection(url));
    }

    static List<Object> data() {
        return Arrays.asList(new Object[] { "https://www.google.com", true },
                new Object[] { "https://www.yotube.com", true });
    }
}
