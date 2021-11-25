package ru.mipt.bit.platformer.util.views.commands;

import ru.mipt.bit.platformer.util.players.TankPlayer;

public class ShowLivesCommand implements ViewCommand {
    private TankPlayer player;

    public ShowLivesCommand(TankPlayer tankPlayer) {
        player = tankPlayer;
    }

    @Override
    public void execute() {
        System.out.println("show lives for tank: " + player.getId().toString() + " " + player.getLives());
    }
}
