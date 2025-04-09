package domein;

import java.sql.Date;
import java.sql.Time;

public class Afspraak {
    private int idAfspraak;
    private Time starttijd;
    private Time eindtijd;
    private Date datum;
    private String clientNaam;

    public Afspraak(int idAfspraak, Time starttijd, Time eindtijd, Date datum, String clientNaam) {
        this(starttijd, eindtijd, datum, clientNaam);
        this.idAfspraak = idAfspraak;
    }

    public Afspraak(Time starttijd, Time eindtijd, Date datum, String clientNaam) {
        this.starttijd = starttijd;
        this.eindtijd = eindtijd;
        this.datum = datum;
        this.clientNaam = clientNaam;
    }

    public Time getStarttijd() {
        return starttijd;
    }

    public Time getEindtijd() {
        return eindtijd;
    }

    public Date getDatum() {
        return datum;
    }

    public String getClientNaam() {
        return clientNaam;
    }
    public void setStarttijd(Time starttijd) {
        this.starttijd = starttijd;
    }
    public void setEindtijd(Time eindtijd) {
        this.eindtijd = eindtijd;
    }
    public void setDatum(Date datum) {
        this.datum = datum;
    }
    public void setClientNaam(String clientNaam) {
        this.clientNaam = clientNaam;
    }

}
