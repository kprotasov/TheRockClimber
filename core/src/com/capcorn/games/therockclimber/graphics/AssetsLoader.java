package com.capcorn.games.therockclimber.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.capcorn.games.therockclimber.graphics.AnimatedResourceNames.CHARACTER;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.BACKGROUND;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.BLACK_TILE;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.GOLD;
import static com.capcorn.games.therockclimber.graphics.StaticResourceNames.WHITE_TILE;

/**
 * Created by kprotasov on 04.06.2017.
 */

public class AssetsLoader {

    private static final float CHARACTER_ANIMATION_TIME = 0.03f; // 20 frames per seconds

    private Texture characterTexture;
    private Animation characterRightAnimation;
    private Animation characterLeftAnimation;

    private Texture backgroundTexture;
    private TextureRegion backgroundTextureRegion;
    private Texture blackTileTexture;
    private TextureRegion blackTileLeftTextureRegion;
    private TextureRegion blackTileRightTextureRegion;
    private Texture whiteTileTexture;
    private TextureRegion whiteTileLeftTextureRegion;
    private TextureRegion whiteTileRightTextureRegion;
    private Texture goldTexture;
    private TextureRegion goldTextureRegion;

    private AssetManager manager;

    public AssetManager start() {
        manager = new AssetManager();
        manager.load(CHARACTER.getName(), Texture.class);
        manager.load(BACKGROUND.getName(), Texture.class);
        manager.load(BLACK_TILE.getName(), Texture.class);
        manager.load(WHITE_TILE.getName(), Texture.class);
        manager.load(GOLD.getName(), Texture.class);
        return manager;
    }

    public void createTextures() {
        characterTexture = manager.get(CHARACTER.getName());
        characterTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        backgroundTexture = manager.get(BACKGROUND.getName());
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        blackTileTexture = manager.get(BLACK_TILE.getName());
        blackTileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        whiteTileTexture = manager.get(WHITE_TILE.getName());
        whiteTileTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        characterRightAnimation = createCharacterTextureRegionRight();
        characterLeftAnimation = createCharacterTextureRegionLeft();

        goldTexture = manager.get(GOLD.getName());
        goldTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        createBackgroundTextureRegion();
        createBlackTileLeftTextureRegion();
        createBlackTileRightTextureRegion();
        createWhiteTileLeftTextureRegion();
        createWhiteTileRightTextureRegion();
        createGoldTextureRegion();
    }

    public void dispose() {
        try {
            characterTexture.dispose();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        manager.unload(CHARACTER.getName());
        manager.unload(BACKGROUND.getName());
        manager.unload(BLACK_TILE.getName());
        manager.unload(WHITE_TILE.getName());
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
        return blackTileLeftTextureRegion;
    }

    public TextureRegion getBlackTileRightTextureRegion() {
        return blackTileRightTextureRegion;
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

    private void createBlackTileLeftTextureRegion() {
        blackTileLeftTextureRegion = new TextureRegion(blackTileTexture, 0, 0, BLACK_TILE.getWidth(), BLACK_TILE.getHeight());
        blackTileLeftTextureRegion.flip(true, true);
    }

    private void createBlackTileRightTextureRegion() {
        blackTileRightTextureRegion = new TextureRegion(blackTileTexture, 0, 0, BLACK_TILE.getWidth(), BLACK_TILE.getHeight());
        blackTileRightTextureRegion.flip(false, true);
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

}
