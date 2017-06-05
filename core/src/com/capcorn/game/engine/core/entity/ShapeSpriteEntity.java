package com.capcorn.game.engine.core.entity;

import com.capcorn.game.engine.sprite.ShapeSprite;

/**
 * Created by kprotasov on 05.11.2016.
 */

public class ShapeSpriteEntity extends BaseEntity {

    private ShapeSprite sprite;

    public ShapeSpriteEntity(final ShapeSprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public ShapeSprite getSprite() {
        return sprite;
    }

    public void setSprite(final ShapeSprite sprite) {
        this.sprite = sprite;
    }
}
