package com.capcorn.game.engine.sprite;

/**
 * Created by kprotasov on 21.08.2016.
 */
public class SpriteHitTest {

    public static boolean hitTest(final Sprite left, final Sprite right) {
        if (left.getX() > right.getX() && left.getY() > right.getY() &&
                left.getX() < (right.getX() + right.getWidth()) && left.getY() < (right.getY() + right.getHeight())) {
            return true;
        }
        return false;
    }

    public static boolean hitTest(final Sprite left, final AnimatedSprite right) {
        if (left.getX() > right.getX() && left.getY() > right.getY() &&
                left.getX() < (right.getX() + right.getWidth()) && left.getY() < (right.getY() + right.getHeight())) {
            return true;
        }
        return false;
    }

    public static boolean hitTest(final AnimatedSprite left, final Sprite right) {
        return left.getX() < right.getX() + right.getWidth() && left.getX() + left.getWidth() > right.getX() &&
                left.getY() < right.getY() + right.getHeight() && left.getY() + left.getHeight() > right.getY();
    }

    public static boolean hitTest(final AnimatedSprite left, final AnimatedSprite right) {
        return left.getX() < right.getX() + right.getWidth() && left.getX() + left.getWidth() > right.getX() &&
                left.getY() < right.getY() + right.getHeight() && left.getY() + left.getHeight() > right.getY();
    }

}
