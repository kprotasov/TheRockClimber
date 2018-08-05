package com.capcorn.games.therockclimber.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

import static com.capcorn.games.therockclimber.graphics.AnimatedResourceNames.STONE;
import static com.capcorn.games.therockclimber.graphics.SoundResourceName.*;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.BACKGROUND;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.BLACK_TILE_ATLAS;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.BRILLIANCE;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.DIALOG_BUTTON;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.GOLD;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.LOOSING_GAME_DIALOG_BACKGROUND;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.PAUSE_BUTTON;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.RAM;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.RED_BUTTON;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.SNOWFLAKE;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.WHITE_TILE;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.WHITE_TILE_ATLAS;

/**
 * User: kprotasov
 * Date: 04.06.2017
 * Time: 15:53
 */

public class AssetsLoader {

    private static final float CHARACTER_ANIMATION_TIME = 0.03f; // 20 frames per seconds
    private static final float STONE_ANIMATION_TIME = 0.12f;

    private final Random tileRandom;

    private Texture characterTexture;
    private Animation characterRightAnimation;
    private Animation characterLeftAnimation;

    private Texture stoneTexture;
    private Animation stoneRightAnimation;
    private Animation stoneLeftAnimation;

    private Texture backgroundTexture;
    private TextureRegion backgroundTextureRegion;
    private TextureRegion loosingGameBackground;
    private TextureRegion dialogButton;
    private TextureRegion redButtonTextureRegion;
    private TextureRegion pauseButtonTextureRegion;

    private Texture ramTexture;
    private TextureRegion ramTextureRegion;

    private Texture blackTileTexture;
    private TextureRegion[] blackTileLeftTextureRegions = new TextureRegion[4];
    private TextureRegion[] blackTileRightTextureRegions = new TextureRegion[4];

    private Texture whiteTileTexture;
    private TextureRegion[] whiteTileLeftTextureRegions = new TextureRegion[4];
    private TextureRegion[] whiteTileRightTextureRegions = new TextureRegion[4];

    private Texture goldTexture;
    private TextureRegion goldTextureRegion;

    private TextureRegion snowflakeTextureRegion;

    private TextureRegion brillianceTextureRegion;

    private AnimatedResourceNames characterResourceName;

    private AssetManager manager;

    private Sound soundGold;
    private Sound soundBrilliance;
    private Sound soundFall;
    private Music backMusic1;
    private Music musicWind;
    private Music musicStone;

    public AssetsLoader() {
        tileRandom = new Random();
    }

    public void setCharacterResource(final AnimatedResourceNames characterResourceName) {
        this.characterResourceName = characterResourceName;
    }

    public AssetManager start() {
        manager = new AssetManager();
        manager.load(characterResourceName.getName(), Texture.class);
        manager.load(STONE.getName(), Texture.class);
        manager.load(BACKGROUND.getName(), Texture.class);
        manager.load(BLACK_TILE_ATLAS.getName(), Texture.class);
        manager.load(WHITE_TILE_ATLAS.getName(), Texture.class);
        manager.load(WHITE_TILE.getName(), Texture.class);
        manager.load(LOOSING_GAME_DIALOG_BACKGROUND.getName(), Texture.class);
        manager.load(DIALOG_BUTTON.getName(), Texture.class);
        manager.load(RAM.getName(), Texture.class);
        manager.load(GOLD.getName(), Texture.class);
        manager.load(BRILLIANCE.getName(), Texture.class);
        manager.load(RED_BUTTON.getName(), Texture.class);
        manager.load(PAUSE_BUTTON.getName(), Texture.class);
        manager.load(SNOWFLAKE.getName(), Texture.class);
        manager.load(SOUND_GOLD, Sound.class);
        manager.load(SOUND_BRILLIANCE, Sound.class);
        manager.load(BACK_MUSIC_1, Music.class);
        manager.load(MUSIC_WIND, Music.class);
        manager.load(MUSIC_STONE, Music.class);
        manager.load(SOUND_FALL, Sound.class);
        return manager;
    }

    public void createTextures() {
        characterTexture = manager.get(characterResourceName.getName());
        characterTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        stoneTexture = manager.get(STONE.getName());
        stoneTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        backgroundTexture = manager.get(BACKGROUND.getName());
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        blackTileTexture = manager.get(BLACK_TILE_ATLAS.getName());
        blackTileTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        whiteTileTexture = manager.get(WHITE_TILE_ATLAS.getName());
        whiteTileTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        Texture loosingGameBackgroundTexture = manager.get(LOOSING_GAME_DIALOG_BACKGROUND.getName());
        loosingGameBackgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture dialogButtonTexture = manager.get(DIALOG_BUTTON.getName());
        dialogButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture redButtonTexture = manager.get(RED_BUTTON.getName());
        redButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        characterRightAnimation = createCharacterTextureRegionRight();
        characterLeftAnimation = createCharacterTextureRegionLeft();

        stoneRightAnimation = createStoneTextureRegionRight();
        stoneLeftAnimation = createStoneTextureRegionLeft();

        goldTexture = manager.get(GOLD.getName());
        goldTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Texture snowflakeTexture = manager.get(SNOWFLAKE.getName());
        snowflakeTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        ramTexture = manager.get(RAM.getName());
        ramTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        final Texture brillianceTexture = manager.get(BRILLIANCE.getName());
        brillianceTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        final Texture pauseButtonTexture = manager.get(PAUSE_BUTTON.getName());
        pauseButtonTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        soundGold = manager.get(SOUND_GOLD);
        soundBrilliance = manager.get(SOUND_BRILLIANCE);
        backMusic1 = manager.get(BACK_MUSIC_1);
        musicWind = manager.get(MUSIC_WIND);
        musicStone = manager.get(MUSIC_STONE);
        soundFall = manager.get(SOUND_FALL);

        createBackgroundTextureRegion();
        createBlackTileTextureRegions();
        createWhiteTileTextureRegions();
        createRamTextureRegion();
        createGoldTextureRegion();
        createLoosingGameBackground(loosingGameBackgroundTexture);
        createDialogButton(dialogButtonTexture);
        createBrillianceTextureRegion(brillianceTexture);
        createRedButtonTextureRegion(redButtonTexture);
        createSnowflakeTextureRegion(snowflakeTexture);
        createPauseButtonTextureRegion(pauseButtonTexture);
    }

    public void dispose() {
        try {
            characterTexture.dispose();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        manager.unload(characterResourceName.getName());
        manager.unload(STONE.getName());
        manager.unload(BACKGROUND.getName());
        manager.unload(BLACK_TILE_ATLAS.getName());
        manager.unload(WHITE_TILE_ATLAS.getName());
        manager.unload(WHITE_TILE.getName());
        manager.unload(RAM.getName());
        manager.unload(LOOSING_GAME_DIALOG_BACKGROUND.getName());
        manager.unload(DIALOG_BUTTON.getName());
        manager.unload(GOLD.getName());
        manager.unload(BRILLIANCE.getName());
        manager.unload(RED_BUTTON.getName());
        manager.unload(PAUSE_BUTTON.getName());
        manager.unload(SNOWFLAKE.getName());
        manager.unload(SOUND_GOLD);
        manager.unload(SOUND_BRILLIANCE);
        manager.unload(BACK_MUSIC_1);
        manager.unload(MUSIC_WIND);
        manager.unload(MUSIC_STONE);
        manager.unload(SOUND_FALL);
        manager.dispose();
    }

    public Animation getCharacterRightAnimation() {
        return characterRightAnimation;
    }

    public Animation getCharacterLeftAnimation() {
        return characterLeftAnimation;
    }

    public Animation getStoneRightAnimation() {
        return stoneRightAnimation;
    }

    public Animation getStoneLeftAnimation() {
        return stoneLeftAnimation;
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
        final int tilePosition = tileRandom.nextInt(whiteTileLeftTextureRegions.length);
        return whiteTileLeftTextureRegions[tilePosition];
    }

    public TextureRegion getWhiteTileRightTextureRegion() {
        final int tilePosition = tileRandom.nextInt(whiteTileRightTextureRegions.length);
        return whiteTileRightTextureRegions[tilePosition];
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

    public TextureRegion getBrillianceTextureRegion() {
        return brillianceTextureRegion;
    }

    public TextureRegion getRedButtonTextureRegion() {
        return redButtonTextureRegion;
    }

    public TextureRegion getSnowflakeTextureRegion() {
        return snowflakeTextureRegion;
    }

    public TextureRegion getPauseButtonTextureRegion() {
        return pauseButtonTextureRegion;
    }

    public Sound getSoundGold() {
        return soundGold;
    }

    public Sound getSoundBrilliance() {
        return soundBrilliance;
    }

    public Music getMusicWind() {
        return musicWind;
    }

    public Music getMusicStone() {
        return musicStone;
    }

    public Music getBackMusic1() {
        backMusic1.setLooping(true);
        backMusic1.setVolume(0.6f);
        return backMusic1;
    }

    public Sound getSoundFall() {
        return soundFall;
    }

    private Animation createCharacterTextureRegionRight() {
        int k = 0;
        TextureRegion[] characterTextureRegion = new TextureRegion[characterResourceName.getHorizontalCount()
                * characterResourceName.getVerticalCount()];
        for (int j = 0; j < characterResourceName.getVerticalCount(); j++){
            for (int i = 0; i < characterResourceName.getHorizontalCount(); i++) {
                characterTextureRegion[k] = new TextureRegion(characterTexture, characterResourceName.getWidth() * i,
                        characterResourceName.getHeight() * j, characterResourceName.getWidth(), characterResourceName.getHeight());
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
        TextureRegion[] characterTextureRegion = new TextureRegion[characterResourceName.getHorizontalCount()
                * characterResourceName.getVerticalCount()];
        for (int j = 0; j < characterResourceName.getVerticalCount(); j++){
            for (int i = 0; i < characterResourceName.getHorizontalCount(); i++) {
                characterTextureRegion[k] = new TextureRegion(characterTexture, characterResourceName.getWidth() * i,
                        characterResourceName.getHeight() * j, characterResourceName.getWidth(), characterResourceName.getHeight());
                characterTextureRegion[k].flip(true, true);
                k++;
            }
        }
        characterLeftAnimation = new Animation(CHARACTER_ANIMATION_TIME, characterTextureRegion);
        characterLeftAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return characterLeftAnimation;
    }

    private Animation createStoneTextureRegionRight() {
        int k = 0;
        TextureRegion[] stoneTextureRegion = new TextureRegion[STONE.getHorizontalCount()
                * STONE.getVerticalCount()];
        for (int j = 0; j < STONE.getVerticalCount(); j++){
            for (int i = 0; i < STONE.getHorizontalCount(); i++) {
                stoneTextureRegion[k] = new TextureRegion(stoneTexture, STONE.getWidth() * i,
                        STONE.getHeight() * j, STONE.getWidth(), STONE.getHeight());
                stoneTextureRegion[k].flip(false, true);
                k++;
            }
        }
        stoneRightAnimation = new Animation(STONE_ANIMATION_TIME, stoneTextureRegion);
        stoneRightAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return stoneRightAnimation;
    }

    private Animation createStoneTextureRegionLeft() {
        int k = 0;
        TextureRegion[] stoneTextureRegion = new TextureRegion[STONE.getHorizontalCount()
                * STONE.getVerticalCount()];
        for (int j = 0; j < STONE.getVerticalCount(); j++){
            for (int i = 0; i < STONE.getHorizontalCount(); i++) {
                stoneTextureRegion[k] = new TextureRegion(stoneTexture, STONE.getWidth() * i,
                        STONE.getHeight() * j, STONE.getWidth(), STONE.getHeight());
                stoneTextureRegion[k].flip(true, false);
                k++;
            }
        }
        stoneRightAnimation = new Animation(STONE_ANIMATION_TIME, stoneTextureRegion);
        stoneRightAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return stoneRightAnimation;
    }

    private void createBackgroundTextureRegion() {
        backgroundTextureRegion = new TextureRegion(backgroundTexture, 0, 0, BACKGROUND.getWidth(), BACKGROUND.getHeight());
        backgroundTextureRegion.flip(false, true);
    }

    private void createBlackTileTextureRegions() {
        final int count = blackTileLeftTextureRegions.length;
        for (int i = 0; i < count; i++) {
            final TextureRegion textureRegionLeft = new TextureRegion(blackTileTexture, (BLACK_TILE_ATLAS.getWidth() / count) * i,
                    0, (BLACK_TILE_ATLAS.getWidth() / count) - 1, BLACK_TILE_ATLAS.getHeight());
            textureRegionLeft.flip(true, true);
            blackTileLeftTextureRegions[i] = textureRegionLeft;

            final TextureRegion textureRegionRight = new TextureRegion(blackTileTexture, (BLACK_TILE_ATLAS.getWidth() / count) * i,
                    0, (BLACK_TILE_ATLAS.getWidth() / count) - 1, BLACK_TILE_ATLAS.getHeight());
            textureRegionRight.flip(false, true);
            blackTileRightTextureRegions[i] = textureRegionRight;
        }
    }

    private void createWhiteTileTextureRegions() {
        final int count = whiteTileLeftTextureRegions.length;
        for (int i = 0; i < count; i++) {
            final TextureRegion textureRegionLeft = new TextureRegion(whiteTileTexture, (WHITE_TILE_ATLAS.getWidth() / count) * i,
                    0, (WHITE_TILE_ATLAS.getWidth() / count) - 1, WHITE_TILE_ATLAS.getHeight());
            textureRegionLeft.flip(true, true);
            whiteTileLeftTextureRegions[i] = textureRegionLeft;

            final TextureRegion textureRegionRight = new TextureRegion(whiteTileTexture, (WHITE_TILE_ATLAS.getWidth() / count) * i,
                    0, (WHITE_TILE_ATLAS.getWidth() / count) - 1, WHITE_TILE_ATLAS.getHeight());
            textureRegionRight.flip(false, true);
            whiteTileRightTextureRegions[i] = textureRegionRight;
        }
    }

    private void createGoldTextureRegion() {
        goldTextureRegion = new TextureRegion(goldTexture, 0, 0, GOLD.getWidth(), GOLD.getHeight());
        goldTextureRegion.flip(false, true);
    }

    private void createRedButtonTextureRegion(final Texture redButtonTexture) {
        redButtonTextureRegion = new TextureRegion(redButtonTexture, 0, 0, RED_BUTTON.getWidth(), RED_BUTTON.getHeight());
        redButtonTextureRegion.flip(false, true);
    }

    private void createBrillianceTextureRegion(final Texture brillianceTexture) {
        brillianceTextureRegion = new TextureRegion(brillianceTexture, 0, 0, BRILLIANCE.getWidth(), BRILLIANCE.getHeight());
        brillianceTextureRegion.flip(false, true);
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

    private void createSnowflakeTextureRegion(final Texture snowflakeTexure) {
        snowflakeTextureRegion = new TextureRegion(snowflakeTexure, 0, 0, SNOWFLAKE.getWidth(), SNOWFLAKE.getHeight());
        snowflakeTextureRegion.flip(false, true);
    }

    private void createPauseButtonTextureRegion(final Texture pauseButtonTexture) {
        pauseButtonTextureRegion = new TextureRegion(pauseButtonTexture, 0, 0, PAUSE_BUTTON.getWidth(), PAUSE_BUTTON.getHeight());
        pauseButtonTextureRegion.flip(false, true);
    }

}
