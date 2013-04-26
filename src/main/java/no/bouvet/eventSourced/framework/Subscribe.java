package no.bouvet.eventSourced.framework;


import no.bouvet.eventSourced.Aggregate;

public class Subscribe {
    private Aggregate aggregate;

    public Subscribe(Aggregate aggregate) {
        this.aggregate = aggregate;
    }

    public Aggregate getAggregate() {
        return aggregate;
    }
}
