package ActorManager;

import com.actors.ActorManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ActorManagerCheckLifetimeTest {

    private ActorManager actorManager;
    private ScheduledExecutorService scheduler;

    @BeforeEach
    public void setup() {
        actorManager = new ActorManager.Builder()
        .checkInterval(1000)
        .autoID(false)
        .build();
        actorManager.getAllActors().clear();
        scheduler = Executors.newScheduledThreadPool(1);
    }

    @DisplayName("test sweeper")
    @Test
    public void testCheckLifeTimeUnit() throws InterruptedException {
        // Ajoutez des acteurs avec un lifetime connu
        ActorAsClass actor1 = new ActorAsClass();
        actor1.setLifetime(2000); // 2 secondes
        actor1.setID("actor1");

        ActorAsClass actor2 = new ActorAsClass();
        actor2.setLifetime(10000); // 10 secondes
        actor2.setID("actor2");

        actorManager.addActor(actor1);
        actorManager.addActor(actor2);

        // Planifiez l'exécution de checkLifeTime toutes les secondes
        scheduler.scheduleAtFixedRate(() -> actorManager.checkLifeTime(), 0, 1, TimeUnit.SECONDS);

        // Attendez 4 secondes pour que l'acteur avec un lifetime de 2000 ms soit supprimé
        Thread.sleep(4000);
        scheduler.shutdown();

        // Vérifiez que l'acteur avec un lifetime de 3000 ms a été supprimé
        assertEquals(1, actorManager.getAllActors().size());
        assertNotNull(actorManager.getActorByID("actor2"));
    }

    @DisplayName("test decrement by checkIntervale")
    @Test
    public void DecrementLifetimeTest() throws InterruptedException {
        ActorAsClass a = new ActorAsClass();
        ActorAsClass b = new ActorAsClass();

        a.setLifetime(10000);
        b.setLifetime(8000);

        actorManager.addActor(a);
        actorManager.addActor(b);

        scheduler.scheduleAtFixedRate(() -> actorManager.checkLifeTime(), 0, 1, TimeUnit.SECONDS);

        Thread.sleep(5000);
        scheduler.shutdown();

        assertEquals(5000, a.getLifetime());
        assertEquals(3000, b.getLifetime());
    }
}