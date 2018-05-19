package com.capcorn.games.therockclimber.sprite.bonus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * User: kprotasov
 * Date: 29.09.2017
 * Time: 16:22
 */

public class RamSprite extends BonusSprite {

    private static final BonusType RAM_BONUS_TYPE = BonusType.GOLD;

    public RamSprite() {
        super();
        setType(RAM_BONUS_TYPE);
    }

    public RamSprite(final TextureRegion textureRegion, final float x, final float y, final float width, final float height) {
        super(textureRegion, x, y, width, height);
        setType(RAM_BONUS_TYPE);
    }

    public RamSprite(final TextureRegion textureRegion, final float x, final float y) {
        super(textureRegion, x, y);
        setType(RAM_BONUS_TYPE);
    }

}
