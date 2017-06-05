package com.capcorn.game.engine.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.capcorn.game.engine.animation.TweenAnimation;

/**
 * Created by kprotasov on 26.04.2016.
 */
public class Sprite implements SpriteBase {

    private TextureRegion textureRegion;
    private float x;
    private float y;
    private float width;
    private float height;
    private TweenAnimation tweenAnimation;

    public Sprite() {

    }

    public Sprite(final TextureRegion textureRegion, final float x, final float y, final float width, final float height) {
        this.textureRegion = textureRegion;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Sprite(final TextureRegion textureRegion, final float x, final float y) {
        this(textureRegion, x, y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    public TextureRegion getTexture(){
        return textureRegion;
    }

    public void setTextureRegion(final TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public float getX(){
        return x;
    }

    public void setX(final float x) {
        this.x = x;
    }

    public float getY(){
        return y;
    }

    public void setY(final float y) {
        this.y = y;
    }

    public float getWidth(){
        return width;
    }

    public void setWidth(final float width) {
        this.width = width;
    }

    public void setHeight(final float height) {
        this.height = height;
    }

    public float getHeight(){
        return height;
    }

    public void setTweenAnimation(final TweenAnimation animation) {
        this.tweenAnimation = animation;
    }

    public TweenAnimation getTweenAnimation() {
        return tweenAnimation;
    }

    @Override
    public void release() {

    }
}
