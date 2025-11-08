package com.msfilm.doe;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.msfilm.msfilm.MSFilmApplication;

@SpringBootTest(classes = MSFilmApplication.class)
public class DOEgetFilmTest {
    private static DOE instance;
    private static Map<String, Object> test = new HashMap<>();
}