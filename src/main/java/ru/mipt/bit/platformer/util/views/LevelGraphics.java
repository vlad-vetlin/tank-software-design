package ru.mipt.bit.platformer.util.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.ObjectEventManager;
import ru.mipt.bit.platformer.util.AbstractObjectWithCoordinates;
import ru.mipt.bit.platformer.util.RenderableInMoveObject;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.TankPlayer;

import java.util.*;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class LevelGraphics implements Disposable {
    private final Batch batch;

    private final MovableView tankPlayerView;

    private final StaticView treeView;

    private final MovableView bulletView;

    private final MapRenderer renderer;

    private final TiledMapTileLayer groundLayer;

    private final Map<String, StaticView> classNameToStaticView;

    private final Map<String, MovableView> classNameToMovableView;

    public LevelGraphics(TiledMap levelMap) {
        batch = new SpriteBatch();

        groundLayer = getSingleLayer(levelMap);
        tankPlayerView = new MovableView(batch, new Texture("images/tank_blue.png"), groundLayer);
        treeView = new StaticView(batch, new Texture("images/greenTree.png"), groundLayer);
        bulletView = new MovableView(batch, new Texture("images/bullet2.png"), groundLayer);

        renderer = createSingleLayerMapRenderer(levelMap, batch);

        classNameToStaticView = Map.of(
                Tree.class.getName(), treeView
        );
        classNameToMovableView = Map.of(
                TankPlayer.class.getName(), tankPlayerView,
                Bullet.class.getName(), bulletView
        );
    }

    public int getWidth() {
        return groundLayer.getWidth();
    }

    public int getHeight() {
        return groundLayer.getHeight();
    }

    public void render(Collection<AbstractObjectWithCoordinates> renderableObjects) {
        renderer.render();

        // start recording all drawing commands
        batch.begin();

        for (AbstractObjectWithCoordinates renderableObject : renderableObjects) {
            if (classNameToStaticView.containsKey(renderableObject.getClass().getName())) {
                classNameToStaticView.get(renderableObject.getClass().getName()).render(renderableObject);
                continue;
            }

            classNameToMovableView
                    .get(renderableObject.getClass().getName())
                    .render((RenderableInMoveObject) renderableObject);
        }

        // submit all drawing requests
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        tankPlayerView.dispose();
        treeView.dispose();
        bulletView.dispose();
    }
}
