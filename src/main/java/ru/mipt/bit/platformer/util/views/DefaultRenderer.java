package ru.mipt.bit.platformer.util.views;

import ru.mipt.bit.platformer.util.RenderableInMoveObject;
import ru.mipt.bit.platformer.util.RenderableObjectWithCoordinates;

import java.util.Map;

/**
 * Adapter в системе портов и адаптеров
 * ApplicationLayer
 */
public class DefaultRenderer implements Renderable<RenderableObjectWithCoordinates> {
    private final Map<String, StaticView> classNameToStaticView;

    private final Map<String, MovableView> classNameToMovableView;

    private RenderableObjectWithCoordinates renderableObject;

    public DefaultRenderer(Map<String, StaticView> classNameToStaticView, Map<String, MovableView> classNameToMovableView) {
        this.classNameToStaticView = classNameToStaticView;
        this.classNameToMovableView = classNameToMovableView;
    }

    public void render() {
        if (renderableObject == null) {
            return;
        }

        if (classNameToStaticView.containsKey(renderableObject.getClass().getName())) {
            classNameToStaticView.get(renderableObject.getClass().getName()).render(renderableObject);
        } else {
            classNameToMovableView
                    .get(renderableObject.getClass().getName())
                    .render((RenderableInMoveObject) renderableObject);
        }
    }

    @Override
    public void setObject(RenderableObjectWithCoordinates object) {
        renderableObject = object;
    }

    @Override
    public RenderableObjectWithCoordinates getObject() {
        return renderableObject;
    }
}
