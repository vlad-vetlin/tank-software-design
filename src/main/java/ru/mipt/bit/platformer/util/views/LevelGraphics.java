package ru.mipt.bit.platformer.util.views;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.utils.Disposable;
import ru.mipt.bit.platformer.util.RenderableObjectWithCoordinates;
import ru.mipt.bit.platformer.util.obstacles.Bullet;
import ru.mipt.bit.platformer.util.obstacles.Tree;
import ru.mipt.bit.platformer.util.players.TankPlayer;
import ru.mipt.bit.platformer.util.views.decorators.RenderWithLivesDecorator;

import java.util.*;
import java.util.function.Function;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class LevelGraphics implements Disposable {
    private final Batch batch;

    private final MovableView tankPlayerView;

    private final StaticView treeView;

    private final MovableView bulletView;

    private final MapRenderer renderer;

    private final TiledMapTileLayer groundLayer;

    private final Renderable<RenderableObjectWithCoordinates> objectRenderer;

    private final Map<
            String, Function<Renderable<RenderableObjectWithCoordinates>, Renderable<RenderableObjectWithCoordinates>>
    > turnedDecorators = new HashMap<>();

    public void turnOnLives() {
        turnedDecorators.put(RenderWithLivesDecorator.class.getName(), RenderWithLivesDecorator::new);
    }

    public void turnOffLives() {
        turnedDecorators.remove(RenderWithLivesDecorator.class.getName());
    }

    public Renderable<RenderableObjectWithCoordinates> decorateAll(
            Renderable<RenderableObjectWithCoordinates> renderer
    ) {
        for (var decorator : turnedDecorators.values()) {
            renderer = decorator.apply(renderer);
        }

        return renderer;
    }

    public LevelGraphics(TiledMap levelMap) {
        batch = new SpriteBatch();

        groundLayer = getSingleLayer(levelMap);
        tankPlayerView = new MovableView(batch, new Texture("images/tank_blue.png"), groundLayer);
        treeView = new StaticView(batch, new Texture("images/greenTree.png"), groundLayer);
        bulletView = new MovableView(batch, new Texture("images/bullet2.png"), groundLayer);

        renderer = createSingleLayerMapRenderer(levelMap, batch);

        objectRenderer = new DefaultRenderer(Map.of(
                Tree.class.getName(), treeView
        ), Map.of(
                TankPlayer.class.getName(), tankPlayerView,
                Bullet.class.getName(), bulletView
        ));
    }

    public int getWidth() {
        return groundLayer.getWidth();
    }

    public int getHeight() {
        return groundLayer.getHeight();
    }

    public void render(Collection<RenderableObjectWithCoordinates> renderableObjects) {
        renderer.render();

        // start recording all drawing commands
        batch.begin();

        for (RenderableObjectWithCoordinates renderableObject : renderableObjects) {
            objectRenderer.setObject(renderableObject);
            decorateAll(objectRenderer).render();
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
