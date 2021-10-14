package ru.mipt.bit.platformer;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.levels.Level;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class GameKeyboardSupporter {
    private final Level level;

    public GameKeyboardSupporter(Level level) {
        this.level = level;
    }

    private void processMovement() {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            level.getPlayer().moveUp();
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            level.getPlayer().moveLeft();
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            level.getPlayer().moveDown();
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            level.getPlayer().moveRight();
        }
    }

    public void processKey() {
        processMovement();
    }
}
