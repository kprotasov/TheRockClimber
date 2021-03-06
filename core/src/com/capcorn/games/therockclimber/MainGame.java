package com.capcorn.games.therockclimber;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.capcorn.games.therockclimber.graphics.AnimatedResourceNames;
import com.capcorn.games.therockclimber.graphics.AssetsLoader;
import com.capcorn.games.therockclimber.screen.GameScreen;
import com.capcorn.games.therockclimber.settings.store.SelectedGameCharacterStore;
import com.capcorn.settings.StringsResources;

public class MainGame extends Game {

	private GameScreen gameScreen;
	private AssetsLoader assetsLoader;
	private AssetManager assetsManager;
	private boolean isAssetsLoaded = false;
	private SelectedGameCharacterStore selectedGameCharacterStore;
	private final OnShowAdListener onShowAdListener;
	private final StringsResources stringsResources;

	public MainGame(final OnShowAdListener listener, final StringsResources stringsResources) {
		this.onShowAdListener = listener;
		this.stringsResources = stringsResources;
	}

	@Override
	public void create() {
		assetsLoader = new AssetsLoader();

		selectedGameCharacterStore = new SelectedGameCharacterStore();
		final String selectedCharacter = selectedGameCharacterStore.getSelectedCharacter();
		final AnimatedResourceNames characterResourceName = AnimatedResourceNames.getResourceByName(selectedCharacter);

		assetsLoader.setCharacterResource(characterResourceName);
		assetsManager = assetsLoader.start();
	}

	@Override
	public void render() {
		super.render();
		if (!isAssetsLoaded) {
			isAssetsLoaded = assetsManager.update();
			final float progress = assetsManager.getProgress();
			if (isAssetsLoaded) {
				initGameScreen();
			}
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		assetsLoader.dispose();
	}

	public void onRewardedVideoLoaded() {
		if (gameScreen != null) {
			gameScreen.onRewardedVideoLoaded();
		}
	}

	public void onRewardedVideoUnloaded() {
		if (gameScreen != null) {
			gameScreen.onRewardedVideoUnload();
		}
	}

	public void onContinueVideoRewarded() {
		if (gameScreen != null) {
			gameScreen.onContinueVideoRewarded();
		}
	}

	public void onX2VideoRewarded() {
		if (gameScreen != null) {
			gameScreen.onX2VideoRewarded();
		}
	}

	private void initGameScreen() {
		assetsLoader.createTextures();
		gameScreen = new GameScreen(assetsLoader, onShowAdListener, stringsResources);
		setScreen(gameScreen);
	}

}
