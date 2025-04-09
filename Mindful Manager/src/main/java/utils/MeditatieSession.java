package utils;

import domein.Meditatie;

public class MeditatieSession {
    private static Meditatie geselecteerdeMeditatie;


    public static Meditatie getGeselecteerdeMeditatie() {
        return geselecteerdeMeditatie;
    }

    public static void setGeselecteerdeMeditatie(Meditatie geselecteerdeMeditatie) {
        MeditatieSession.geselecteerdeMeditatie = geselecteerdeMeditatie;
    }

    public static void clear () {
        geselecteerdeMeditatie = null;
    }
}
