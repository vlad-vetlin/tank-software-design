package ru.mipt.bit.platformer.util.views.decorators;

import ru.mipt.bit.platformer.util.RenderableObjectWithCoordinates;
import ru.mipt.bit.platformer.util.players.Tank;
import ru.mipt.bit.platformer.util.views.Renderable;
import ru.mipt.bit.platformer.util.views.commands.ShowLivesCommand;

/**
 * Port в системе портов и адаптеров
 * ApplicationLayer
 */
public class RenderWithLivesDecorator implements Renderable<RenderableObjectWithCoordinates> {
    private final Renderable<RenderableObjectWithCoordinates> baseRenderer;

    public RenderWithLivesDecorator(Renderable<RenderableObjectWithCoordinates> baseRenderer) {
        this.baseRenderer = baseRenderer;
    }

    @Override
    public void render() {
        if (getObject() instanceof Tank) {
            new ShowLivesCommand((Tank) getObject()).execute();
        }

        baseRenderer.render();
    }

    @Override
    public void setObject(RenderableObjectWithCoordinates object) {
        baseRenderer.setObject(object);
    }

    @Override
    public RenderableObjectWithCoordinates getObject() {
        return baseRenderer.getObject();
    }
}
