package no.bouvet.eventSourced;

import no.bouvet.eventSourced.framework.Event;

public class DeltagerLagtTil implements Event {
    private final String presentasjonTittel;
    private final String deltagerNavn;

    public DeltagerLagtTil(String presentasjonTittel, String deltagerNavn) {
        this.presentasjonTittel = presentasjonTittel;
        this.deltagerNavn = deltagerNavn;
    }

    public String getPresentasjonTittel() {
        return presentasjonTittel;
    }

    public String getDeltagerNavn() {
        return deltagerNavn;
    }

    @Override
    public Aggregate getAggregate() {
        return Aggregate.PRESENTASJON;
    }
}
