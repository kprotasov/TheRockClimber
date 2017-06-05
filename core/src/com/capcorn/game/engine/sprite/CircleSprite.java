package com.capcorn.game.engine.sprite;

import com.badlogic.gdx.graphics.Color;
import com.capcorn.game.engine.utils.XYPair;

/**
 * Created by kprotasov on 04.05.2016.
 */
public class CircleSprite extends ShapeSprite {

    private float radius;

    public CircleSprite(final Color color, final float x, final float y, final float radius) {
        super(color, Type.CIRCLE, x, y);

        this.radius = radius;
    }

    public CircleSprite(final Color color, final XYPair centerXYPair, final float radius) {
        super(color, Type.CIRCLE, centerXYPair.getX(), centerXYPair.getY());

        this.radius = radius;
    }

    public float getRadius(){
        return radius;
    }

    public void setRadius(final float radius) {
        this.radius = radius;
    }

    public XYPair calculateXYByCenter(final float centerX, final float centerY){
        final float halfRadius = radius / 2;
        return new XYPair(centerX - halfRadius, centerY - halfRadius);
    }

}
