package no.bouvet.eventSourced;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventStore extends UntypedActor{

    Map<Aggregate, Set<ActorRef>> subscribers = new HashMap<Aggregate, Set<ActorRef>>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Subscribe)
            addSubscription((Subscribe) message);
        else if (message instanceof Event)
            publishEvent((Event) message);
    }

    private void addSubscription(Subscribe subscribe) {
        if (!subscribers.containsKey(subscribe.getAggregate()))
                subscribers.put(subscribe.getAggregate(), new HashSet<ActorRef>());
        subscribers.get(subscribe.getAggregate()).add(sender());
    }

    private void publishEvent(Event event) {
        for (ActorRef projection : subscribers.get(event.getAggregate()))
            projection.tell(event, self());
    }

}
