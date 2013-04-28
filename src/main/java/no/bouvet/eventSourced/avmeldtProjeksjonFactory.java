package no.bouvet.eventSourced;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.UntypedActorFactory;
import akka.testkit.TestActorRef;

//TODO: Gjøre ferdig, denne klassen skal lage antallIkkeMøtt projeksjonen
public class avmeldtProjeksjonFactory implements UntypedActorFactory {
    public avmeldtProjeksjonFactory(ActorRef eventStoreRef) {
    }

    @Override
    public Actor create() throws Exception {
        return null;
    }
}
