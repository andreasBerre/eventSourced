package no.bouvet.eventSourced;

import akka.actor.Props;
import akka.testkit.TestActorRef;
import no.bouvet.eventSourced.framework.EventStore;
import no.bouvet.eventSourced.framework.Subscribe;
import org.junit.Test;

public class FrameworkTest extends EventSourcedTestKit{

    @Test
    public void testNyeSubscribersFarTidligereEventsOgSaPafolgendeEvents() throws Exception {
        TestActorRef<EventStore> eventStore = TestActorRef.create(_system, new Props(EventStore.class), "EventStore");

        eventStore.tell(new PresentasjonOpprettet("En presentasjon"), null);
        eventStore.tell(new Subscribe(Aggregate.PRESENTASJON), super.testActor());

        expectMsgClass(PresentasjonOpprettet.class);

        eventStore.tell(new PresentasjonOpprettet("En annen presentasjon"), null);

        expectMsgClass(PresentasjonOpprettet.class);
    }

}
