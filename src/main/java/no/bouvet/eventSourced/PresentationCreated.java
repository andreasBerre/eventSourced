package no.bouvet.eventSourced;


public class PresentationCreated implements Event {

    private String title;

    public PresentationCreated(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public Aggregate getAggregate() {
        return Aggregate.PRESENTATION;
    }
}
