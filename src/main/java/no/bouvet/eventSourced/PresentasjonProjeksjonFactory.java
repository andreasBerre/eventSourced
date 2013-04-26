package no.bouvet.eventSourced;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.UntypedActorFactory;

public class PresentasjonProjeksjonFactory implements UntypedActorFactory {

    private ActorRef eventStoreRef;

    public PresentasjonProjeksjonFactory(ActorRef eventStoreRef) {
        this.eventStoreRef = eventStoreRef;
    }

    @Override
    public Actor create() throws Exception {
        return new PresentasjonDeltagerlisteProjeksjon(eventStoreRef);
    }
}
