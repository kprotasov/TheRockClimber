package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.capcorn.game.engine.sprite.TextSprite;
import com.capcorn.games.therockclimber.font.DistanceFontCreator;
import com.capcorn.games.therockclimber.settings.Settings;

/**
 * User: kprotasov
 * Date: 10.10.2017
 * Time: 20:27
 */

public class ClickToStartTextSprite extends TextSprite {

    private static final int FONT_SIZE = 50;

    private BitmapFont font;

    public ClickToStartTextSprite(String text, final Settings.ScreenSize screenSize) {
        super(text, null, 0, 0);
        font = DistanceFontCreator.createFont(FONT_SIZE, false);
        this.setFont(font);

        // calculate x and y
        final float xPosition = screenSize.WIDTH / 2 - getWidth() / 2;
        final float yPosition = screenSize.HEIGHT / 2 - getHeight() / 2;
        setX(xPosition);
        setY(yPosition);
    }
}
