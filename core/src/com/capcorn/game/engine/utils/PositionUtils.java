package com.capcorn.game.engine.utils;

import com.capcorn.game.engine.sprite.AnimatedSprite;
import com.capcorn.game.engine.sprite.Sprite;

/**
 * Created by kprotasov on 12.02.2017.
 */

public class PositionUtils {

    public static float getXFromParentCenter(final Sprite sprite, final Sprite parent) {
        return parent.getX() + parent.getWidth() * 0.5f - sprite.getWidth() * 0.5f;
    }

    public static float getYFromParentCenter(final Sprite sprite, final Sprite parent) {
        return parent.getY() + parent.getHeight() * 0.5f - sprite.getHeight() * 0.5f;
    }

    public static float getXFromParentCenter(final Sprite sprite, final AnimatedSprite parent) {
        return parent.getX() + parent.getWidth() * 0.5f - sprite.getWidth() * 0.5f;
    }

    public static float getYFromParentCenter(final Sprite sprite, final AnimatedSprite parent) {
        return parent.getY() + parent.getHeight() * 0.5f - sprite.getHeight() * 0.5f;
    }

}
