package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.generators.LevelGenerator;
import ru.mipt.bit.platformer.util.generators.SimpleRandomGenerator;
import ru.mipt.bit.platformer.util.levels.Level;
import ru.mipt.bit.platformer.util.views.LevelGraphicProcessor;
import ru.mipt.bit.platformer.util.views.LevelGraphics;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 */
public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Level level;

    private LevelGraphics levelView;

    private LevelGraphicProcessor currentLevelGraphics;


    @Override
    public void create() {
        TiledMap levelMap = new TmxMapLoader().load("level.tmx");

        levelView = new LevelGraphics(levelMap);

        LevelGenerator generator = new SimpleRandomGenerator(
                new GridPoint2(levelView.getWidth(), levelView.getHeight()),
                10,
                10
        );

//        LevelGenerator generator = new FileGenerator("src/main/resources/levels/testLevel");

        level = generator.createLevel();
        Settings.setLevelToMoveStrategies(level);
        currentLevelGraphics = new LevelGraphicProcessor(levelView, level);
    }

    private void clearScreen() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);
    }


    @Override
    public void render() {
        clearScreen();

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        level.processOneTick(deltaTime, MOVEMENT_SPEED);

        // Processing of new commands should be after processing tick. Because we need to check collisions before
        // calling new movements
        Settings.updateActionGenerators(level);
        level.processActions();

        currentLevelGraphics.render();
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
