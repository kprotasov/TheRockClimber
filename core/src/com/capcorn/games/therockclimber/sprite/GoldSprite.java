package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.capcorn.game.engine.sprite.Sprite;

/**
 * User: kprotasov
 * Date: 15.07.2017
 * Time: 17:54
 */

public class GoldSprite extends Sprite {

    public GoldSprite() {
        super();
    }

    public GoldSprite(TextureRegion textureRegion, float x, float y, float width, float height) {
        super(textureRegion, x, y, width, height);
    }

    public GoldSprite(TextureRegion textureRegion, float x, float y) {
        super(textureRegion, x, y);
    }

    public BonusType getType() {
        return BonusType.GOLD;
    }

}
