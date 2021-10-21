package ru.mipt.bit.platformer.util.control;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.util.Movable;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.Input.Keys.D;

public class KeyboardControl implements AbstractControl {
    @Override
    public void processMovement(Movable movable) {
        if (Gdx.input.isKeyPressed(UP) || Gdx.input.isKeyPressed(W)) {
            movable.moveUp();
        }
        if (Gdx.input.isKeyPressed(LEFT) || Gdx.input.isKeyPressed(A)) {
            movable.moveLeft();
        }
        if (Gdx.input.isKeyPressed(DOWN) || Gdx.input.isKeyPressed(S)) {
            movable.moveDown();
        }
        if (Gdx.input.isKeyPressed(RIGHT) || Gdx.input.isKeyPressed(D)) {
            movable.moveRight();
        }
    }
}
