package dev.czechitas.java1.kockamyssyr.api;

/**
 * Rozhraní pro „mozek“ ovládající zvolenou postavu.
 * <p>
 * „Mozek“ může být například kód programu nebo klávesnice, pomocí které ovládá postavu hráč.
 */
public interface Brain {

    void controlPlayer(Player p);

}
