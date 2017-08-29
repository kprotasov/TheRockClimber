package com.capcorn.games.therockclimber.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.capcorn.game.engine.animation.Animator;
import com.capcorn.game.engine.animation.TweenAnimation;
import com.capcorn.game.engine.core.RenderLayer;
import com.capcorn.game.engine.pool.ObjectPool;
import com.capcorn.game.engine.sprite.AnimatedSprite;
import com.capcorn.game.engine.sprite.Sprite;
import com.capcorn.game.engine.sprite.TextSprite;
import com.capcorn.game.engine.utils.AccelerationRandom;
import com.capcorn.game.engine.utils.BinaryRandom;
import com.capcorn.game.engine.utils.PositionUtils;
import com.capcorn.games.therockclimber.creator.TileCreator;
import com.capcorn.games.therockclimber.entity.TileEntity;
import com.capcorn.games.therockclimber.font.DistanceFontCreator;
import com.capcorn.games.therockclimber.font.FontUtils;
import com.capcorn.games.therockclimber.graphics.AssetsLoader;
import com.capcorn.games.therockclimber.input.InputHandler;
import com.capcorn.games.therockclimber.input.OnTouchListener;
import com.capcorn.games.therockclimber.settings.Settings;
import com.capcorn.games.therockclimber.sprite.CharacterSprite;
import com.capcorn.games.therockclimber.sprite.GoldSprite;
import com.capcorn.games.therockclimber.sprite.TileSprite;

import java.util.ArrayList;

/**
 * User: kprotasov
 * Date: 17.06.2017
 * Time: 17:00
 */

public class GameScreen implements Screen, OnTouchListener{

    private static final float CHARACTER_MAX_UP = 50;
    private static final int CHARACTER_MAX_ROTATION_ANGLE = 20;
    private static final int TILE_WIDTH = 100;
    private static final int TILE_BEVEL_SIZE = 20;
    private static final int TILE_HEIGHT = 240;
    private static final int TILE_SPEED = 800;
    private static final float CHARACTER_TWEEN_SPEED = 2000.0f;
    private static final float GOLD_TWEEN_SPEED = 1000.0f;
    private static int HORIZONTAL_TILE_COUNT;
    private static int VERTICAL_TILE_COUNT;
    private static float CHARACTER_Y_POSITION;
    private static final float CHARACTER_X_OFFSET = 50;
    private static final float GOLD_SIZE = 50;
    private static final float CHARACTER_HEIGHT = 196;

    private AssetsLoader assetsLoader;
    private RenderLayer renderLayer;
    private Settings.ScreenSize screenSize;
    private OrthographicCamera camera;
    private FPSLogger fps;
    private ObjectPool pool;
    private Animator animator;
    private BitmapFont gameFont;

    private TextSprite distanceText;
    private int distance = 0;
    private TextSprite moneyText;
    private int moneyCount = 0;

    private TweenAnimation characterLeftTween;
    private TweenAnimation characterRightTween;
    private TweenAnimation characterTween;
    private CharacterSprite character;
    private Sprite backgroundSprite;

    private TileCreator tileCreator;
    private ArrayList<TileEntity> tiles;
    private ArrayList<TileSprite> tileSprites;
    private float tilesStartYPosition = TILE_HEIGHT;

    private AccelerationRandom goldBonusRandom;
    private BinaryRandom binaryRandom;

    private static final int BACKGROUND_LAYER = 0;
    private static final int TILE_LAYER = 1;
    private static final int BONUS_LAYER = 2;
    private static final int CHARACTER_LAYER = 3;
    private static final int TEXT_LAYER = 4;

    public GameScreen(final AssetsLoader assetsLoader) {
        this.assetsLoader = assetsLoader;
        Gdx.input.setInputProcessor(new InputHandler(this));

        fps = new FPSLogger();
        screenSize = Settings.getScreenSize();
        camera = new OrthographicCamera();
        camera.setToOrtho(true, screenSize.WIDTH, screenSize.HEIGHT);

        gameFont = DistanceFontCreator.createFont();

        animator = new Animator();

        HORIZONTAL_TILE_COUNT = 2;
        VERTICAL_TILE_COUNT = (int) screenSize.HEIGHT / TILE_HEIGHT + 2;
        CHARACTER_Y_POSITION = screenSize.HEIGHT - 246;

        renderLayer = new RenderLayer(camera, Color.FOREST, screenSize.WIDTH, screenSize.HEIGHT);
        renderLayer.showLogs(false);
        final Color topColor = new Color(70 / 255.0f, 168 / 255.0f, 255 / 255.0f, 1);
        final Color bottomColor = new Color(53 / 255.0f, 255 / 255.0f, 205 / 255.0f, 1);
        renderLayer.setColor(topColor, topColor, bottomColor, bottomColor);

        pool = new ObjectPool();
        initGameObjects();

        tileCreator = new TileCreator(HORIZONTAL_TILE_COUNT, VERTICAL_TILE_COUNT, pool);
        tiles = tileCreator.createTiles();
        tileSprites = new ArrayList<TileSprite>(HORIZONTAL_TILE_COUNT * VERTICAL_TILE_COUNT);

        createTiles();
    }

    private void initGameObjects() {
        // init objects from pool
        try {
            backgroundSprite = new Sprite(assetsLoader.getBackgroundTextureRegion(), 0, 0, screenSize.WIDTH, screenSize.HEIGHT);
            renderLayer.addSprite(backgroundSprite, true, BACKGROUND_LAYER);

            character = new CharacterSprite(assetsLoader.getCharacterRightAnimation(), screenSize.WIDTH - 100 - CHARACTER_X_OFFSET, CHARACTER_Y_POSITION, 100, CHARACTER_HEIGHT);
            character.setRightAnimation(assetsLoader.getCharacterRightAnimation());
            character.setLeftAnimation(assetsLoader.getCharacterLeftAnimation());
            character.changeAnimation(CharacterSprite.FlipDirection.RIGHT);
            character.setState(AnimatedSprite.AnimationState.PLAY);

            characterLeftTween = new TweenAnimation(screenSize.WIDTH - 100 - CHARACTER_X_OFFSET, CHARACTER_Y_POSITION,
                    CHARACTER_X_OFFSET, CHARACTER_Y_POSITION, CHARACTER_TWEEN_SPEED);
            characterRightTween = new TweenAnimation(CHARACTER_X_OFFSET, CHARACTER_Y_POSITION, screenSize.WIDTH - 100 - CHARACTER_X_OFFSET,
                    CHARACTER_Y_POSITION, CHARACTER_TWEEN_SPEED);
            if (character.getTweenAnimation() == null) {
                characterTween = new TweenAnimation();
                character.setTweenAnimation(characterTween);
            } else {
                characterTween = character.getTweenAnimation();
            }
            animator.createAnimation(characterTween);

            renderLayer.addAnimatedSprite(character, true, CHARACTER_LAYER);

            pool.create(TileSprite.class, HORIZONTAL_TILE_COUNT * VERTICAL_TILE_COUNT);
            pool.create(TileEntity.class, HORIZONTAL_TILE_COUNT * VERTICAL_TILE_COUNT);
            pool.create(GoldSprite.class, 1);

            goldBonusRandom = new AccelerationRandom(10, 50, 1);
            binaryRandom = new BinaryRandom();

            distanceText = new TextSprite("0m", gameFont, 50, 30);
            renderLayer.addTextSprite(distanceText, TEXT_LAYER);
            moneyText = new TextSprite("0", gameFont, screenSize.WIDTH - FontUtils.getFontWidth(gameFont, "0") - 50, 30);
            renderLayer.addTextSprite(moneyText, TEXT_LAYER);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTiles() {
        int i = 0;
        for (int y = 0; y < VERTICAL_TILE_COUNT; y++) {
            for (int x = 0; x < HORIZONTAL_TILE_COUNT; x++) {
                try {
                    TileSprite tileSprite;
                    final float currentYPosition = screenSize.HEIGHT - (y + 1) * TILE_HEIGHT;
                    if (x == 0) {
                        final float leftXPosition = - TILE_BEVEL_SIZE * ((screenSize.HEIGHT - currentYPosition) / TILE_HEIGHT);
                        tileSprite = TileSprite.createTileSprite(leftXPosition, screenSize.HEIGHT - TILE_HEIGHT - y * TILE_HEIGHT, tiles.get(i).getType(),
                                TileEntity.Position.LEFT, pool, assetsLoader, TILE_WIDTH, TILE_HEIGHT);
                    } else {
                        final float rightXPosition = (screenSize.WIDTH - TILE_WIDTH) + TILE_BEVEL_SIZE * ((screenSize.HEIGHT - currentYPosition) / TILE_HEIGHT);
                        tileSprite = TileSprite.createTileSprite(rightXPosition, screenSize.HEIGHT - TILE_HEIGHT - y * TILE_HEIGHT, tiles.get(i).getType(),
                                TileEntity.Position.RIGHT, pool, assetsLoader, TILE_WIDTH, TILE_HEIGHT);
                    }
                    renderLayer.addSprite(tileSprite, true, TILE_LAYER);
                    tileSprites.add(tileSprite);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
        }
        if (tiles.get(0).getType() == TileEntity.Type.WHITE) {
            setCharacterPositionLeft();
        } else {
            setCharacterPositionRight();
        }
    }

    private void setCharacterPositionLeft() {
        characterTween.copyFromAnimation(characterRightTween);
        character.changeAnimation(CharacterSprite.FlipDirection.LEFT);
    }

    private void setCharacterPositionRight() {
        characterTween.copyFromAnimation(characterLeftTween);
        character.changeAnimation(CharacterSprite.FlipDirection.RIGHT);
    }

    private void moveTiles(final float delta) {
        final float moveParam = TILE_SPEED * delta;
        tilesStartYPosition = tileSprites.get(0).getY();
        tilesStartYPosition += moveParam;
        if (tilesStartYPosition >= screenSize.HEIGHT) {
            tilesStartYPosition = screenSize.HEIGHT;
            int i = 0;
            for (int y = 0; y < VERTICAL_TILE_COUNT; y++) {
                for (int x = 0; x < HORIZONTAL_TILE_COUNT; x++) {
                    final TileSprite tileSprite = tileSprites.get(i);
                    tileSprite.setY(tilesStartYPosition - y * TILE_HEIGHT);
                    i++;
                }
            }
            /*if (getTileTypeUnderDuck().equals(TileEntity.Type.BLACK)) {//TODO условие проигрыша
                if (screenChangeListener != null) {
                    //screenChangeListener.onScreenChange(ScreenType.LOOSE_SCREEN);
                }
            }*/

            createNewPair();
        } else {
            int i = 0;
            for (int y = 0; y < VERTICAL_TILE_COUNT; y++) {
                for (int x = 0; x < HORIZONTAL_TILE_COUNT; x++) {
                    final TileSprite tileSprite = tileSprites.get(i);
                    final float currentYPosition = tilesStartYPosition - y * TILE_HEIGHT;
                    final boolean isLeft;
                    if (x == 0) {
                        final float tileLeftXPosition = - TILE_BEVEL_SIZE * ((screenSize.HEIGHT - currentYPosition) / TILE_HEIGHT);
                        tileSprite.setX(tileLeftXPosition);
                        isLeft = true;
                    } else {
                        final float tileRightXPosition = (screenSize.WIDTH - TILE_WIDTH) + TILE_BEVEL_SIZE * ((screenSize.HEIGHT - currentYPosition) / TILE_HEIGHT);
                        tileSprite.setX(tileRightXPosition);
                        isLeft = false;
                    }
                    tileSprite.setY(currentYPosition);
                    if (tileSprite.hasBonus()) { // TODO бонусы
                        final Sprite bonus = tileSprite.getBonus();
                        final float bonusXPos;
                        if (isLeft) {
                            bonusXPos = tileSprite.getX() + tileSprite.getWidth();
                        } else {
                            bonusXPos = tileSprite.getX() - bonus.getWidth();
                        }
                        final float bonusYPos = PositionUtils.getYFromParentCenter(bonus, tileSprite);
                        bonus.setX(bonusXPos);
                        bonus.setY(bonusYPos);
                    }
                    i++;
                }
            }
            final TileSprite tileSpriteUnderCharacter = getTileSpriteUnderCharacter();
            if (tileSpriteUnderCharacter != null) {
                Gdx.app.log("TileType", tileSpriteUnderCharacter.getType().name());
                if (tileSpriteUnderCharacter.hasBonus()) { // TODO услокие бонусов
                    onBonusCatched(tileSpriteUnderCharacter);
                }
            }
        }
    }

    private void onBonusCatched(final TileSprite tileWithBonus) {
        final Sprite bonus = tileWithBonus.getBonus();
        try {
            final GoldSprite goldSprite = (GoldSprite) pool.get(GoldSprite.class);
            goldSprite.setTextureRegion(assetsLoader.getGoldTextureRegion());
            goldSprite.setX(bonus.getX());
            goldSprite.setY(bonus.getY());
            goldSprite.setWidth(bonus.getWidth());
            goldSprite.setHeight(bonus.getHeight());
            final TweenAnimation goldTweenAnimation;
            if (goldSprite.getTweenAnimation() != null) {
                goldTweenAnimation = goldSprite.getTweenAnimation();
            } else {
                goldTweenAnimation = new TweenAnimation();
                goldSprite.setTweenAnimation(goldTweenAnimation);
            }
            animator.createAnimation(goldTweenAnimation);
            goldTweenAnimation.restart(goldSprite.getX(), goldSprite.getY(), moneyText.getX(), moneyText.getY(), GOLD_TWEEN_SPEED);
            goldTweenAnimation.start();
            goldTweenAnimation.setOnAnimationFinishListener(new TweenAnimation.OnAnimationFinishListener() {
                @Override
                public void onAnimationFinish(final float destX, final float destY) {
                    moneyCount ++;
                    moneyText.setText(moneyCount + "");
                    moneyText.setX(screenSize.WIDTH - FontUtils.getFontWidth(gameFont, moneyCount + "") - 50);
                    renderLayer.removeSprite(goldSprite);
                    pool.release(goldSprite);
                }
            });
            renderLayer.addSprite(goldSprite, true, TILE_LAYER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.release(bonus);
        renderLayer.removeSprite(bonus);
        tileWithBonus.setBonus(null);
    }

    private void createNewPair() {
        distance ++;
        distanceText.setText(distance + "m");

        tiles = tileCreator.setNewPair(tiles);

        final TileSprite leftSprite = tileSprites.get(0);
        if (leftSprite.hasBonus()) {
            pool.release(leftSprite.getBonus());
            renderLayer.removeSprite(leftSprite.getBonus());
            leftSprite.setBonus(null);
        }
        pool.release(leftSprite);
        renderLayer.removeSprite(leftSprite);
        tileSprites.remove(0);

        final TileSprite rightSprite = tileSprites.get(0);
        if (rightSprite.hasBonus()) {
            pool.release(rightSprite.getBonus());
            renderLayer.removeSprite(rightSprite.getBonus());
            rightSprite.setBonus(null);
        }
        pool.release(rightSprite);
        renderLayer.removeSprite(rightSprite);
        tileSprites.remove(0);

        try {
            final TileEntity newLeftTileEntity = tiles.get(tiles.size() - 2);
            final TileSprite newLeftTileSprite = TileSprite.createTileSprite(0, screenSize.HEIGHT - TILE_HEIGHT - (VERTICAL_TILE_COUNT - 1) * TILE_HEIGHT,
                    newLeftTileEntity.getType(), TileEntity.Position.LEFT, pool, assetsLoader, TILE_WIDTH, TILE_HEIGHT);
            renderLayer.addSprite(newLeftTileSprite, true, TILE_LAYER);
            tileSprites.add(newLeftTileSprite);

            final TileEntity newRightTileEntity = tiles.get(tiles.size() - 1);
            final TileSprite newRightTileSprite = TileSprite.createTileSprite(screenSize.WIDTH - TILE_WIDTH,
                    screenSize.HEIGHT - TILE_HEIGHT - (VERTICAL_TILE_COUNT - 1) * TILE_HEIGHT, newRightTileEntity.getType(), TileEntity.Position.RIGHT,
                    pool, assetsLoader, TILE_WIDTH, TILE_HEIGHT);
            renderLayer.addSprite(newRightTileSprite, true, TILE_LAYER);
            tileSprites.add(newRightTileSprite);

            // CREATE GOLD BONUS
            if (goldBonusRandom.getRandom()) {
                final TileSprite notEmptyTileSprite;
                final boolean isLeft;
                if (newLeftTileEntity.getType().equals(TileEntity.Type.WHITE) && newRightTileEntity.getType().equals(TileEntity.Type.WHITE)) {
                    if (binaryRandom.getRandom()) {
                        notEmptyTileSprite = newLeftTileSprite;
                        isLeft = false;
                    } else {
                        notEmptyTileSprite = newRightTileSprite;
                        isLeft = true;
                    }
                } else {
                    if (newRightTileEntity.getType().equals(TileEntity.Type.WHITE)) {
                        notEmptyTileSprite = newRightTileSprite;
                        isLeft = true;
                    } else {
                        notEmptyTileSprite = newLeftTileSprite;
                        isLeft = false;
                    }
                }
                final GoldSprite goldSprite = (GoldSprite) pool.get(GoldSprite.class);
                goldSprite.setTextureRegion(assetsLoader.getGoldTextureRegion());
                goldSprite.setWidth(GOLD_SIZE);
                goldSprite.setHeight(GOLD_SIZE);
                final float goldYPos = notEmptyTileSprite.getY() + notEmptyTileSprite.getHeight() * 0.5f - goldSprite.getHeight() * 0.5f;
                goldSprite.setY(goldYPos);
                if (isLeft) {
                    goldSprite.setX(notEmptyTileSprite.getX() + notEmptyTileSprite.getWidth());
                } else {
                    goldSprite.setX(notEmptyTileSprite.getX() - goldSprite.getWidth());
                }
                notEmptyTileSprite.setBonus(goldSprite);
                renderLayer.addSprite(goldSprite, true, BONUS_LAYER);
            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void rotateCharacter() {
        float rotateAngle = 0;
        final float halfWidth = screenSize.WIDTH / 2;
        if (character.getX() + character.getWidth() / 2 <= halfWidth) {
            rotateAngle = - CHARACTER_MAX_ROTATION_ANGLE * ((halfWidth - character.getX()) / halfWidth);
        } else {
            rotateAngle = CHARACTER_MAX_ROTATION_ANGLE * (((character.getX() + character.getWidth()) - halfWidth) / halfWidth);
        }
        character.setRotateAngle(rotateAngle);
    }

    private void moveCharacterUpDown() {
        final float halfWidth = screenSize.WIDTH / 2;
        final float distance = halfWidth - CHARACTER_X_OFFSET;
        final float halfCharacterWidth = character.getWidth() / 2;
        if (character.getX() + halfCharacterWidth <= halfWidth) {
            final float offset = ((character.getX() - CHARACTER_X_OFFSET) / distance) * CHARACTER_MAX_UP;
            characterTween.setY(CHARACTER_Y_POSITION - offset);
        } else {
            characterTween.setY(CHARACTER_Y_POSITION - CHARACTER_MAX_UP + ((character.getX() + character.getWidth() - halfWidth) / distance) * CHARACTER_MAX_UP);
        }
    }

    boolean characterIsReachedLeftSprite() {
        if (character.getX() <= CHARACTER_X_OFFSET + 1) {
            return true;
        }
        return false;
    }

    boolean characterIsReachedRightSprite() {
        if (character.getX() + character.getWidth() >= screenSize.WIDTH - CHARACTER_X_OFFSET - 1) {
            return true;
        }
        return false;
    }

    private TileSprite getTileSpriteUnderCharacter() {
        final float characterMiddle = character.getY() + CHARACTER_HEIGHT * 0.5f;
        for (int i = 0, length = tileSprites.size(); i < length; i++) {
            final TileSprite tileSprite = tileSprites.get(i);
            if (characterIsReachedLeftSprite()) {
                if (tileSprite.getPosition() == TileEntity.Position.LEFT) {
                    if (tileSprite.getY() <= characterMiddle && tileSprite.getY() + TILE_HEIGHT >= characterMiddle) {
                        return tileSprite;
                    }
                }
            }
            if (characterIsReachedRightSprite()) {
                if (tileSprite.getPosition() == TileEntity.Position.RIGHT) {
                    if (tileSprite.getY() <= characterMiddle && tileSprite.getY() + TILE_HEIGHT >= characterMiddle) {
                        return tileSprite;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        renderLayer.render(delta);
        animator.update(delta);
        moveTiles(delta);
        rotateCharacter();
        //moveCharacterUpDown();
        fps.log();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onTouchDown(float xPos, float yPos) {
        if (character.getFlipDirection() == CharacterSprite.FlipDirection.LEFT) {
            character.changeAnimation(CharacterSprite.FlipDirection.RIGHT);
            characterTween.copyFromAnimation(characterRightTween);
            characterTween.start();
        } else {
            character.changeAnimation(CharacterSprite.FlipDirection.LEFT);
            characterTween.copyFromAnimation(characterLeftTween);
            characterTween.start();
        }
    }

    @Override
    public void onTouchUp() {

    }
}
