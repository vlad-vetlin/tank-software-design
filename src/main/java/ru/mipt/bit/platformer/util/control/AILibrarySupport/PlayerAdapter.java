package ru.mipt.bit.platformer.util.control.AILibrarySupport;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.util.players.Tank;

/**
 * Adapter в системе портов и адаптеров
 * Application layer
 */
public class PlayerAdapter {
    private final Player player;

    public PlayerAdapter(Tank tankPlayer) {
        Player.PlayerBuilder builder = Player.builder();
        builder.source(tankPlayer);

        GridPoint2 position = tankPlayer.getCoordinates();
        builder.x(position.x).y(position.y);

        GridPoint2 destination = tankPlayer.getDestinationCoordinates();
        builder.destX(destination.x).destY(destination.y);

        builder.orientation(new OrientationAdapter(tankPlayer.getRotation()).getOrientation());

        player = builder.build();
    }

    public Player getPlayer() {
        return player;
    }
}
