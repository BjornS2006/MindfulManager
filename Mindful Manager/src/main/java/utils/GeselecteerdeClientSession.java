package utils;

import domein.Client;

public class GeselecteerdeClientSession {
    private static Client geselecteerdeClient;


    public static Client getGeselecteerdeClient() {
        return geselecteerdeClient;
    }

    public static void setGeselecteerdeClient(Client geselecteerdeClient) {
        GeselecteerdeClientSession.geselecteerdeClient = geselecteerdeClient;
    }

    public static void clear () {
        geselecteerdeClient = null;
    }

}
