package ru.mipt.bit.platformer.util.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.RenderableInMoveObject;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 */
public class MovableView implements Disposable, View<RenderableInMoveObject> {
    private final Batch batch;

    private final TextureRegion graphics;

    private final TileMovement tileMovement;

    private final Texture texture;

    private final Rectangle rectangle;

    public MovableView(Batch batch, Texture texture, TiledMapTileLayer groundLayer) {
        this.batch = batch;
        this.texture = texture;
        graphics = new TextureRegion(texture);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        rectangle = createBoundingRectangle(graphics);
    }

    public void render(RenderableInMoveObject renderableObject) {
        tileMovement.moveRectangleBetweenTileCenters(
                rectangle,
                renderableObject.getCoordinates(),
                renderableObject.getDestinationCoordinates(),
                renderableObject.getMovementProgress()
        );
        drawTextureRegionUnscaled(batch, graphics, rectangle, renderableObject.getRotation());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
