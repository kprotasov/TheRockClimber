package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.Gdx;
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
    private static final String LEFT_BUTTON_TEXT = "Restart";
    private static final String RIGHT_BUTTON_TEXT = "Continue";

    private static final int TITLE_FONT_SIZE = 30;
    private static final int BUTTON_CLICKABLE_ZONE = 30;

    private final BitmapFont dialogFont;
    private final TextSprite currentScoreTextSprite;
    private final TextSprite bestScoreTextSprite;
    private final TextSprite leftButtonTextSprite;
    private final TextSprite rightButtonTextSprite;

    private boolean isShown = false;
    private OnLoosingDialogActionListener onLoosingDialogActionListener;

    public LoosingGameDialog(final AssetsLoader assetsLoader, OnLoosingDialogActionListener listener) {
        super(assetsLoader.getLoosingGameBackgroundTextureRegion(), 0, 0);

        this.onLoosingDialogActionListener = listener;

        dialogFont = DistanceFontCreator.createFont(TITLE_FONT_SIZE, false);
        currentScoreTextSprite = new TextSprite(INITIAL_TEXT, dialogFont, 0, 0);
        bestScoreTextSprite = new TextSprite(INITIAL_TEXT, dialogFont, 0, 0);
        leftButtonTextSprite = new TextSprite(LEFT_BUTTON_TEXT, dialogFont, 0, 0);
        rightButtonTextSprite = new TextSprite(RIGHT_BUTTON_TEXT, dialogFont, 0, 0);
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

        final float leftTextWidth = FontUtils.getFontWidth(dialogFont, LEFT_BUTTON_TEXT);
        final float leftTextHeight = FontUtils.getFontHeight(dialogFont, LEFT_BUTTON_TEXT);
        leftButtonTextSprite.setX(getX() + getWidth() / 4 - leftTextWidth / 2);
        leftButtonTextSprite.setY(getY() + getHeight() - leftTextHeight - 50);

        final float rightTextWidth = FontUtils.getFontWidth(dialogFont, RIGHT_BUTTON_TEXT);
        final float rightTextHeight = FontUtils.getFontHeight(dialogFont, RIGHT_BUTTON_TEXT);
        rightButtonTextSprite.setX(getX() + ((getWidth() / 4) * 3) - rightTextWidth / 2);
        rightButtonTextSprite.setY(getY() + getHeight() - rightTextHeight - 50);
    }

    public void addOnScreen(final RenderLayer renderLayer, final int layer) {
        this.isShown = true;

        renderLayer.addSprite(this, true, layer);

        final int contentLayer = layer + 1;
        renderLayer.addTextSprite(currentScoreTextSprite, contentLayer);
        renderLayer.addTextSprite(bestScoreTextSprite, contentLayer);

        final int buttonTextLayer = contentLayer + 1;
        renderLayer.addTextSprite(leftButtonTextSprite, buttonTextLayer);
        renderLayer.addTextSprite(rightButtonTextSprite, buttonTextLayer);
    }

    public void removeFromScreen(final RenderLayer renderLayer) {
        this.isShown = false;

        renderLayer.removeSprite(this);
        renderLayer.removeSprite(currentScoreTextSprite);
        renderLayer.removeSprite(bestScoreTextSprite);
        renderLayer.removeSprite(leftButtonTextSprite);
        renderLayer.removeSprite(rightButtonTextSprite);
    }

    public boolean isShown() {
        return isShown;
    }

    public void checkLeftButtonClickedAndAction(final float clickX, final float clickY) {
        if (clickX >= leftButtonTextSprite.getX() && clickX <= leftButtonTextSprite.getX() + leftButtonTextSprite.getWidth()
                && clickY >= leftButtonTextSprite.getY() - BUTTON_CLICKABLE_ZONE
                && clickY <= leftButtonTextSprite.getY() + leftButtonTextSprite.getHeight() + BUTTON_CLICKABLE_ZONE) {
            if (onLoosingDialogActionListener != null) {
                onLoosingDialogActionListener.onLeftAction();
            }
        }
    }

    public void checkRightButtonClickedAndAction(final float clickX, final float clickY) {
        if (clickX >= rightButtonTextSprite.getX() && clickX <= rightButtonTextSprite.getX() + rightButtonTextSprite.getWidth()
                && clickY >= rightButtonTextSprite.getY() - BUTTON_CLICKABLE_ZONE
                && clickY <= rightButtonTextSprite.getY() + rightButtonTextSprite.getHeight() + BUTTON_CLICKABLE_ZONE) {
            if (onLoosingDialogActionListener != null) {
                onLoosingDialogActionListener.onRightAction();
            }
        }
    }

    public interface OnLoosingDialogActionListener {

        void onLeftAction();
        void onRightAction();

    }

}
