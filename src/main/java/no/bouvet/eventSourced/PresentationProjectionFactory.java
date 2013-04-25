package no.bouvet.eventSourced;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.UntypedActorFactory;

public class PresentationProjectionFactory implements UntypedActorFactory {

    private ActorRef eventStoreRef;

    public PresentationProjectionFactory(ActorRef eventStoreRef) {
        this.eventStoreRef = eventStoreRef;
    }

    @Override
    public Actor create() throws Exception {
        return new PresentationProjection(eventStoreRef);
    }
}
