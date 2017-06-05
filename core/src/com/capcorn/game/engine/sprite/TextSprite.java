package com.capcorn.game.engine.sprite;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.capcorn.games.eightmarchcatch.font.FontUtils;

/**
 * Created by kprotasov on 04.12.2016.
 */

public class TextSprite implements SpriteBase {

    private BitmapFont font;
    private String text;
    private float x;
    private float y;

    public TextSprite(final String text, final BitmapFont font, final float x, final float y) {
        this.text = text;
        this.font = font;
        this.x = x;
        this.y = y;
    }

    public float getWidth() {
        return FontUtils.getFontWidth(font, String.valueOf(text));
    }

    public float getHeight() {
        return FontUtils.getFontHeight(font, String.valueOf(text));
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(final BitmapFont font) {
        this.font = font;
    }

    public float getX() {
        return x;
    }

    public void setX(final float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(final float y) {
        this.y = y;
    }

    @Override
    public void release() {

    }

}
