package ru.mipt.bit.platformer.util.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;
import ru.mipt.bit.platformer.util.RenderableInMoveObject;
import ru.mipt.bit.platformer.util.RenderableObjectWithCoordinates;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class StaticView implements Disposable, View<RenderableObjectWithCoordinates> {
    protected Batch batch;

    protected Texture texture;

    protected TextureRegion graphics;

    private final TiledMapTileLayer groundLayer;

    private final Rectangle rectangle;

    protected StaticView(Batch batch, Texture texture, TiledMapTileLayer groundLayer) {
        this.batch = batch;
        this.texture = texture;
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
        this.groundLayer = groundLayer;
    }

    public void render(RenderableObjectWithCoordinates tree) {
        moveRectangleAtTileCenter(groundLayer, rectangle, tree.getCoordinates());
        drawTextureRegionUnscaled(batch, graphics, rectangle, tree.getRotation());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
