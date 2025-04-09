package domein;


import utils.ClientSession;
import utils.DatabaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Client extends Gebruiker implements IZoek {
    private int idClient;
    private int niveau;
    private String adres;
    private String woonplaats;
    private Date geboortedatum;
    private String notities;

    public Client(String voornaam, String achternaam, String email, String telnr, String wachtwoord, int idClient
    ,int niveau, String adres, String woonplaats, String notities, Date geboortedatum) {
        super(voornaam, achternaam, email, telnr, wachtwoord);
        this.idClient = idClient;
        this.niveau = niveau;
        this.adres = adres;
        this.woonplaats = woonplaats;
        this.notities = notities;
        this.geboortedatum = geboortedatum;
        this.meditaties = getMeditaties();
        this.afspraken = getAfspraken();
        this.tijdsloten = getTijdsloten();
    }
    public Client (String voornaam, String achternaam, String email, String telnr, String wachtwoord
            ,int niveau, String adres, String woonplaats, String notities, Date geboortedatum) {
        super(voornaam, achternaam, email, telnr, wachtwoord);
        this.niveau = niveau;
        this.adres = adres;
        this.woonplaats = woonplaats;
        this.notities = notities;
        this.geboortedatum = geboortedatum;
        this.meditaties = getMeditaties();
        this.afspraken = getAfspraken();
        this.tijdsloten = getTijdsloten();
    }



    @Override
    protected ArrayList<Afspraak> getAfspraken() {
        afspraken = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT idAfspraak, starttijd, eindtijd, datum, Voornaam, Achternaam "
                    + "FROM afspraak "
                    + "JOIN client ON afspraak.Client_idClient = client.idClient "
                    + "JOIN tijdslot ON afspraak.Tijdslot_idTijdslot = tijdslot.idTijdslot "
                    + "WHERE afspraak.Client_idClient = ? "
                    + "ORDER BY datum";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, this.idClient);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String naam = rs.getString("Voornaam") + " " + rs.getString("Achternaam");
                Afspraak afspraak = new Afspraak(rs.getInt("idAfspraak"), rs.getTime("starttijd"), rs.getTime("eindtijd"), rs.getDate("datum"), naam);
                afspraken.add(afspraak);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return afspraken;
    }
    @Override
    protected ArrayList<Meditatie> getMeditaties() {
        meditaties = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT idMeditatie, niveau, lengte, meditatieNaam FROM meditatie WHERE niveau = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ClientSession.getActieveClient().getNiveau());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Meditatie meditatie = new Meditatie(rs.getInt("idMeditatie"), rs.getInt("niveau"), rs.getInt("lengte"), rs.getString("meditatieNaam"));
                meditaties.add(meditatie);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return meditaties;
    }
    @Override
    protected ArrayList<Tijdslot> getTijdsloten() {
        tijdsloten = new ArrayList<>();
        try (Connection conn = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM tijdslot WHERE gebruikt = false";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Tijdslot tijdslot = new Tijdslot(rs.getInt("idTijdslot"), rs.getDate("datum"),
                        rs.getTime("starttijd"), rs.getTime("eindtijd"), rs.getBoolean("gebruikt"));
                tijdsloten.add(tijdslot);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tijdsloten;
    }


    public void afspraakAanmaken(Tijdslot tijdslot) throws SQLException{
        Afspraak nieuweAfspraak = new Afspraak(tijdslot.getStarttijd(), tijdslot.getEindtijd(), tijdslot.getDatum(), ClientSession.getActieveClient().getNaam());
        ClientSession.getActieveClient().getAfspraakList().add(nieuweAfspraak);
        ClientSession.getActieveClient().getTijdslotList().remove(tijdslot);
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO afspraak (Client_idClient, Beheerder_idBeheerder, Tijdslot_idTijdslot) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ClientSession.getActieveClient().getIdClient());
            statement.setInt(2, 1);
            statement.setInt(3, tijdslot.getIdTijdslot());
            statement.executeUpdate();
        }
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "UPDATE tijdslot SET gebruikt = true WHERE idTijdslot = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, tijdslot.getIdTijdslot());
            statement.executeUpdate();
        }

    }
    public boolean zoekMeditatie (String zoekterm, Meditatie meditatie) {
        if (meditatie.getMeditatieNaam().toLowerCase().contains(zoekterm)) {
            return true;
        }
        return false;
    }

    public boolean zoekTijdslot (String zoekterm, Tijdslot tijdslot) {
        java.sql.Date sqlDate = tijdslot.getDatum();
        LocalDate localDate = sqlDate.toLocalDate();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate zoekDate = LocalDate.parse(zoekterm, formatter);

        return zoekDate.equals(localDate);


    }
    public String getNaam() {
        return voornaam;
    }
    public ArrayList<Meditatie> getMeditatieList () {
        return this.meditaties;
    }
    public ArrayList<Afspraak> getAfspraakList () {
        return this.afspraken;
    }
    public ArrayList<Tijdslot> getTijdslotList () {
        return this.tijdsloten;
    }
    public int getIdClient () {
        return this.idClient;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getNotities() {
        return notities;
    }

    public void setNotities(String notities) {
        this.notities = notities;
    }
    public int getNiveau () {
        return this.niveau;
    }
    public String getEmail () {
        return this.email;
    }
    public String getTelnr () {
        return this.telnr;
    }
    public void setNiveau (int niveau) {
        this.niveau = niveau;
    }
}
