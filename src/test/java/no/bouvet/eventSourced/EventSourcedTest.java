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

    private TestActorRef<Actor> eventStoreRef;
    private TestActorRef<Actor> presentasjonProjeksjonRef;
    private TestActorRef<Actor> antallIkkeMottProjeksjonRef;

    @Before
    public void setUp() throws Exception {
        Props eventStoreProps = new Props(EventStore.class);
        eventStoreRef = TestActorRef.create(_system, eventStoreProps, "eventStoreRef");

        Props presentasjonProjeksjonProps = new Props(new PresentasjonProjeksjonFactory(eventStoreRef));
        presentasjonProjeksjonRef = TestActorRef.create(_system, presentasjonProjeksjonProps, "presentasjonProjeksjon");

        Props ikkeMottProjeksjonProps = new Props(new AntallIkkeMottProjeksjonFactory(eventStoreRef));
        antallIkkeMottProjeksjonRef = TestActorRef.create(_system, ikkeMottProjeksjonProps, "antallIkkeMottProjeksjon");
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
    @Ignore //TODO: fikse denne testen, må utvide PresentasjonProjeksjon for å håndtere DeltagerLagtTil
    public void testNyeDeltagerePaPresentasjonFinnesIProjeksjon() throws Exception {
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

    @Test
    @Ignore //TODO: fikse denne testen, må lage en ny projeksjon for å holde styr på hvor mange som ikke møter til hver presentasjon
    public void testAntallDeltagereIkkeMott() throws Exception {
        eventStoreRef.tell(new PresentasjonOpprettet("Databaser er for pyser!"), null);
        eventStoreRef.tell(new DeltagerLagtTil("Databaser er for pyser!", "Andreas Berre"), null);
        eventStoreRef.tell(new DeltagerLagtTil("Databaser er for pyser!", "Idar Borlaug"), null);
        eventStoreRef.tell(new DeltagerLagtTil("Databaser er for pyser!", "Endre Stølsvik"), null);
        eventStoreRef.tell(new DeltagerMotte("Databaser er for pyser!", "Idar Borlaug"), null);
        eventStoreRef.tell(new DeltagerMotte("Databaser er for pyser!", "Andreas Berre"), null);

        Future<Object> getAntallIkkeMott =  ask (antallIkkeMottProjeksjonRef, new GetAntallIkkeMott("Databaser er for pyser!"), 3000);
        Object result = Await.result(getAntallIkkeMott, Duration.create(3, TimeUnit.SECONDS));

        assertTrue(result instanceof Integer);
        assertEquals(1, result);
    }


}
