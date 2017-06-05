package com.capcorn.game.engine.sprite;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.capcorn.game.engine.animation.TweenAnimation;
import com.capcorn.game.engine.sprite.entity.PolygonSpriteEntity;

/**
 * Created by user on 12.08.2016.
 */
public class AnimatedPolygonSprite implements SpriteBase {

    private TweenAnimation tweenAnimation;
    private PolygonSprite sprite;
    private PolygonSpriteEntity polygonSpriteEntity;

    public AnimatedPolygonSprite(final PolygonSprite sprite) {
        this.sprite = sprite;
    }

    public AnimatedPolygonSprite(final PolygonSpriteEntity entity) {
        this.polygonSpriteEntity = entity;
    }

    public TweenAnimation getTweenAnimation() {
        return tweenAnimation;
    }

    public void setTweenAnimation(final TweenAnimation tweenAnimation) {
        this.tweenAnimation = tweenAnimation;
    }

    public void setSprite(final PolygonSprite sprite) {
        this.sprite = sprite;
    }

    public PolygonSprite getSprite() {
        if (polygonSpriteEntity != null) {
            setSprite(polygonSpriteEntity.getPolygonSprite());
        }
        return sprite;
    }

    public void setPolygonSpriteEntity(final PolygonSpriteEntity entity) {
        this.polygonSpriteEntity = entity;
    }

    public PolygonSpriteEntity getPolygonSpriteEntity() {
        return polygonSpriteEntity;
    }

    public void release() {

    }

}
