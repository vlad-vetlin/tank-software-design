package ru.mipt.bit.platformer.util.levels;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.ICanBeRendered;
import ru.mipt.bit.platformer.util.obstacles.AbstractObstacle;
import ru.mipt.bit.platformer.util.players.AbstractPlayer;

import java.util.ArrayList;

public final class Map implements ICanBeRendered, Disposable {
    private AbstractPlayer player;

    private final ArrayList<AbstractObstacle> obstacles;

    public Map() {
        obstacles = new ArrayList<>();
    }

    public void addPlayer(AbstractPlayer player) {
        this.player = player;
    }

    public AbstractPlayer getPlayer() {
        return player;
    }

    public void addObstacle(AbstractObstacle obstacle) {
        obstacles.add(obstacle);
    }

    public boolean hasObstacle(GridPoint2 point) {
        // obstacle are few, because of it we can use linear algo
        for (AbstractObstacle obstacle : obstacles) {
            if (point.equals(obstacle.getCoordinates())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void render() {
        player.render();

        for (AbstractObstacle obstacle : obstacles) {
            obstacle.render();
        }
    }

    @Override
    public void dispose() {
        player.dispose();

        for (AbstractObstacle obstacle : obstacles) {
            obstacle.dispose();
        }
    }
}
