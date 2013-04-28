package no.bouvet.eventSourced;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.UntypedActorFactory;
import akka.testkit.TestActorRef;

//TODO: Gjøre ferdig, denne klassen skal lage antallIkkeMøtt projeksjonen
public class AntallIkkeMottProjeksjonFactory implements UntypedActorFactory {
    public AntallIkkeMottProjeksjonFactory(ActorRef eventStoreRef) {
    }

    @Override
    public Actor create() throws Exception {
        return null;
    }
}
