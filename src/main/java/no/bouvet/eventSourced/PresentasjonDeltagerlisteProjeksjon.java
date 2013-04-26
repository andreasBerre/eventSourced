package no.bouvet.eventSourced;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PresentasjonDeltagerlisteProjeksjon extends UntypedActor {

    ActorRef eventstore;
    Map<String, List<String>> presentasjoner = new HashMap<String, List<String>>();

    public PresentasjonDeltagerlisteProjeksjon(ActorRef eventstore) {
        this.eventstore = eventstore;
    }

    @Override
    public void preStart() {
        super.preStart();
        eventstore.tell(new Subscribe(Aggregate.PRESENTASJON), self());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof PresentasjonOpprettet)
            handlePresentasjonOpprettet((PresentasjonOpprettet) message);
        else if (message instanceof GetPresentasjonTittel)
            sender().tell(ImmutableList.copyOf(presentasjoner.keySet()), self());
    }

    private void handlePresentasjonOpprettet(PresentasjonOpprettet nyPresentasjonOpprettet) {
        presentasjoner.put(nyPresentasjonOpprettet.getTittel(), new ArrayList<String>());
    }
}
