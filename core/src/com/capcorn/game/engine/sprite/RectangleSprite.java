package com.capcorn.game.engine.sprite;

import com.badlogic.gdx.graphics.Color;

/**
 * User: kprotasov
 * Date: 19.05.2018
 * Time: 15:55
 */

public class RectangleSprite extends ShapeSprite {

    private final float width;
    private final float height;

    public RectangleSprite(final Color color, final float x, final float y, final float width, final float height) {
        super(color, Type.RECTANGLE, x, y);

        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
