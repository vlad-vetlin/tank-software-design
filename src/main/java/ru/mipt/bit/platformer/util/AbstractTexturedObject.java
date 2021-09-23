package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public abstract class AbstractTexturedObject implements ICanBeRendered, Disposable {
    protected Texture texture;
    protected Batch batch;
    protected Rectangle rectangle;
    protected TextureRegion graphics;

    public AbstractTexturedObject(Batch batch, Texture texture) {
        this.batch = batch;
        this.texture = texture;
        graphics = new TextureRegion(texture);
        rectangle = createBoundingRectangle(graphics);
    }

    public void render() {
        // 0f - default rotation. If class have rotation, then it should Override this method
        drawTextureRegionUnscaled(batch, graphics, rectangle, 0f);
    }

    public void dispose() {
        texture.dispose();
    }
}
