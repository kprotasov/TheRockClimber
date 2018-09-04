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

	private static final String FONT = "fonts/Tenor_Sans/TenorSans-Regular.ttf";
	private static final int DEFAULT_FONT_SIZE = 20;
	private static final Color FONT_COLOR = Color.valueOf("455060");
	private static final Color BORDER_COLOR = Color.valueOf("132338");
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
			+ "abcdefghijklmnopqrstuvwxyz"
			+ "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
			+ "абвгдеёжзийклмнопрстуфхцчшщъыьэюя"
			+ "1234567890.,:;_¡!¿?\"'+-*/()[]={}";

	public static BitmapFont createFont(final int fontSize) {
		return createFont(fontSize, true, FONT_COLOR);
	}

	public static BitmapFont createFont(final int fontSize, final boolean hasBorder) {
		return createFont(fontSize, hasBorder, FONT_COLOR);
	}

	public static BitmapFont createFont(final int fontSize, final boolean hasBorder, final Color fontColor) {
		final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(FONT));
		final FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.characters = CHARACTERS;
		parameter.size = fontSize;
		parameter.color = fontColor;
		if (hasBorder) {
			parameter.borderColor = BORDER_COLOR;
			parameter.borderWidth = 0.8f;
		}
		parameter.minFilter = Texture.TextureFilter.Linear;
		parameter.magFilter = Texture.TextureFilter.Linear;
		parameter.flip = true;
		final BitmapFont font = generator.generateFont(parameter);
		generator.dispose();
		return font;
	}

	public static BitmapFont createFont() {
		return createFont(DEFAULT_FONT_SIZE);
	}

}
