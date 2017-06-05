package com.capcorn.game.engine.core.entity;

import com.capcorn.game.engine.sprite.Sprite;

/**
 * Created by kprotasov on 05.11.2016.
 */

public class SpriteEntity extends BaseEntity {

    private Sprite sprite;

    public SpriteEntity(final Sprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
