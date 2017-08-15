package com.capcorn.games.therockclimber.font;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * Created by kprotasov on 04.02.2017.
 */

public class FontUtils {

    private FontUtils() {
        throw new UnsupportedOperationException("use static methods");
    }

    public static float getFontWidth(final BitmapFont font, final String text) {
        final GlyphLayout glyphLayout = new GlyphLayout(font, text);
        return glyphLayout.width;
    }

    public static float getFontHeight(final BitmapFont font, final String text) {
        final GlyphLayout glyphLayout = new GlyphLayout(font, text);
        return glyphLayout.height;
    }

}
