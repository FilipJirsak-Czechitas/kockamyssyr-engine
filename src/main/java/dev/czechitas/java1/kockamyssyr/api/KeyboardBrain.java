package dev.czechitas.java1.kockamyssyr.api;

import dev.czechitas.java1.kockamyssyr.engine.swing.MainWindow;
import dev.czechitas.java1.kockamyssyr.engine.swing.Utils;
import net.sevecek.util.ThreadUtils;
import net.sevecek.util.swing.JKeyboard;

/**
 * Mozek, který ovládá danou postavu pomocí klávesnice.
 * <p>
 * Výchozí ovládání je pomocí šipek, ale je možné ovládací klávesy změnit použitím parametrického konstruktoru.
 */
public class KeyboardBrain implements Brain {

    private int keyCodeLeft;
    private int keyCodeUp;
    private int keyCodeRight;
    private int keyCodeDown;

    /**
     * Vytvoří mozek ovládaný pomocí standardních kláves – šipek.
     */
    public KeyboardBrain() {
        this(KeyCode.UP, KeyCode.LEFT, KeyCode.DOWN, KeyCode.RIGHT);
    }

    /**
     * Vytvoří mozek ovládaný pomocí alternativních kláves.
     *
     * @param keyCodeUp
     * @param keyCodeLeft
     * @param keyCodeDown
     * @param keyCodeRight
     */
    public KeyboardBrain(KeyCode keyCodeUp, KeyCode keyCodeLeft, KeyCode keyCodeDown, KeyCode keyCodeRight) {
        this.keyCodeLeft = keyCodeLeft.getKeyEventVkCode();
        this.keyCodeUp = keyCodeUp.getKeyEventVkCode();
        this.keyCodeRight = keyCodeRight.getKeyEventVkCode();
        this.keyCodeDown = keyCodeDown.getKeyEventVkCode();
    }

    @Override
    public void controlPlayer(Player player) {
        while (!Thread.currentThread().isInterrupted()) {
            ThreadUtils.sleep(20L);
            Utils.invokeAndWait(() -> {
                JKeyboard keyboard = MainWindow.getInstance().getKeyboard();
                if (keyboard.isKeyDown(keyCodeUp)) {
                    player.setOrientation(PlayerOrientation.UP);
                    player.moveForwardInternal();
                }
                if (keyboard.isKeyDown(keyCodeDown)) {
                    player.setOrientation(PlayerOrientation.DOWN);
                    player.moveForwardInternal();
                }
                if (keyboard.isKeyDown(keyCodeLeft)) {
                    player.setOrientation(PlayerOrientation.LEFT);
                    player.moveForwardInternal();
                }
                if (keyboard.isKeyDown(keyCodeRight)) {
                    player.setOrientation(PlayerOrientation.RIGHT);
                    player.moveForwardInternal();
                }
                player.getSprite().repaint();
            });
        }
    }

    public int getKeyCodeLeft() {
        return keyCodeLeft;
    }

    public int getKeyCodeUp() {
        return keyCodeUp;
    }

    public int getKeyCodeRight() {
        return keyCodeRight;
    }

    public int getKeyCodeDown() {
        return keyCodeDown;
    }
}
