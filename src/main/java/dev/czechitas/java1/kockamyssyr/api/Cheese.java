package dev.czechitas.java1.kockamyssyr.api;

import java.awt.*;

/**
 * Sýr, potrava pro myš.
 */
public class Cheese extends Player {

    public Cheese(Point point) {
        super(point, "cheese.png", PlayerType.FOOD);
    }

    public Cheese(int x, int y) {
        super(x, y, "cheese.png", PlayerType.FOOD);
    }

}
