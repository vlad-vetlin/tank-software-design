package ru.mipt.bit.platformer.util.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.RenderableObject;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.TankPlayer;

import java.util.Collection;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;

public class LevelView implements Disposable {
    private final Batch batch;

    private final ObjectWithTextureView tankPlayerView;

    private final ObjectWithTextureView treeView;

    private final MapRenderer renderer;

    public LevelView(TiledMap levelMap) {
        batch = new SpriteBatch();

        tankPlayerView = new ObjectWithTextureView(batch, new Texture("images/tank_blue.png"));
        treeView = new ObjectWithTextureView(batch, new Texture("images/greenTree.png"));

        renderer = createSingleLayerMapRenderer(levelMap, batch);
    }

    public Rectangle getObstacleRect() {
        return treeView.getRectangle();
    }

    public Rectangle getPlayerRect() {
        return tankPlayerView.getRectangle();
    }

    public void render(Level level) {
        renderer.render();

        // start recording all drawing commands
        batch.begin();

        Collection<? extends RenderableObject> renderableObjects = level.getRenderableObjects();
        for (RenderableObject renderableObject : renderableObjects) {
            if (renderableObject instanceof TankPlayer) {
                tankPlayerView.render(renderableObject);
            } else if (renderableObject instanceof Tree) {
                treeView.render(renderableObject);
            }
        }

        // submit all drawing requests
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        tankPlayerView.dispose();
        treeView.dispose();
    }
}
