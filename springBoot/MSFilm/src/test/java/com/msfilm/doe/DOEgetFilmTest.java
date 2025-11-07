package com.msfilm.doe;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

import com.msfilm.msfilm.MSFilmApplication;

@SpringBootTest(classes = MSFilmApplication.class)
public class DOEgetFilmTest {
    private static DOE instance;

    @BeforeAll
    static void riseUp(){
        instance = DOE.getInstance();
    }
    /* 
    @Test 
    void testAPI(){
        try {
            Map<String, Object> test = instance.getJsonAsMap("https://www.omdbapi.com/?t=$Avengers&apikey=f9a63c9c");
            System.out.println(test);
            assertEquals("aaa",test);
           } catch (Exception e) {
        }
    }
        */
}
