package com.capcorn.game.engine.core.entity;


import com.capcorn.game.engine.sprite.AnimatedSprite;

/**
 * Created by kprotasov on 06.11.2016.
 */

public class AnimatedSpriteEntity extends BaseEntity {

    private AnimatedSprite sprite;

    public AnimatedSpriteEntity(final AnimatedSprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public AnimatedSprite getSprite() {
        return sprite;
    }

    public void setSprite(final AnimatedSprite sprite) {
        this.sprite = sprite;
    }
}
