package utils;

import domein.Beheerder;

public class BeheerderSession {
    private static Beheerder actieveBeheerder;

    public static void setActieveBeheerder(Beheerder beheerder) {
        actieveBeheerder = beheerder;
    }

    public static Beheerder getActieveBeheerder() {
        return actieveBeheerder;
    }

    public static void clear() {
        actieveBeheerder = null;
    }
}
