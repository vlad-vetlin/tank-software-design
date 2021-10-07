package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.views.LevelView;

import java.util.List;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Level level;

    private LevelView levelView;

    private GameKeyboardSupporter keyboardSupporter;

    @Override
    public void create() {
        TiledMap levelMap = new TmxMapLoader().load("level.tmx");

        levelView = new LevelView(levelMap);
        level = new Level(levelMap);
        level.addPlayer(levelView.getPlayerRect(), new GridPoint2(1, 1));
        level.addObstacles(levelView.getObstacleRect(), List.of(
            new GridPoint2(1, 3),
            new GridPoint2(2, 5)
        ));

        keyboardSupporter = new GameKeyboardSupporter(level);
    }


    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        keyboardSupporter.processKey();

        level.processMoveToDestination(deltaTime, MOVEMENT_SPEED);

        // render each tile of the level
        levelView.render(level);
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        levelView.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
