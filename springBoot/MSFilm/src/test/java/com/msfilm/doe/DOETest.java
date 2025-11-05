package com.msfilm.doe;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.msfilm.msfilm.MSFilmApplication;

@SpringBootTest(classes = MSFilmApplication.class)
public class DOETest {

    @Autowired
    private DOE doe;

    @Test
    void testConnection() {
        assertTrue(doe.testConnection());
    }
}
