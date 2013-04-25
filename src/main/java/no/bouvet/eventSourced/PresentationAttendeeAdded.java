package no.bouvet.eventSourced;

public class PresentationAttendeeAdded implements Event {
    private final String presentationTitle;
    private final String attendeeName;

    public PresentationAttendeeAdded(String presentationTitle, String attendeeName) {
        this.presentationTitle = presentationTitle;
        this.attendeeName = attendeeName;
    }

    public String getPresentationTitle() {
        return presentationTitle;
    }

    public String getAttendeeName() {
        return attendeeName;
    }

    @Override
    public Aggregate getAggregate() {
        return Aggregate.PRESENTATION;
    }
}
