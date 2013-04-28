package no.bouvet.eventSourced.framework;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import no.bouvet.eventSourced.Aggregate;

import java.util.*;

public class EventStore extends UntypedActor{

    Map<Aggregate, Set<ActorRef>> subscribers = new HashMap<Aggregate, Set<ActorRef>>();
    List<Event> eventLog = new ArrayList<Event>();

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Subscribe){
            addSubscription((Subscribe) message);
        } else if (message instanceof Event) {
            eventLog.add((Event) message);
            publishEvent((Event) message);
        }
    }

    private void addSubscription(Subscribe subscribe) {
        if (!subscribers.containsKey(subscribe.getAggregate()))
                subscribers.put(subscribe.getAggregate(), new HashSet<ActorRef>());
        subscribers.get(subscribe.getAggregate()).add(sender());
        publishEvents(sender(), subscribe.getAggregate());
    }

    private void publishEvent(Event event) {
        if (subscribers.containsKey(event.getAggregate())){
            for (ActorRef projection : subscribers.get(event.getAggregate()))
                projection.tell(event, self());
        }
    }

    private void publishEvents(ActorRef subscriber, Aggregate subscriberAggregate) {
        for (Event event : eventLog){
            if (event.getAggregate().equals(subscriberAggregate))
                subscriber.tell(event);
        }
    }

}
