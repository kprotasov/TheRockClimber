package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.capcorn.game.engine.sprite.AnimatedSprite;
import com.capcorn.games.therockclimber.graphics.AssetsLoader;

/**
 * User: kprotasov
 * Date: 26.06.2017
 * Time: 23:31
 */

public class CharacterSprite extends DirectedAnimatedSprite {

    public CharacterSprite() {
        super();
    }

    public CharacterSprite(Animation animation, float x, float y) {
        super(animation, x, y);
    }

    public CharacterSprite(Animation animation, float x, float y, float width, float height) {
        super(animation, x, y, width, height);
    }

}
