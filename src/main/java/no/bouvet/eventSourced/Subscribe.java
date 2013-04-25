package no.bouvet.eventSourced;


public class Subscribe {
    private Aggregate aggregate;

    public Subscribe(Aggregate aggregate) {
        this.aggregate = aggregate;
    }

    public Aggregate getAggregate() {
        return aggregate;
    }
}
