package ru.mipt.bit.platformer.util.control;

import ru.mipt.bit.platformer.util.Movable;

import java.util.Random;

public class AIControl implements AbstractControl {
    private static final Random random = new Random();

    @Override
    public void processMovement(Movable movable) {
        int direction = random.nextInt(4);

        switch (direction) {
            case 0:
                movable.moveUp();
                return;
            case 1:
                movable.moveRight();
                return;
            case 2:
                movable.moveDown();
                return;
            case 3:
                movable.moveLeft();
        }
    }
}
