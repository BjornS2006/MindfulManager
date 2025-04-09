package domein;

import java.sql.Date;
import java.sql.Time;

public class Tijdslot {
    private int idTijdslot;
    private Date datum;
    private Time starttijd;
    private Time eindtijd;
    private boolean gebruikt;

    public Tijdslot (int idTijdslot, Date datum, Time starttijd, Time eindtijd, boolean gebruikt) {
        this(datum, starttijd, eindtijd, gebruikt);
        this.idTijdslot = idTijdslot;
    }
    public Tijdslot (Date datum, Time starttijd, Time eindtijd, boolean gebruikt) {
        this.datum = datum;
        this.starttijd = starttijd;
        this.eindtijd = eindtijd;
        this.gebruikt = gebruikt;
    }

    public Date getDatum() {
        return datum;
    }

    public Time getStarttijd() {
        return starttijd;
    }

    public Time getEindtijd() {
        return eindtijd;
    }

    public int getIdTijdslot() {
        return idTijdslot;
    }

    public void setIdTijdslot(int idTijdslot) {
        this.idTijdslot = idTijdslot;
    }
}
