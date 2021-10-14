package ru.mipt.bit.platformer.util.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.RenderableObject;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.TankPlayer;

import java.util.Collection;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class LevelView implements Disposable {
    private final Batch batch;

    private final TankPlayerView tankPlayerView;

    private final TreeView treeView;

    private final MapRenderer renderer;

    public LevelView(TiledMap levelMap) {
        batch = new SpriteBatch();

        TiledMapTileLayer groundLayer = getSingleLayer(levelMap);
        tankPlayerView = new TankPlayerView(batch, new Texture("images/tank_blue.png"), groundLayer);
        treeView = new TreeView(batch, new Texture("images/greenTree.png"), groundLayer);

        renderer = createSingleLayerMapRenderer(levelMap, batch);
    }

    public void render(Level level) {
        renderer.render();

        // start recording all drawing commands
        batch.begin();

        Collection<? extends RenderableObject> renderableObjects = level.getRenderableObjects();
        for (RenderableObject renderableObject : renderableObjects) {
            if (renderableObject instanceof TankPlayer) {
                tankPlayerView.render((TankPlayer) renderableObject);
            } else if (renderableObject instanceof Tree) {
                treeView.render((Tree) renderableObject);
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
