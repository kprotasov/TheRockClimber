package com.capcorn.game.engine.utils;

/**
 * Created by kprotasov on 04.05.2016.
 */
public class XYPair {

    private float x;
    private float y;

    public XYPair(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public String toString() {
        return "x: " + x + " y: " + y;
    }
}
