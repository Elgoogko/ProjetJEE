package com.msfilm.doe;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import com.msfilm.msfilm.MSFilmApplication;

@SpringBootTest(classes = MSFilmApplication.class)
public class DOETest {
    private static DOE instance;

    @BeforeAll
    static void riseUp(){
        instance = DOE.getInstance();
    }

    @Test
    @DisplayName("Empty URL")
    void testEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {instance.testConnection("");});
    }

    @Test
    @DisplayName("Null URL")
    void testNull(){
        assertThrows(NullPointerException.class, () -> {instance.testConnection(null);});
    }

    @DisplayName("Simple connection")
    @MethodSource("Data")
    @ParameterizedTest
    void testConnection(String url, boolean expected){
        assertEquals(expected, instance.testConnection(url));
    }

    static List<Object> data(){
        return Arrays.asList(new Object[][] {{"https://www.google.com", true}, {"https://www.yotube.com", true}, {"https://www.zakjdoazidjpoaz.com", false}, {"https://www.azrtyuiop.fr", false}});
    }
}
