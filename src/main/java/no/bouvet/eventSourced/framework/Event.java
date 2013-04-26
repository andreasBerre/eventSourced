package no.bouvet.eventSourced.framework;

import no.bouvet.eventSourced.Aggregate;

public interface Event {
    abstract Aggregate getAggregate();
}
