package domein;

import java.sql.Date;

public interface IZoek {
    boolean zoekMeditatie(String zoekterm, Meditatie meditatie);
    boolean zoekTijdslot(String zoekterm, Tijdslot tijdslot);
}
