package com.capcorn.games.therockclimber.font;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * User: kprotasov
 * Date: 03.02.2017
 * Time: 17:44
 */
public class DistanceFontCreator {

	private static final String FONT = "fonts/Lemonada-Regular.ttf";
	private static final int FONT_SIZE = 20;
	private static final Color FONT_COLOR = Color.valueOf("455060");//Color.valueOf("ffffff");
	private static final Color BORDER_COLOR = Color.valueOf("132338");

	public static BitmapFont createFont() {
		final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT));
		final FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = FONT_SIZE;
		parameter.color = FONT_COLOR;
		parameter.borderColor = BORDER_COLOR;
		parameter.borderWidth = 0.8f;
		parameter.minFilter = Texture.TextureFilter.Linear;
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.flip = true;
		final BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}

}
