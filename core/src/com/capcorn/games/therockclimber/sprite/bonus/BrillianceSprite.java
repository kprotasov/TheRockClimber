package com.capcorn.games.therockclimber.sprite.bonus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * User: kprotasov
 * Date: 13.10.2017
 * Time: 23:18
 */

public class BrillianceSprite extends BonusSprite{

    private static final BonusType BONUS_TYPE = BonusType.BRILLIANCE;
    private static final int SELF_COAST = 10;

    public BrillianceSprite() {
        super();
        setType(BONUS_TYPE);
    }

    public BrillianceSprite(final TextureRegion textureRegion, final float x, final float y, final float width, final float height) {
        super(textureRegion, x, y, width, height);
        setType(BONUS_TYPE);
    }

    public BrillianceSprite(final TextureRegion textureRegion, final float x, final float y) {
        super(textureRegion, x, y);
        setType(BONUS_TYPE);
    }

    public int getSelfCoast() {
        return SELF_COAST;
    }

}
