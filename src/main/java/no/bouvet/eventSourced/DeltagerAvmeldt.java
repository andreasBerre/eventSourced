package no.bouvet.eventSourced;

public class DeltagerAvmeldt {
    private final String presentasjonTittel;
    private final String deltagerNavn;

    public DeltagerAvmeldt(String presentasjonTittel, String deltagerNavn) {
        this.presentasjonTittel = presentasjonTittel;
        this.deltagerNavn = deltagerNavn;
    }

    public String getPresentasjonTittel() {
        return presentasjonTittel;
    }

    public String getDeltagerNavn() {
        return deltagerNavn;
    }
}
