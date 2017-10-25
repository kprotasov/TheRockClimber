package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.capcorn.game.engine.sprite.Sprite;

/**
 * User: kprotasov
 * Date: 15.07.2017
 * Time: 17:54
 */

public class BonusSprite extends Sprite {

    private BonusType type;

    public BonusSprite() {
        super();
    }

    public BonusSprite(final TextureRegion textureRegion, final float x, final float y, final float width, final float height) {
        super(textureRegion, x, y, width, height);
    }

    public BonusSprite(final TextureRegion textureRegion, final float x, final float y) {
        super(textureRegion, x, y);
    }

    public void setType(final BonusType type) {
        this.type = type;
    }

    public BonusType getType() {
        return type;
    }

    public int getSelfCoast() {
        return 0;
    }

}
