package domein;

import java.util.ArrayList;

public abstract class Gebruiker {
    protected String voornaam;
    protected String achternaam;
    protected String email;
    protected String telnr;
    protected String wachtwoord;
    protected ArrayList<Tijdslot> tijdsloten;
    protected ArrayList<Afspraak> afspraken;
    protected ArrayList<Meditatie> meditaties;

    public Gebruiker(String voornaam, String achternaam, String email, String telnr, String wachtwoord) {
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.telnr = telnr;
        this.wachtwoord = wachtwoord;
    }

    protected abstract ArrayList<Afspraak> getAfspraken();
    protected abstract ArrayList<Meditatie> getMeditaties();
    protected abstract ArrayList<Tijdslot> getTijdsloten();

}
