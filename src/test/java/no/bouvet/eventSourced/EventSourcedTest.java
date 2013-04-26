package no.bouvet.eventSourced;

import akka.actor.Actor;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class EventSourcedTest extends EventSourcedTestKit{

    private TestActorRef<EventStore> eventStoreRef;
    private TestActorRef<Actor> presentasjonProjeksjonRef;

    @Before
    public void setUp() throws Exception {
        Props eventStoreProps = new Props(EventStore.class);
        eventStoreRef = TestActorRef.create(_system, eventStoreProps, "eventStoreRef");

        Props projeksjonProps = new Props(new PresentasjonProjeksjonFactory(eventStoreRef));
        presentasjonProjeksjonRef = TestActorRef.create(_system, projeksjonProps, "presentatasjonProjeksjon");
    }

    @Test
    public void testOpprettedePresentasjonerFinnesIProjeksjon() throws Exception {
        eventStoreRef.tell(new PresentasjonOpprettet("Databaser er for pyser!"), null);

        Future<Object> getPresentasjonTittel =  ask (presentasjonProjeksjonRef, new GetPresentasjonTittel(), 3000);
        Object result = Await.result(getPresentasjonTittel, Duration.create(3, TimeUnit.SECONDS));

        assertTrue(result instanceof List);
        assertEquals(1, ((List) result).size());
        assertTrue(((List) result).contains("Databaser er for pyser!"));
    }

    @Test
    @Ignore //TODO: fikse denne testen
    public void testNyeDeltagerePåPresentasjonFinnesIProjeksjon() throws Exception {
        eventStoreRef.tell(new PresentasjonOpprettet("Databaser er for pyser!"), null);
        eventStoreRef.tell(new DeltagerLagtTil("Databaser er for pyser!", "Andreas Berre"), null);
        eventStoreRef.tell(new DeltagerLagtTil("Databaser er for pyser!", "Idar Borlaug"), null);

        Future<Object> getPresentasjonDeltagere =  ask (presentasjonProjeksjonRef, new GetPresentasjonDeltagere("Databaser er for pyser!"), 3000);
        Object result = Await.result(getPresentasjonDeltagere, Duration.create(3, TimeUnit.SECONDS));

        assertTrue(result instanceof List);
        assertEquals(2, ((List) result).size());
        assertTrue(((List) result).contains("Andreas Berre"));
        assertTrue(((List) result).contains("Idar Borlaug"));
    }

}
