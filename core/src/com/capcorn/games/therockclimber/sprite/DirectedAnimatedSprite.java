package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.capcorn.game.engine.sprite.AnimatedSprite;

/**
 * User: kprotasov
 * Date: 23.10.2017
 * Time: 17:41
 */

public class DirectedAnimatedSprite extends AnimatedSprite {

    DirectedAnimatedSprite() {
        super();
    }

    DirectedAnimatedSprite(Animation animation, float x, float y) {
        super(animation, x, y);
    }

    DirectedAnimatedSprite(Animation animation, float x, float y, float width, float height) {
        super(animation, x, y, width, height);
    }

    private FlipDirection flipDirection = FlipDirection.LEFT;
    private Animation leftAnimation;
    private Animation rightAnimation;

    public enum FlipDirection {
        LEFT,
        RIGHT;
    }

    public void setRightAnimation(Animation rightAnimation) {
        this.rightAnimation = rightAnimation;
    }

    public void setLeftAnimation(Animation leftAnimation) {
        this.leftAnimation = leftAnimation;
    }

    public void changeAnimation(final FlipDirection direction) {
        switch (direction) {
            case LEFT:
                setAnimation(leftAnimation);
                flipDirection = FlipDirection.LEFT;
                break;
            case RIGHT:
                setAnimation(rightAnimation);
                flipDirection = FlipDirection.RIGHT;
                break;
        }
    }

    public FlipDirection getFlipDirection() {
        return flipDirection;
    }

}
