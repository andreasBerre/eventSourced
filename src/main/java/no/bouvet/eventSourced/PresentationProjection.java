package no.bouvet.eventSourced;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PresentationProjection extends UntypedActor {

    ActorRef eventstore;
    Map<String, List<String>> presentations = new HashMap<String, List<String>>();

    public PresentationProjection(ActorRef eventstore) {
        this.eventstore = eventstore;
    }

    @Override
    public void preStart() {
        super.preStart();
        eventstore.tell(new Subscribe(Aggregate.PRESENTATION), self());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof PresentationCreated)
            handleEvent((PresentationCreated) message);
        else if (message instanceof GetPresentationTitles)
            sender().tell(ImmutableList.copyOf(presentations.keySet()));
    }

    private void handleEvent(PresentationCreated nyPresentasjonOpprettet) {
        presentations.put(nyPresentasjonOpprettet.getTitle(), new ArrayList<String>());
    }
}
