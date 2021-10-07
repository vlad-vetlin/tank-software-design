package ru.mipt.bit.platformer.util.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.RenderableObject;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class ObjectWithTextureView implements Disposable {
    protected Batch batch;

    protected Texture texture;

    protected TextureRegion graphics;

    protected ObjectWithTextureView(Batch batch, Texture texture) {
        this.batch = batch;
        this.texture = texture;
        graphics = new TextureRegion(texture);
    }

    public void render(RenderableObject renderableObject) {
        drawTextureRegionUnscaled(batch, graphics, renderableObject.getCoordsAsRect(), renderableObject.getRotation());
    }

    public Rectangle getRectangle() {
        return createBoundingRectangle(graphics);
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }
}
