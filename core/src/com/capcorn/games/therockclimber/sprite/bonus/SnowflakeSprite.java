package com.capcorn.games.therockclimber.sprite.bonus;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * User: kprotasov
 * Date: 27.02.2018
 * Time: 20:40
 */

public class SnowflakeSprite extends BonusSprite {

    private static final BonusType BONUS_TYPE = BonusType.SNOWFLAKE;

    public SnowflakeSprite() {
        super();
        setType(BONUS_TYPE);
    }

    public SnowflakeSprite(final TextureRegion textureRegion, final float x, final float y, final float width, final float height) {
        super(textureRegion, x, y, width, height);
        setType(BONUS_TYPE);
    }

    public SnowflakeSprite(final TextureRegion textureRegion, final float x, final float y) {
        super(textureRegion, x, y);
        setType(BONUS_TYPE);
    }

}
