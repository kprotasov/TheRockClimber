package com.capcorn.game.engine.sprite;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by kprotasov on 17.08.2016.
 */
public class LineSprite extends ShapeSprite {

    private float endX;
    private float endY;
    private float depth;

    public LineSprite(final Color color, final float startX, final float startY, final float endX, final float endY, final float depth) {
        super(color, Type.LINE, startX, startY);
        this.endX = endX;
        this.endY = endY;
        this.depth = depth;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndY() {
        return endY;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public float getDepth() {
        return depth;
    }
}
