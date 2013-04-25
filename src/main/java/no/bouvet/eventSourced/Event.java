package no.bouvet.eventSourced;

public interface Event {
    abstract Aggregate getAggregate();
}
