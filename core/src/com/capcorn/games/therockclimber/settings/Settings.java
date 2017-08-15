package com.capcorn.games.therockclimber.settings;

import com.badlogic.gdx.Gdx;

/**
 * User: kprotasov
 * Date: 28.12.2016
 * Time: 14:27
 */
public final class Settings {

	private Settings() {
		throw new IllegalArgumentException("private class constructor");
	}

	public static final float GAME_SCREEN_WIDTH = 480;
	public static final float GAME_SCREEN_HEIGHT = 854;

	private static float gameWidth;
	private static float gameHeight;
	private static float scaleParam;

	public static final ScreenSize getScreenSize() {
		final float screenWidth = Gdx.graphics.getWidth() >= Gdx.graphics.getHeight() ? Gdx.graphics.getHeight() : Gdx.graphics.getWidth();
		final float screenHeight = Gdx.graphics.getHeight() >= Gdx.graphics.getWidth() ? Gdx.graphics.getHeight() : Gdx.graphics.getWidth();

		gameWidth = Settings.GAME_SCREEN_WIDTH;
		scaleParam = gameWidth / screenWidth;
		gameHeight = screenHeight * (scaleParam);

		return new ScreenSize(gameWidth, gameHeight, scaleParam);
	}

	public static class ScreenSize {

		public float WIDTH = 0;
		public float HEIGHT = 0;
		public float SCALE_PARAM = 0;

		public ScreenSize(final float width, final float height, final float scaleParam) {
			this.WIDTH = width;
			this.HEIGHT = height;
			SCALE_PARAM = scaleParam;
		}

	}

}
