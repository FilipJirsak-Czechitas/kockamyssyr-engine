package dev.czechitas.java1.kockamyssyr.api;

import dev.czechitas.java1.kockamyssyr.engine.Gameplay;
import dev.czechitas.java1.kockamyssyr.engine.swing.Utils;
import net.sevecek.util.ThreadUtils;

import javax.swing.*;
import java.awt.*;

import static dev.czechitas.java1.kockamyssyr.api.CollisionType.NO_COLLISION;

/**
 * Postava hry (například myš nebo kočka).
 * <p>
 * Postava může provádět různé akce, například se pohybovat.
 */
public abstract class Player extends Figure {

    private final PlayerType type;
    private PlayerOrientation orientation;
    private Brain brain;

    protected Player(Point point, String pictureName, PlayerType type) {
        super(point, pictureName);
        this.type = type;
        this.orientation = PlayerOrientation.RIGHT;
        Gameplay.getInstance().addPlayer(this);
    }

    protected Player(int x, int y, String pictureName, PlayerType type) {
        super(x, y, pictureName);
        this.type = type;
        this.orientation = PlayerOrientation.RIGHT;
        Gameplay.getInstance().addPlayer(this);
    }

    /**
     * Vrací „mozek“ postavy, který ji právě ovládá.
     *
     * @return
     */
    public Brain getBrain() {
        return brain;
    }

    /**
     * Dá postavě „mozek“, který postavu může ovládat.
     *
     * @param brain
     */
    public void setBrain(Brain brain) {
        if (this.brain != null) {
            Gameplay.getInstance().stopMoving(this);
        }
        this.brain = brain;
        Gameplay.getInstance().startMoving(this);
    }

    /**
     * Vrací aktuální orientaci postavy (kterým směrem je otočená).
     *
     * @return
     */
    public PlayerOrientation getOrientation() {
        return orientation;
    }

    protected void setOrientation(PlayerOrientation orientation) {
        this.orientation = orientation;
        repaint();
    }

    /**
     * Posune henrí postavu o 10 bodů vpřed.
     */
    public void moveForward() {
        moveForward(10);
    }

    /**
     * Posune herní postavu o zadaný počet bodů vpřed.
     *
     * @param pixels
     */
    public void moveForward(int pixels) {
        for (int i = 0; i < pixels / 5; i++) {
            ThreadUtils.sleep(20L);
            Utils.invokeLater(() -> {
                moveForwardInternal();
                repaint();
            });
        }
    }

    void moveForwardInternal() {
        if (!isPossibleToMoveForward()) return;

        JComponent sprite = getSprite();
        Point location = sprite.getLocation();
        if (getOrientation() == PlayerOrientation.RIGHT) {
            location.x += 5;
        }
        if (getOrientation() == PlayerOrientation.LEFT) {
            location.x -= 5;
        }
        if (getOrientation() == PlayerOrientation.UP) {
            location.y -= 5;
        }
        if (getOrientation() == PlayerOrientation.DOWN) {
            location.y += 5;
        }
        // Align to 5x5 grid
        location.x = location.x - location.x % 5;
        location.y = location.y - location.y % 5;
        sprite.setLocation(location);

        Gameplay.getInstance().detectCollisionBetweenPlayers();
    }

    /**
     * Otočí postavu vlevo (o 90°).
     */
    public void turnLeft() {
        ThreadUtils.sleep(10L);
        Utils.invokeLater(() -> {
            switch (getOrientation()) {
                case UP:
                    setOrientation(PlayerOrientation.LEFT);
                    break;
                case LEFT:
                    setOrientation(PlayerOrientation.DOWN);
                    break;
                case DOWN:
                    setOrientation(PlayerOrientation.RIGHT);
                    break;
                case RIGHT:
                    setOrientation(PlayerOrientation.UP);
                    break;
            }
        });
    }

    /**
     * Otočí postavu pravo (o 90°).
     */
    public void turnRight() {
        ThreadUtils.sleep(10L);
        Utils.invokeLater(() -> {
            switch (getOrientation()) {
                case UP:
                    setOrientation(PlayerOrientation.RIGHT);
                    break;
                case LEFT:
                    setOrientation(PlayerOrientation.UP);
                    break;
                case DOWN:
                    setOrientation(PlayerOrientation.LEFT);
                    break;
                case RIGHT:
                    setOrientation(PlayerOrientation.DOWN);
                    break;
            }
        });
    }

    /**
     * Zjistí, zda se postava může posunout vpřed (zda nestojí před překážkou nabo na okraji herní plochy).
     *
     * @return
     */
    public boolean isPossibleToMoveForward() {
        return Utils.invokeAndWait(() -> {
            JLabel sprite = getSprite();
            Point location = sprite.getLocation();
            Point originalLocation = new Point(location);
            if (getOrientation() == PlayerOrientation.RIGHT) {
                location.x += 5;
            }
            if (getOrientation() == PlayerOrientation.LEFT) {
                location.x -= 5;
            }
            if (getOrientation() == PlayerOrientation.UP) {
                location.y -= 5;
            }
            if (getOrientation() == PlayerOrientation.DOWN) {
                location.y += 5;
            }
            location.x = location.x - location.x % 5;
            location.y = location.y - location.y % 5;

            if (location.x < 0 || location.y < 0
                || location.x + sprite.getWidth() > sprite.getParent().getWidth()
                || location.y + sprite.getHeight() > sprite.getParent().getHeight()) {
                return false;
            }

            sprite.setLocation(location);
            boolean result = Gameplay.getInstance().detectCollisionWithPassiveFigures(this) == NO_COLLISION;
            sprite.setLocation(originalLocation);
            return result;
        }).booleanValue();
    }

    public PlayerType getType() {
        return type;
    }

    @Override
    public void remove() {
        Gameplay.getInstance().removePlayer(this);
        super.remove();
    }
}
