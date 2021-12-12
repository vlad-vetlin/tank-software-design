package ru.mipt.bit.platformer.util.control.AILibrarySupport;

import ru.mipt.bit.platformer.util.players.Action;

/**
 * Адаптер в системе портов и адаптеров
 * Application layer
 */
public class ActionAdapter {
    private final Action action;

    public ActionAdapter(org.awesome.ai.Action action) {
        switch (action) {
            case MoveNorth:
                this.action = Action.MoveNorth;
                break;
            case MoveEast:
                this.action = Action.MoveEast;
                break;
            case MoveSouth:
                this.action = Action.MoveSouth;
                break;
            case MoveWest:
                this.action = Action.MoveWest;
                break;
            case Shoot:
            default:
                this.action = Action.Shoot;
        }
    }

    public Action getAction() {
        return action;
    }
}
