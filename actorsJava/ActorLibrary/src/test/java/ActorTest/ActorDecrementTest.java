package ActorTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.actors.Actor;
import com.exceptions.ActorException;

public class ActorDecrementTest {  

    private static Actor instance= null;

    @BeforeAll
    public static void riseUp(){
        instance = new Actor();
        instance.setLifetime(5000);
    }

    @DisplayName("Decrement negative number")
    @Test
    public void decrementNegativeNumberTest(){
        assertThrows(ActorException.class, () -> {instance.decrementLifetime(-1000);});
    }

    @DisplayName("loop Test")
    @Test
    public void loopTestTest(){
        int i = 0;
        while (i < 10) {
            assertEquals(5000-(i*500), instance.getLifetime());
            instance.decrementLifetime(500);
            i=i+1;
        }
    }
}
