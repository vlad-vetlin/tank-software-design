package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.players.Action;
import ru.mipt.bit.platformer.util.views.LevelGraphics;

import static com.badlogic.gdx.Input.Keys.*;

public class LevelKeyboardControlCommand implements ControlCommand {
    LevelGraphics levelGraphics;

    public LevelKeyboardControlCommand(LevelGraphics graphics) {
        levelGraphics = graphics;
    }

    @Override
    public void execute() {
        if (Gdx.input.isKeyPressed(L)) {
            levelGraphics.turnOnLives();
            return;
        }

        levelGraphics.turnOffLives();
    }
}
