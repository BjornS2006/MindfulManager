package utils;

import domein.Client;

public class ClientSession {
    private static Client actieveClient;

    public static void setActieveClient(Client client) {
        actieveClient = client;
    }

    public static Client getActieveClient() {
        return actieveClient;
    }

    public static void clear() {
        actieveClient = null;
    }
}
