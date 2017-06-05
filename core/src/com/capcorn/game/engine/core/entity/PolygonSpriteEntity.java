package com.capcorn.game.engine.core.entity;

import com.capcorn.game.engine.sprite.AnimatedPolygonSprite;

/**
 * Created by kprotasov on 05.11.2016.
 */

public class PolygonSpriteEntity extends BaseEntity {

    private AnimatedPolygonSprite polygonSprite;

    public PolygonSpriteEntity(final AnimatedPolygonSprite polygonSprite) {
        this.polygonSprite = polygonSprite;
    }

    @Override
    public AnimatedPolygonSprite getSprite() {
        return polygonSprite;
    }

    public void setSprite(final AnimatedPolygonSprite polygonSprite) {
        this.polygonSprite = polygonSprite;
    }
}
