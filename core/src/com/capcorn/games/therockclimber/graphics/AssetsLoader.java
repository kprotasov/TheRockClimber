package com.capcorn.games.therockclimber.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import static com.capcorn.games.therockclimber.graphics.AnimatedResourceNames.CHARACTER;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.BACKGROUND;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.BLACK_TILE_ATLAS;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.DIALOG_BUTTON;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.GOLD;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.LOOSING_GAME_DIALOG_BACKGROUND;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.RAM;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.WHITE_TILE;

/**
 * User: kprotasov
 * Date: 04.06.2017
 * Time: 15:53
 */

public class AssetsLoader {

    private static final float CHARACTER_ANIMATION_TIME = 0.03f; // 20 frames per seconds

    private final Random tileRandom;

    private Texture characterTexture;
    private Animation characterRightAnimation;
    private Animation characterLeftAnimation;

    private Texture backgroundTexture;
    private TextureRegion backgroundTextureRegion;
    private TextureRegion loosingGameBackground;
    private TextureRegion dialogButton;

    private Texture ramTexture;
    private TextureRegion ramTextureRegion;

    private Texture blackTileTexture;
    private TextureRegion[] blackTileLeftTextureRegions = new TextureRegion[2];
    private TextureRegion[] blackTileRightTextureRegions = new TextureRegion[2];

    private Texture whiteTileTexture;
    private TextureRegion whiteTileLeftTextureRegion;
    private TextureRegion whiteTileRightTextureRegion;

    private Texture goldTexture;
    private TextureRegion goldTextureRegion;

    private AssetManager manager;

    public AssetsLoader() {
        tileRandom = new Random();
    }

    public AssetManager start() {
        manager = new AssetManager();
        manager.load(CHARACTER.getName(), Texture.class);
        manager.load(BACKGROUND.getName(), Texture.class);
        manager.load(BLACK_TILE_ATLAS.getName(), Texture.class);
        manager.load(WHITE_TILE.getName(), Texture.class);
        manager.load(LOOSING_GAME_DIALOG_BACKGROUND.getName(), Texture.class);
        manager.load(DIALOG_BUTTON.getName(), Texture.class);
        manager.load(RAM.getName(), Texture.class);
        manager.load(GOLD.getName(), Texture.class);
        return manager;
    }

    public void createTextures() {
        characterTexture = manager.get(CHARACTER.getName());
        characterTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        backgroundTexture = manager.get(BACKGROUND.getName());
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        blackTileTexture = manager.get(BLACK_TILE_ATLAS.getName());
        blackTileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        whiteTileTexture = manager.get(WHITE_TILE.getName());
        whiteTileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture loosingGameBackgroundTexture = manager.get(LOOSING_GAME_DIALOG_BACKGROUND.getName());
        loosingGameBackgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture dialogButtonTexture = manager.get(DIALOG_BUTTON.getName());
        dialogButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        characterRightAnimation = createCharacterTextureRegionRight();
        characterLeftAnimation = createCharacterTextureRegionLeft();

        goldTexture = manager.get(GOLD.getName());
        goldTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        ramTexture = manager.get(RAM.getName());
        ramTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        createBackgroundTextureRegion();
        createBlackTileTextureRegions();
        createWhiteTileLeftTextureRegion();
        createWhiteTileRightTextureRegion();
        createRamTextureRegion();
        createGoldTextureRegion();
        createLoosingGameBackground(loosingGameBackgroundTexture);
        createDialogButton(dialogButtonTexture);
    }

    public void dispose() {
        try {
            characterTexture.dispose();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        manager.unload(CHARACTER.getName());
        manager.unload(BACKGROUND.getName());
        manager.unload(BLACK_TILE_ATLAS.getName());
        manager.unload(WHITE_TILE.getName());
        manager.unload(RAM.getName());
        manager.unload(LOOSING_GAME_DIALOG_BACKGROUND.getName());
        manager.unload(DIALOG_BUTTON.getName());
        manager.unload(GOLD.getName());
        manager.dispose();
    }

    public Animation getCharacterRightAnimation() {
        return characterRightAnimation;
    }

    public Animation getCharacterLeftAnimation() {
        return characterLeftAnimation;
    }

    public TextureRegion getBackgroundTextureRegion() {
        return backgroundTextureRegion;
    }

    public TextureRegion getBlackTileLeftTextureRegion() {
        final int tilePosition = tileRandom.nextInt(blackTileLeftTextureRegions.length);
        return blackTileLeftTextureRegions[tilePosition];
    }

    public TextureRegion getBlackTileRightTextureRegion() {
        final int tilePosition = tileRandom.nextInt(blackTileRightTextureRegions.length);
        return blackTileRightTextureRegions[tilePosition];
    }

    public TextureRegion getWhiteTileLeftTextureRegion() {
        return whiteTileLeftTextureRegion;
    }

    public TextureRegion getWhiteTileRightTextureRegion() {
        return whiteTileRightTextureRegion;
    }

    public TextureRegion getGoldTextureRegion() {
        return goldTextureRegion;
    }

    public TextureRegion getRamTextureRegion() {
        return ramTextureRegion;
    }

    public TextureRegion getLoosingGameBackgroundTextureRegion() {
        return loosingGameBackground;
    }

    public TextureRegion getDialogButton() {
        return dialogButton;
    }

    private Animation createCharacterTextureRegionRight() {
        int k = 0;
        TextureRegion[] characterTextureRegion = new TextureRegion[CHARACTER.getHorizontalCount()
                * CHARACTER.getVerticalCount()];
        for (int j = 0; j < CHARACTER.getVerticalCount(); j++){
            for (int i = 0; i < CHARACTER.getHorizontalCount(); i++) {
                characterTextureRegion[k] = new TextureRegion(characterTexture, CHARACTER.getWidth() * i,
                        CHARACTER.getHeight() * j, CHARACTER.getWidth(), CHARACTER.getHeight());
                characterTextureRegion[k].flip(false, true);
                k++;
            }
        }
        characterRightAnimation = new Animation(CHARACTER_ANIMATION_TIME, characterTextureRegion);
        characterRightAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return characterRightAnimation;
    }

    private Animation createCharacterTextureRegionLeft() {
        int k = 0;
        TextureRegion[] characterTextureRegion = new TextureRegion[CHARACTER.getHorizontalCount()
                * CHARACTER.getVerticalCount()];
        for (int j = 0; j < CHARACTER.getVerticalCount(); j++){
            for (int i = 0; i < CHARACTER.getHorizontalCount(); i++) {
                characterTextureRegion[k] = new TextureRegion(characterTexture, CHARACTER.getWidth() * i,
                        CHARACTER.getHeight() * j, CHARACTER.getWidth(), CHARACTER.getHeight());
                characterTextureRegion[k].flip(true, true);
                k++;
            }
        }
        characterLeftAnimation = new Animation(CHARACTER_ANIMATION_TIME, characterTextureRegion);
        characterLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return characterLeftAnimation;
    }

    private void createBackgroundTextureRegion() {
        backgroundTextureRegion = new TextureRegion(backgroundTexture, 0, 0, BACKGROUND.getWidth(), BACKGROUND.getHeight());
        backgroundTextureRegion.flip(false, true);
    }

    private void createBlackTileTextureRegions() {
        final int count = blackTileLeftTextureRegions.length;
        for (int i = 0; i < count; i++) {
            final TextureRegion textureRegionLeft = new TextureRegion(blackTileTexture, (BLACK_TILE_ATLAS.getWidth() / count) * i,
                    0, (BLACK_TILE_ATLAS.getWidth() / count), BLACK_TILE_ATLAS.getHeight());
            textureRegionLeft.flip(true, true);
            blackTileLeftTextureRegions[i] = textureRegionLeft;

            final TextureRegion textureRegionRight = new TextureRegion(blackTileTexture, (BLACK_TILE_ATLAS.getWidth() / count) * i,
                    0, (BLACK_TILE_ATLAS.getWidth() / count), BLACK_TILE_ATLAS.getHeight());
            textureRegionRight.flip(false, true);
            blackTileRightTextureRegions[i] = textureRegionRight;
        }
    }

    private void createWhiteTileLeftTextureRegion() {
        whiteTileLeftTextureRegion = new TextureRegion(whiteTileTexture, 0, 0, WHITE_TILE.getWidth(), WHITE_TILE.getHeight());
        whiteTileLeftTextureRegion.flip(true, true);
    }

    private void createWhiteTileRightTextureRegion() {
        whiteTileRightTextureRegion = new TextureRegion(whiteTileTexture, 0, 0, WHITE_TILE.getWidth(), WHITE_TILE.getHeight());
        whiteTileRightTextureRegion.flip(false, true);
    }

    private void createGoldTextureRegion() {
        goldTextureRegion = new TextureRegion(goldTexture, 0, 0, GOLD.getWidth(), GOLD.getHeight());
        goldTextureRegion.flip(false, true);
    }

    private void createRamTextureRegion() {
        ramTextureRegion = new TextureRegion(ramTexture, 0, 0, GOLD.getWidth(), GOLD.getHeight());
        ramTextureRegion.flip(false, true);
    }

    private void createLoosingGameBackground(final Texture loosingGameBackgroundTexture) {
        loosingGameBackground = new TextureRegion(loosingGameBackgroundTexture, 0, 0, LOOSING_GAME_DIALOG_BACKGROUND.getWidth(),
                LOOSING_GAME_DIALOG_BACKGROUND.getHeight());
        loosingGameBackground.flip(false, true);
    }

    private void createDialogButton(final Texture dialogButtonTexture) {
        dialogButton = new TextureRegion(dialogButtonTexture, 0, 0, DIALOG_BUTTON.getWidth(), DIALOG_BUTTON.getHeight());
        dialogButton.flip(false, true);
    }

}
