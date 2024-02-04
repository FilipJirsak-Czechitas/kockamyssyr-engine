package dev.czechitas.java1.kockamyssyr.api;

import dev.czechitas.java1.kockamyssyr.engine.swing.MainWindow;
import dev.czechitas.java1.kockamyssyr.engine.swing.Utils;

import java.awt.*;

/**
 * This class is just an object-oriented wrapper for the Gameplay singleton
 * to avoid explanation of factory methods (<code>Gameplay.getInstance()</code>) to students.
 * You can instantiate as many GameManagers as you want,
 * and they all will delegate its methods to the Gameplay singleton.
 * <p>
 * Usage:
 * <pre>
 * GameManager manager;
 * manager = new GameManager();
 * manager.getSize();
 * </pre>
 */
public class GameManager {

    public int getWidth() {
        return Utils.invokeAndWait(() -> {
            return MainWindow.getInstance().getContentPane().getWidth();
        });
    }

    public int getHeight() {
        return Utils.invokeAndWait(() -> {
            return MainWindow.getInstance().getContentPane().getHeight();
        });
    }

    public Dimension getSize() {
        return Utils.invokeAndWait(() -> {
            return MainWindow.getInstance().getContentPane().getSize();
        });
    }

}
