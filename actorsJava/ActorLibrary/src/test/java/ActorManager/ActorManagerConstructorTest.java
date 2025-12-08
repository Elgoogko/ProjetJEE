package ActorManager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.actors.ActorManager;
import com.exceptions.ActorManagerException;

public class ActorManagerConstructorTest {

    @DisplayName("Test builder with defaultparameters")
    @Test
    public void defaultTest(){
        ActorManager manager = new ActorManager.Builder().build();
        assertNotNull(manager);
    }

    @DisplayName("test Builder for all parameters")
    @Test
    public void allParametersTest(){
        ActorManager manager = new ActorManager.Builder()
        .actorIdentifier("ABCD")
        .autoID(false)
        .checkInterval(1)
        .build();

        assertEquals("ABCD", manager.getActorIdentifier());
        assertEquals(false, manager.isAutoID());
        assertEquals(1, manager.getCheckInterval());
        assertNotNull(manager.getAllActors());
    }

    @DisplayName("Test builder for null ID format")
    @Test
    public void nullIDparameterTest(){
        assertThrows(ActorManagerException.class, () -> {new ActorManager.Builder().actorIdentifier(null).build();});
    }

    @DisplayName("Test builder for blank ID format")
    @Test
    public void blankIDParameterTest(){
        assertThrows(ActorManagerException.class, () -> {new ActorManager.Builder().actorIdentifier("").build();});
    }

    @DisplayName("Test for negative checkInterval")
    @Test
    public void negativeCheckIntervalTest(){
        assertThrows(ActorManagerException.class, () -> {new ActorManager.Builder().checkInterval(-1000).build();});
    }

    @DisplayName("Test maximum number of actor")
    @Test
    public void negativeOrNullNumberOfActorTest(){
        assertThrows(ActorManagerException.class, () -> {new ActorManager.Builder().maximumNumberOfActors(0);});
        assertThrows(ActorManagerException.class, () -> {new ActorManager.Builder().maximumNumberOfActors(-10);});
    }
}
