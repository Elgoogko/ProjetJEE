package ActorManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.actors.ActorManager;
import com.exceptions.ActorManagerException;

public class ActorManagerAddAndDeleteTest {
    private static ActorManager manager = new ActorManager.Builder().autoID(false).build();

    @AfterAll
    public static void tearDown() {
        manager.getAllActors().clear();
    }

    @DisplayName("add null actor")
    @Test
    public void addNullActorTest() {
        assertThrows(ActorManagerException.class, () -> {
            manager.addActor(null);
        });
    }

    @DisplayName("Add one actor")
    @Test
    public void addSingleActorTest() {
        ActorAsClass a = new ActorAsClass();
        manager.addActor(a);
        assertTrue(manager.getAllActors().contains(a));
    }

    @DisplayName("Add multiple actors")
    @Test
    public void addMultipleActorTest() {
        ActorAsClass a = new ActorAsClass();
        ActorAsClass b = new ActorAsClass();
        ActorAsClass c = new ActorAsClass();
        manager.addActor(a);
        manager.addActor(b);
        manager.addActor(c);
        assertTrue(manager.getAllActors().contains(a));
        assertTrue(manager.getAllActors().contains(b));
        assertTrue(manager.getAllActors().contains(c));
    }

    @DisplayName("Remove an actor by object")
    @Test
    public void removeSingleActorTest() {
        ActorAsClass a = new ActorAsClass();
        manager.addActor(a);
        manager.deleteActor(a);
        assertFalse(manager.getAllActors().contains(a));
    }

    @DisplayName("get actor by ID")
    @Test
    public void getActorByIDTest() {
        ActorAsClass a = new ActorAsClass();
        a.setID("ABC");
        manager.addActor(a);
        assertEquals(a, manager.getActorByID("ABC"));
    }

    @DisplayName("Remove an actor by id")
    @Test
    public void removeSingleActorbyIDTest() {
        ActorAsClass a = new ActorAsClass();
        a.setID("ABC");
        manager.addActor(a);
        manager.deleteActor("ABC");
        assertFalse(manager.getAllActors().contains(a));
    }
}
