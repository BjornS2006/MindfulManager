package domein;

import utils.BeheerderSession;
import utils.DatabaseUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;



public class Beheerder extends Gebruiker implements IZoek {
    private int idBeheerder;
    private ArrayList<Client> clienten;

    public Beheerder(String voornaam, String achternaam, String email, String telnr, String wachtwoord) {
        super(voornaam, achternaam, email, telnr, wachtwoord);
        this.meditaties = getMeditaties();
        this.afspraken = getAfspraken();
        this.tijdsloten = getTijdsloten();
        this.clienten = getClienten();
    }


    public boolean zoekClient(String zoekterm, Client client) {
        if (client.getNaam().toLowerCase().contains(zoekterm.toLowerCase())) {
            return true;
        }
        return false;
    }
    private ArrayList<Client> getClienten() {
        clienten = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT * FROM client";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Client client = new Client(

                        rs.getInt("idClient"),
                        rs.getString("voornaam"),
                        rs.getString("achternaam"),
                        rs.getString("email"),
                        rs.getString("telnr"),
                        rs.getString("wachtwoord"),
                        rs.getInt("niveau"),
                        rs.getString("adres"),
                        rs.getString("woonplaats"),
                        rs.getString("notities"),
                        rs.getDate("geboortedatum")
                );
                clienten.add(client);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clienten;
    }

    @Override
    protected ArrayList<Afspraak> getAfspraken() {
        afspraken = new ArrayList<>();
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT idAfspraak, starttijd, eindtijd, datum, Voornaam, Achternaam FROM afspraak "
                    + "JOIN client ON afspraak.Client_idClient = client.idClient "
                    + "JOIN tijdslot ON afspraak.Tijdslot_idTijdslot = tijdslot.idTijdslot "
                    + "ORDER BY datum";

            PreparedStatement statement = connection.prepareStatement(query);
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
            String query = "SELECT idMeditatie, niveau, lengte, meditatieNaam FROM meditatie";
            PreparedStatement statement = connection.prepareStatement(query);
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



    public boolean zoekMeditatie (String zoekterm, Meditatie meditatie) {
        if (meditatie.getMeditatieNaam().toLowerCase().contains(zoekterm)) {
            return true;
        }
        return false;
    }

    public boolean zoekTijdslot (String zoekterm, Tijdslot tijdslot) {
        Date sqlDate = tijdslot.getDatum();
        LocalDate localDate = sqlDate.toLocalDate();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate zoekDate = LocalDate.parse(zoekterm, formatter);

        return zoekDate.equals(localDate);


    }
    public void meditatieAanmaken(String naam, int niveau, int lengte, File spraakBestand) throws SQLException {


        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO meditatie (lengte, meditatieNaam, niveau, spraakBestand, Beheerder_idBeheerder) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, lengte);
            statement.setString(2, naam);
            statement.setInt(3, niveau);
            statement.setBlob(4, new FileInputStream(spraakBestand));
            statement.setInt(5, 1);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int id = 0;
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT MAX(idMeditatie) FROM meditatie";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt("MAX(idMeditatie)");
            }


        }
        Meditatie nieuweMeditatie = new Meditatie(id, niveau, lengte, naam);
        BeheerderSession.getActieveBeheerder().getMeditatieList().add(nieuweMeditatie);
    }
    public void tijdslotAanmaken (Date datum, Time starttijd, Time eindtijd) throws SQLException {

        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "INSERT INTO tijdslot (datum, starttijd, eindtijd, Beheerder_idBeheerder, gebruikt) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDate(1, datum);
            statement.setTime(2, starttijd);
            statement.setTime(3, eindtijd);
            statement.setInt(4, 1);
            statement.setBoolean(5, false);
            statement.executeUpdate();
        }
        int id = 0;
        try (Connection connection = DatabaseUtil.getConnection()) {
            String query = "SELECT MAX(idTijdslot) FROM tijdslot";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt("MAX(idTijdslot)");
            }
        }
        Tijdslot nieuwTijdslot = new Tijdslot(id, datum, starttijd, eindtijd, false);
        BeheerderSession.getActieveBeheerder().getTijdslotList().add(nieuwTijdslot);
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
    public String getNaam () {
        return this.voornaam;
    }
    public ArrayList<Client> getClientenList () {
        return this.clienten;
    }
}

