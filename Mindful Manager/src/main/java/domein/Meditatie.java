package domein;

public class Meditatie {
    private int idMeditatie;
    private int niveau;
    private int lengte;
    private String meditatieNaam;

    public Meditatie (int idMeditatie, int niveau, int lengte, String meditatieNaam) {
        this(niveau, lengte, meditatieNaam);
        this.idMeditatie = idMeditatie;
    }
    public Meditatie (int niveau, int lengte, String meditatieNaam) {
        this.niveau = niveau;
        this.lengte = lengte;
        this.meditatieNaam = meditatieNaam;
    }


    public String getMeditatieNaam() {
        return meditatieNaam;
    }

    public int getLengte() {
        return lengte;
    }

    public void setLengte(int lengte) {
        this.lengte = lengte;
    }

    public int getNiveau() {
        return niveau;
    }

    public void setNiveau(int niveau) {
        this.niveau = niveau;
    }

    public int getIdMeditatie() {
        return idMeditatie;
    }
}
