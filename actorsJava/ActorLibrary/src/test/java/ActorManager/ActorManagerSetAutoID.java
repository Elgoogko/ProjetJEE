package ActorManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.actors.Actor;
import com.actors.ActorManager;
import com.exceptions.ActorManagerException;

public class ActorManagerSetAutoID {
    private static ActorManager manager = new ActorManager.Builder().build();

    @AfterAll
    public static void tearDown(){
        manager.getAllActors().clear();
    }

    @DisplayName("Test single actor ID")
    @Test
    public void singleActorIDtest(){
        Actor a = new Actor();
        manager.addActor(a);
        assertEquals("AM0", a.getId());
    }

    @DisplayName("Multiple Actor ID")
    @Test
    public void multipleActorTest(){
        int i = 0;
        while(i < 10){
            Actor a = new Actor();
            manager.addActor(a);
            assertEquals(manager.getActorIdentifier()+String.valueOf(i), a.getId());
            i = i+1;
        }
    }

    @DisplayName("Give old ID to new actors")
    @Test
    public void oldIDTest(){
        Actor a = new Actor();
        Actor b = new Actor();
        Actor c = new Actor();

        manager.addActor(a);
        manager.addActor(b);

        manager.deleteActor(a);
        manager.addActor(c);
        
        assertEquals(manager.getActorIdentifier()+"0", c.getId());
        assertEquals(manager.getActorIdentifier()+"1", b.getId());
    }

    @DisplayName("Limits to Actor ID")
    @Test
    public void limitIDtest(){
        int i = 0;
        while(i < 1000){
            Actor a = new Actor();
            manager.addActor(a);
            i = i +1;
        }
        Actor b = new Actor();
        assertThrows(ActorManagerException.class, () -> {manager.addActor(b);});
    }

    @DisplayName("Remove unknown actor")
    @Test
    public void removeUnknownActortest(){
        Actor a = new Actor();
        assertFalse(manager.deleteActor(a));
    }
}
