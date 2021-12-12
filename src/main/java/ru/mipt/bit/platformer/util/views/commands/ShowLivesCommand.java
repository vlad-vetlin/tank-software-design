package ru.mipt.bit.platformer.util.views.commands;

import ru.mipt.bit.platformer.util.players.Tank;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 */
public class ShowLivesCommand implements ViewCommand {
    private final Tank player;

    public ShowLivesCommand(Tank tankPlayer) {
        player = tankPlayer;
    }

    @Override
    public void execute() {
        System.out.println("show lives for tank: " + player.getId().toString() + " " + player.getLives());
    }
}
