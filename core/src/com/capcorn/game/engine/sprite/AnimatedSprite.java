package com.capcorn.game.engine.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.capcorn.game.engine.animation.TweenAnimation;

/**
 * Created by kprotasov on 10.07.2016.
 */
public class AnimatedSprite implements SpriteBase {

    public enum AnimationState {
        PAUSE,
        PLAY
    }

    private Animation animation;
    private float x;
    private float y;
    private float width;
    private float height;
    private float stateTime;
    private float rotateAngle;
    private AnimationState state = AnimationState.PLAY;
    private OnAnimationFinishListener onAnimationFinishListener;

    private TweenAnimation tween;

    // required
    public AnimatedSprite() {
        this.stateTime = 0;
    }

    public AnimatedSprite(final Animation animation, final float x, final float y) {
        this();
        this.animation = animation;
        this.x = x;
        this.y = y;
    }

    public AnimatedSprite(final Animation animation, final float x, final float y, final float width, final float height){
        this();
        this.animation = animation;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void set(final Animation animation, final float x, final float y, final float width, final float height) {
        this.stateTime = 0;
        this.animation = animation;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setOnAnimationFinishListener(final OnAnimationFinishListener listener) {
        this.onAnimationFinishListener = listener;
    }

    public void addRunTime(final float time) {
        this.stateTime += time;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public void setState(final AnimationState state) {
        this.state = state;
    }

    public TextureRegion getTextureRegion() {
        if (state == AnimationState.PLAY) {
            if (getAnimation().isAnimationFinished(stateTime)) {
                if (onAnimationFinishListener != null) {
                    onAnimationFinishListener.onAnimationFinish();
                }
            }
            return (TextureRegion) getAnimation().getKeyFrame(stateTime);
        } else {
            stateTime = 0;
            return (TextureRegion) getAnimation().getKeyFrame(0);
        }
    }

    public float getX() {
        return x;
    }

    public void setX(final float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(final float y) {
        this.y = y;
    }

    public float getWidth() {
        if (width == 0) {
            if (animation != null && animation.getKeyFrame(stateTime) != null) {
                return ((TextureRegion)animation.getKeyFrame(stateTime)).getRegionWidth();
            } else {
                return 0;
            }
        } else {
            return width;
        }
    }

    public void setWidth(final float width) {
        this.width = width;
    }

    public float getHeight() {
        if (height == 0) {
            return ((TextureRegion)animation.getKeyFrame(stateTime)).getRegionHeight();
        } else {
            return height;
        }
    }

    public float getRotateAngle() {
        return rotateAngle;
    }

    public void setRotateAngle(float rotateAngle) {
        this.rotateAngle = rotateAngle;
    }

    public void setHeight(final float height) {
        this.height = height;
    }

    public void setTweenAnimation(final TweenAnimation tween) {
        this.tween = tween;
    }

    public TweenAnimation getTweenAnimation() {
        return tween;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public Polygon getPolygon() {
        final Rectangle rectangle = getRectangle();
        final float[] vertices = {rectangle.getX(), rectangle.getY(), (rectangle.getX() + rectangle.getWidth()), rectangle.getY(),
                (rectangle.getX() + rectangle.getWidth()), (rectangle.getY() + rectangle.getHeight()), rectangle.getX(), (rectangle.getY() + rectangle.getHeight())};
        return new Polygon(vertices);
    }

    @Override
    public void release() {
        this.stateTime = 0;
        this.state = AnimationState.PLAY;
    }
}
