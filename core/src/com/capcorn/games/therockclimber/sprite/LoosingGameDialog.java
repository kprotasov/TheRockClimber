package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.capcorn.game.engine.core.RenderLayer;
import com.capcorn.game.engine.sprite.Sprite;
import com.capcorn.game.engine.sprite.TextSprite;
import com.capcorn.games.therockclimber.font.DistanceFontCreator;
import com.capcorn.games.therockclimber.font.FontUtils;
import com.capcorn.games.therockclimber.graphics.AssetsLoader;

/**
 * User: kprotasov
 * Date: 10.09.2017
 * Time: 17:47
 */

public class LoosingGameDialog extends Sprite {

    private static final String INITIAL_TEXT = "";
    private static final String CURRENT_SCORE_TEXT = "You Score: %s";
    private static final String BEST_SCORE_TEXT = "Best Score: %s";

    private static final int TITLE_FONT_SIZE = 30;

    private final BitmapFont dialogFont;
    private final TextSprite currentScoreTextSprite;
    private final TextSprite bestScoreTextSprite;
    private final Sprite leftButtonSprite;
    private final Sprite rightButtonSprite;

    public LoosingGameDialog(final AssetsLoader assetsLoader) {
        super(assetsLoader.getLoosingGameBackgroundTextureRegion(), 0, 0);

        dialogFont = DistanceFontCreator.createFont(TITLE_FONT_SIZE);
        currentScoreTextSprite = new TextSprite(INITIAL_TEXT, dialogFont, 0, 0);
        bestScoreTextSprite = new TextSprite(INITIAL_TEXT, dialogFont, 0, 0);

        leftButtonSprite = new Sprite(assetsLoader.getDialogButton(), 0, 0, 200, 50);
        rightButtonSprite = new Sprite(assetsLoader.getDialogButton(), 0, 0, 200, 50);
    }

    public void initDialog(final int currentScore, final int bestScore) {
        final String formattedCurrentScoreText = String.format(CURRENT_SCORE_TEXT, currentScore);
        final String formattedBestScoreText = String.format(BEST_SCORE_TEXT, bestScore);

        final float dialogCenter = getX() + getWidth() / 2;

        final float currentScoreWidth = FontUtils.getFontWidth(dialogFont, formattedCurrentScoreText);
        final float currentScoreHeight = FontUtils.getFontHeight(dialogFont, formattedCurrentScoreText);
        currentScoreTextSprite.setText(formattedCurrentScoreText);
        currentScoreTextSprite.setX(dialogCenter - currentScoreWidth / 2);
        currentScoreTextSprite.setY(getY() + 30);

        final float bestScoreWidth = FontUtils.getFontWidth(dialogFont, formattedBestScoreText);
        bestScoreTextSprite.setText(formattedBestScoreText);
        bestScoreTextSprite.setX(dialogCenter - bestScoreWidth / 2);
        bestScoreTextSprite.setY(currentScoreTextSprite.getY() + currentScoreHeight + 30);

        leftButtonSprite.setX(getX() + 15);
        leftButtonSprite.setY(getY() + getHeight() - 15 - leftButtonSprite.getHeight());

        rightButtonSprite.setX(getX() + getWidth() - rightButtonSprite.getWidth() - 15);
        rightButtonSprite.setY(getY() + getHeight() - 15 - rightButtonSprite.getHeight());
    }

    public void addOnScreen(final RenderLayer renderLayer, final int layer) {
        renderLayer.addSprite(this, true, layer);

        final int contentLayer = layer + 1;
        renderLayer.addTextSprite(currentScoreTextSprite, contentLayer);
        renderLayer.addTextSprite(bestScoreTextSprite, contentLayer);
        renderLayer.addSprite(leftButtonSprite, true, contentLayer);
        renderLayer.addSprite(rightButtonSprite, true, contentLayer);
    }

    public void removeFromScreen(final RenderLayer renderLayer) {
        renderLayer.removeSprite(this);
        renderLayer.removeSprite(currentScoreTextSprite);
        renderLayer.removeSprite(bestScoreTextSprite);
        renderLayer.removeSprite(leftButtonSprite);
        renderLayer.removeSprite(rightButtonSprite);
    }

}
