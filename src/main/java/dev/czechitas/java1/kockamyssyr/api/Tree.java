package dev.czechitas.java1.kockamyssyr.api;

import dev.czechitas.java1.kockamyssyr.engine.Gameplay;
import dev.czechitas.java1.kockamyssyr.engine.swing.Utils;

import javax.swing.*;
import java.awt.*;

/**
 * Strom, který tvoří překážku na hrací ploše – neprojde přes něj kočka ani myš.
 */
public class Tree extends Figure implements Stackable {

    private Icon stackedImage;

    public Tree(Point point) {
        super(point, "tree.png");
        init();
    }

    public Tree(int x, int y) {
        super(x, y, "tree.png");
        init();
    }

    private void init() {
        Utils.invokeAndWait(() -> {
            stackedImage = Utils.loadSprite("tree-stacked.png");
        });
        Gameplay.getInstance().addPassiveFigure(this);
    }

    @Override
    public void remove() {
        Gameplay.getInstance().removePassiveFigure(this);
        super.remove();
    }

    @Override
    public Icon getStackableIcon() {
        return stackedImage;
    }
}
