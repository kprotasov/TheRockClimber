package com.capcorn.game.engine.core.entity;

import com.capcorn.game.engine.sprite.TextSprite;

/**
 * Created by kprotasov on 04.12.2016.
 */

public class TextEntity extends BaseEntity {

    private TextSprite sprite;

    public TextEntity(final TextSprite sprite) {
        this.sprite = sprite;
    }

    @Override
    public TextSprite getSprite() {
        return sprite;
    }

    public void setSprite(final TextSprite sprite) {
        this.sprite = sprite;
    }
}
