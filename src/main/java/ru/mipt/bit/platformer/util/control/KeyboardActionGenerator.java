package ru.mipt.bit.platformer.util.control;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.AbstractMovable;
import ru.mipt.bit.platformer.util.actions.*;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Tank;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.N;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 */
public final class KeyboardActionGenerator implements ActionGenerator {
    @Override
    public ActionCommand getRecommendation(AbstractMovable movable) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            return new MoveUpCommand(movable);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            return new MoveLeftCommand(movable);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            return new MoveDownCommand(movable);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            return new MoveRightCommand(movable);
        }
        if (Gdx.input.isKeyPressed(SPACE) || Gdx.input.isKeyPressed(N)) {
            if (movable instanceof Tank) {
                return new FireCommand((Tank) movable);
            }
        }

        return new NothingCommand(movable);
    }

    @Override
    public void setNewGameState(Level level) {}
}
