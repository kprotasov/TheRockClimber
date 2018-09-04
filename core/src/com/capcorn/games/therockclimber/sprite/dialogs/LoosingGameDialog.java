package com.capcorn.games.therockclimber.sprite.dialogs;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.capcorn.game.engine.core.RenderLayer;
import com.capcorn.game.engine.sprite.Sprite;
import com.capcorn.game.engine.sprite.TextSprite;
import com.capcorn.games.therockclimber.font.DistanceFontCreator;
import com.capcorn.games.therockclimber.font.FontUtils;
import com.capcorn.games.therockclimber.graphics.AssetsLoader;
import com.capcorn.settings.StringsResources;

/**
 * User: kprotasov
 * Date: 10.09.2017
 * Time: 17:47
 */

public class LoosingGameDialog extends Sprite {

    private static final String INITIAL_TEXT = "";
    private static String RESTART_BUTTON_TEXT;
    private static String CONTINUE_BUTTON_TEXT;
    private static final String X_2_BUTTON_TEXT = "x1.5";

    private static final int TITLE_FONT_SIZE = 30;
    private static final int BUTTON_CLICKABLE_ZONE = 30;

    private final BitmapFont dialogFont;
    private final BitmapFont x2Font;

    private final TextSprite currentScoreTextSprite;
    private final TextSprite bestScoreTextSprite;
    private final TextSprite moneyTextSprite;
    private final TextSprite restartButtonTextSprite;
    private final TextSprite continueButtonTextSprite;
    private final TextSprite x2ButtonTextSprite;

    private boolean canContinueGame = false;
    private boolean canIncreaseMoney = false;

    private boolean isShown = false;
    private OnLoosingDialogActionListener onLoosingDialogActionListener;
    private StringsResources stringsResources;

    public LoosingGameDialog(final AssetsLoader assetsLoader,
                             final OnLoosingDialogActionListener listener, final StringsResources stringsResources) {
        super(assetsLoader.getLoosingGameBackgroundTextureRegion(), 0, 0);

        this.onLoosingDialogActionListener = listener;
        this.stringsResources = stringsResources;

        RESTART_BUTTON_TEXT = stringsResources.getRestartButton();
        CONTINUE_BUTTON_TEXT = stringsResources.getContinueButton();

        dialogFont = DistanceFontCreator.createFont(TITLE_FONT_SIZE, false);
        currentScoreTextSprite = new TextSprite(INITIAL_TEXT, dialogFont, 0, 0);
        bestScoreTextSprite = new TextSprite(INITIAL_TEXT, dialogFont, 0, 0);
        moneyTextSprite = new TextSprite(INITIAL_TEXT, dialogFont, 0, 0);
        restartButtonTextSprite = new TextSprite(RESTART_BUTTON_TEXT, dialogFont, 0, 0);
        continueButtonTextSprite = new TextSprite(CONTINUE_BUTTON_TEXT, dialogFont, 0, 0);

        x2Font = DistanceFontCreator.createFont(TITLE_FONT_SIZE, false, Color.valueOf("ef1515"));
        x2ButtonTextSprite = new TextSprite(X_2_BUTTON_TEXT, x2Font, 0, 0);
    }

    public void initDialog(final long currentScore, final long bestScore, final long totalMoneyCount,
                           final boolean canContinueGame, final boolean canIncreaseMoney) {
        this.canContinueGame = canContinueGame;
        this.canIncreaseMoney = canIncreaseMoney;

        final String formattedCurrentScoreText = String.format(stringsResources.getScore(), currentScore);
        final String formattedBestScoreText = String.format(stringsResources.getBestScore(), bestScore);
        final String formattedTotalMoneyText = String.format(stringsResources.getMoney(), totalMoneyCount);

        final float dialogCenter = getX() + getWidth() / 2;

        final float currentScoreWidth = FontUtils.getFontWidth(dialogFont, formattedCurrentScoreText);
        final float currentScoreHeight = FontUtils.getFontHeight(dialogFont, formattedCurrentScoreText);
        currentScoreTextSprite.setText(formattedCurrentScoreText);
        currentScoreTextSprite.setX(dialogCenter - currentScoreWidth / 2);
        currentScoreTextSprite.setY(getY() + 30);

        final float bestScoreWidth = FontUtils.getFontWidth(dialogFont, formattedBestScoreText);
        final float bestScoreHeight = FontUtils.getFontHeight(dialogFont, formattedBestScoreText);
        bestScoreTextSprite.setText(formattedBestScoreText);
        bestScoreTextSprite.setX(dialogCenter - bestScoreWidth / 2);
        bestScoreTextSprite.setY(currentScoreTextSprite.getY() + currentScoreHeight + 30);

        final float totalMoneyWidth = FontUtils.getFontWidth(dialogFont, formattedTotalMoneyText);
        moneyTextSprite.setText(formattedTotalMoneyText);
        moneyTextSprite.setX(dialogCenter - totalMoneyWidth / 2);
        moneyTextSprite.setY(bestScoreTextSprite.getY() + bestScoreHeight + 30);

        if (canContinueGame) {
            final float restartTextWidth = FontUtils.getFontWidth(dialogFont, RESTART_BUTTON_TEXT);
            final float restartTextHeight = FontUtils.getFontHeight(dialogFont, RESTART_BUTTON_TEXT);
            restartButtonTextSprite.setX(getX() + getWidth() / 4 - restartTextWidth / 2);
            restartButtonTextSprite.setY(getY() + getHeight() - restartTextHeight - 50);

            final float continueTextWidth = FontUtils.getFontWidth(dialogFont, CONTINUE_BUTTON_TEXT);
            final float continueTextHeight = FontUtils.getFontHeight(dialogFont, CONTINUE_BUTTON_TEXT);
            continueButtonTextSprite.setX(getX() + ((getWidth() / 4) * 3) - continueTextWidth / 2);
            continueButtonTextSprite.setY(getY() + getHeight() - continueTextHeight - 50);
        } else {
            final float restartTextWidth = FontUtils.getFontWidth(dialogFont, RESTART_BUTTON_TEXT);
            final float restartTextHeight = FontUtils.getFontHeight(dialogFont, RESTART_BUTTON_TEXT);
            restartButtonTextSprite.setX(getX() + getWidth() / 2 - restartTextWidth / 2);
            restartButtonTextSprite.setY(getY() + getHeight() - restartTextHeight - 50);
        }
        if (canIncreaseMoney) {
            //final float x2TextWidth = FontUtils.getFontWidth(x2Font, X_2_BUTTON_TEXT);
            //final float x2TextHeight = FontUtils.getFontHeight(x2Font, X_2_BUTTON_TEXT);
            x2ButtonTextSprite.setX(moneyTextSprite.getX() + totalMoneyWidth + 10);
            x2ButtonTextSprite.setY(moneyTextSprite.getY());
        }
    }

    public void addOnScreen(final RenderLayer renderLayer, final int layer) {
        this.isShown = true;

        renderLayer.addSprite(this, true, layer);

        final int contentLayer = layer + 1;
        renderLayer.addTextSprite(currentScoreTextSprite, contentLayer);
        renderLayer.addTextSprite(bestScoreTextSprite, contentLayer);
        renderLayer.addTextSprite(moneyTextSprite, contentLayer);

        final int buttonTextLayer = contentLayer + 1;
        renderLayer.addTextSprite(restartButtonTextSprite, buttonTextLayer);
        if (canContinueGame) {
            renderLayer.addTextSprite(continueButtonTextSprite, buttonTextLayer);
        }
        if (canIncreaseMoney) {
            renderLayer.addTextSprite(x2ButtonTextSprite, buttonTextLayer);
        }
    }

    public void removeFromScreen(final RenderLayer renderLayer) {
        this.isShown = false;

        renderLayer.removeSprite(this);
        renderLayer.removeSprite(currentScoreTextSprite);
        renderLayer.removeSprite(bestScoreTextSprite);
        renderLayer.removeSprite(moneyTextSprite);
        renderLayer.removeSprite(restartButtonTextSprite);
        renderLayer.removeSprite(continueButtonTextSprite);
        renderLayer.removeSprite(x2ButtonTextSprite);
    }

    public boolean isShown() {
        return isShown;
    }

    public void checkRestartButtonClickedAndAction(final float clickX, final float clickY) {
        if (clickX >= restartButtonTextSprite.getX() && clickX <= restartButtonTextSprite.getX() + restartButtonTextSprite.getWidth()
                && clickY >= restartButtonTextSprite.getY() - BUTTON_CLICKABLE_ZONE
                && clickY <= restartButtonTextSprite.getY() + restartButtonTextSprite.getHeight() + BUTTON_CLICKABLE_ZONE) {
            if (onLoosingDialogActionListener != null) {
                onLoosingDialogActionListener.onRestartAction();
            }
        }
    }

    public void checkContinueButtonClickedAndAction(final float clickX, final float clickY) {
        if (clickX >= continueButtonTextSprite.getX() && clickX <= continueButtonTextSprite.getX() + continueButtonTextSprite.getWidth()
                && clickY >= continueButtonTextSprite.getY() - BUTTON_CLICKABLE_ZONE
                && clickY <= continueButtonTextSprite.getY() + continueButtonTextSprite.getHeight() + BUTTON_CLICKABLE_ZONE && canContinueGame) {
            if (onLoosingDialogActionListener != null) {
                onLoosingDialogActionListener.onContinueAction();
            }
        }
    }

    public void checkX2ButtonClickAndAction(final float clickX, final float clickY) {
        if (clickX >= x2ButtonTextSprite.getX() && clickX <= x2ButtonTextSprite.getX() + x2ButtonTextSprite.getWidth()
                && clickY >= x2ButtonTextSprite.getY() - BUTTON_CLICKABLE_ZONE
                && clickY <= x2ButtonTextSprite.getY() + x2ButtonTextSprite.getHeight() + BUTTON_CLICKABLE_ZONE && canIncreaseMoney) {
            if (onLoosingDialogActionListener != null) {
                onLoosingDialogActionListener.onX2Action();
            }
        }
    }

    public interface OnLoosingDialogActionListener {

        void onRestartAction();
        void onContinueAction();
        void onX2Action();

    }

}
