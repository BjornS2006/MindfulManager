package utils;

import domein.Tijdslot;

public class TijdslotSession {
    private static Tijdslot geselecteerdTijdslot;

    public static Tijdslot getGeselecteerdTijdslot() {
        return geselecteerdTijdslot;
    }

    public static void setGeselecteerdTijdslot(Tijdslot geselecteerdTijdslot) {
        TijdslotSession.geselecteerdTijdslot = geselecteerdTijdslot;
    }

    public static void clear () {
        geselecteerdTijdslot = null;
    }
}
