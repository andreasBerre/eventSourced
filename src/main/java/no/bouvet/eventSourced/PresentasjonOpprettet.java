package no.bouvet.eventSourced;


public class PresentasjonOpprettet implements Event {

    private String tittel;

    public PresentasjonOpprettet(String tittel) {
        this.tittel = tittel;
    }

    public String getTittel() {
        return tittel;
    }

    @Override
    public Aggregate getAggregate() {
        return Aggregate.PRESENTASJON;
    }
}
