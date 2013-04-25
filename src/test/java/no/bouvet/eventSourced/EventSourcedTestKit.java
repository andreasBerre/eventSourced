package no.bouvet.eventSourced;

import akka.actor.ActorSystem;
import akka.testkit.TestKit;
import com.typesafe.config.ConfigFactory;

public class EventSourcedTestKit extends TestKit {

    protected static ActorSystem _system = ActorSystem.create("EventSourced", ConfigFactory
            .load().getConfig("EventSourced"));

    public EventSourcedTestKit() {
        super(_system);
    }
}
