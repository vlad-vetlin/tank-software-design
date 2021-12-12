package ru.mipt.bit.platformer.util.control.AILibrarySupport;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.state.movable.Bot;
import ru.mipt.bit.platformer.util.players.Tank;

/**
 * Адаптер в системе портов и адаптеров
 * Application layer
 */
public class BotAdapter {
    private final Bot bot;

    public BotAdapter(Tank tankPlayer) {
        Bot.BotBuilder builder = Bot.builder();
        builder.source(tankPlayer);

        GridPoint2 position = tankPlayer.getCoordinates();
        builder.x(position.x).y(position.y);

        GridPoint2 destination = tankPlayer.getDestinationCoordinates();
        builder.destX(destination.x).destY(destination.y);

        builder.orientation(new OrientationAdapter(tankPlayer.getRotation()).getOrientation());

        bot = builder.build();
    }

    public Bot getBot() {
        return bot;
    }
}
