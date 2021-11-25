package ru.mipt.bit.platformer.util.control;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.ControlCommand;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.players.Action;
import ru.mipt.bit.platformer.util.players.TankPlayer;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class KeyboardControlCommand implements ControlCommand {
    private final TankPlayer player;

    public KeyboardControlCommand(Level level) {
        this.player = level.getPlayer();
    }

    @Override
    public void execute() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            player.move(Action.MoveNorth);
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            player.move(Action.MoveWest);
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            player.move(Action.MoveSouth);
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            player.move(Action.MoveEast);
        }
        if (Gdx.input.isKeyPressed(SPACE) || Gdx.input.isKeyPressed(N)) {
            player.fire();
        }
    }
}
