package ActorTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.actors.Actor;
import com.exceptions.ActorException;

import ActorManager.ActorAsClass;

public class ActorParametersTest {

    private static Actor instance = null;
    
    @BeforeAll
    public static void riseUp(){
        instance = new ActorAsClass();
    }

    @DisplayName("null id")
    @Test
    public void nullIDTest(){
        assertThrows(NullPointerException.class, () -> {instance.setID(null);});
    }
    
    @DisplayName("blank id")
    @Test
    public void blankIDTest(){
        assertThrows(ActorException.class, () -> {instance.setID("");});
        assertThrows(ActorException.class, () -> {instance.setID("  ");});
    }

    @DisplayName("negative or below required lifetime")
    @Test
    public void negativeOrBelowLifetimeTest(){
        assertThrows(ActorException.class, () -> {instance.setLifetime(500);});
        assertThrows(ActorException.class, () -> {instance.setLifetime(0);});
        assertThrows(ActorException.class, () -> {instance.setLifetime(-500);});
    }

    @DisplayName("test Lifetime setter")
    @Test
    public void lifeTimeSetter(){
        instance.setLifetime(1000);
        assertEquals(1000, instance.getLifetime());
        instance.setLifetime(2500);
        assertEquals(2500, instance.getLifetime());
    }

    @DisplayName("test ID setter")
    @Test
    public void idSetterTest(){
        assertThrows(ActorException.class, () -> {instance.setID("");});
        assertThrows(ActorException.class, () -> {instance.setID("  ");});
    }
}
