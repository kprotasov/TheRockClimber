package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * User: kprotasov
 * Date: 15.07.2017
 * Time: 17:54
 */

public class GoldSprite extends BonusSprite {

    private static final BonusType GOLD_BONUS_TYPE = BonusType.GOLD;

    public GoldSprite() {
        super();
        setType(GOLD_BONUS_TYPE);
    }

    public GoldSprite(final TextureRegion textureRegion, final float x, final float y, final float width, final float height) {
        super(textureRegion, x, y, width, height);
        setType(GOLD_BONUS_TYPE);
    }

    public GoldSprite(final TextureRegion textureRegion, final float x, final float y) {
        super(textureRegion, x, y);
        setType(GOLD_BONUS_TYPE);
    }

}
