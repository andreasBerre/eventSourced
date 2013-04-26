package no.bouvet.eventSourced;

public class GetPresentasjonDeltagere {
    private String presentasjonTittel;

    public GetPresentasjonDeltagere(String presentasjonTittel) {
        this.presentasjonTittel = presentasjonTittel;
    }

    public String getPresentasjonTittel() {
        return presentasjonTittel;
    }
}
