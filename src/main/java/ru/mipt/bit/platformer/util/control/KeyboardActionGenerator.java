package ru.mipt.bit.platformer.util.control;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Action;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.N;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 */
public final class KeyboardActionGenerator implements ActionGenerator {
    @Override
    public Action getRecommendation(AbstractMovable movable) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return Action.MoveNorth;
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return Action.MoveWest;
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return Action.MoveSouth;
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return Action.MoveEast;
        }
        if (Gdx.input.isKeyPressed(SPACE) || Gdx.input.isKeyPressed(N)) {
            return Action.Shoot;
        }

        return Action.Nothing;
    }

    @Override
    public void setNewGameState(Level level) {}
}
