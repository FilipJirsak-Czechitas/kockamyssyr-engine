package dev.czechitas.java1.kockamyssyr.api;

/**
 * Výčet možných typů herních prvků (objektů a hráčů).
 */
public enum PlayerType {

    FOOD,
    GOOD,
    BAD;

    /**
     * Vrací {@code true}, pokud se tento typ hráče snaží chytit/získat zadaný typ hráče.
     *
     * @param otherPlayerType
     * @return
     */
    public boolean isCatching(PlayerType otherPlayerType) {
        if (this == BAD && otherPlayerType == GOOD) {
            return true;
        }
        return this == GOOD && otherPlayerType == FOOD;
    }
}
