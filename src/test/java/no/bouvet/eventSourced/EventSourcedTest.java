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
    private TestActorRef<Actor> presentationProjectionRef;

    @Before
    public void setUp() throws Exception {
        eventStoreRef = TestActorRef.create(_system, new Props(EventStore.class), "eventStoreRef");

        Props props = new Props(new PresentationProjectionFactory(eventStoreRef));
        presentationProjectionRef = TestActorRef.create(_system, props, "presentationProjection");
    }

    @Test
    public void testCreatedPresentationsAreProjected() throws Exception {
        eventStoreRef.tell(new PresentationCreated("Databaser er for pyser!"));

        Future<Object> getPresentationTitles =  ask (presentationProjectionRef, new GetPresentationTitles(), 3000);
        Object result = Await.result(getPresentationTitles, Duration.create(3, TimeUnit.SECONDS));

        assertTrue(result instanceof List);
        assertEquals(1, ((List) result).size());
        assertTrue(((List) result).contains("Databaser er for pyser!"));
    }

    @Test
    @Ignore //TODO:fikse denne testen
    public void testNewAttendingPresentationIsProjected() throws Exception {
        eventStoreRef.tell(new PresentationCreated("Databaser er for pyser!"));
        eventStoreRef.tell(new PresentationAttendeeAdded("Databaser er for pyser!", "Andreas Berre"));
        eventStoreRef.tell(new PresentationAttendeeAdded("Databaser er for pyser!", "Idar Borlaug"));

        Future<Object> getPresentationAttendees =  ask (presentationProjectionRef, new GetPresentationAttendees("Databaser er for pyser!"), 3000);
        Object result = Await.result(getPresentationAttendees, Duration.create(3, TimeUnit.SECONDS));

        assertTrue(result instanceof List);
        assertEquals(2, ((List) result).size());
        assertTrue(((List) result).contains("Andreas Berre"));
        assertTrue(((List) result).contains("Idar Borlaug"));
    }

}
