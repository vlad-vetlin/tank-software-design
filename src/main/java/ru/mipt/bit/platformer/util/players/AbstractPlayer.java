package ru.mipt.bit.platformer.util.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.ICanMove;
import ru.mipt.bit.platformer.util.ObjectWithCoordinates;

public abstract class AbstractPlayer extends ObjectWithCoordinates implements ICanMove {
    public AbstractPlayer(Batch batch, Texture texture, GridPoint2 coordinates) {
        super(batch, texture, coordinates);
    }
}
