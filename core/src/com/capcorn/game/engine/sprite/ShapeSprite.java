package com.capcorn.game.engine.sprite;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by kprotasov on 04.05.2016.
 */
public class ShapeSprite implements SpriteBase {

    public enum Type{
        CIRCLE,
        LINE;
    }

    private Color color;
    private final Type type;
    private float x;
    private float y;

    public ShapeSprite(final Color color, final Type type, final float x, final float y) {
        this.color = color;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public void setColor(final Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public Type getType() {
        return type;
    }

    public float getX(){
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY(){
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void release() {

    }
}
