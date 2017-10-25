package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.g2d.Animation;

/**
 * User: kprotasov
 * Date: 26.06.2017
 * Time: 23:31
 */

public class StoneSprite extends DirectedAnimatedSprite {

    public StoneSprite() {
        super();
    }

    public StoneSprite(Animation animation, float x, float y) {
        super(animation, x, y);
    }

    public StoneSprite(Animation animation, float x, float y, float width, float height) {
        super(animation, x, y, width, height);
    }

}
