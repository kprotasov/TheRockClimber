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
import com.capcorn.game.engine.utils.ProbabilityRandom;
import com.capcorn.games.therockclimber.creator.BonusCreator;
import com.capcorn.games.therockclimber.creator.TileCreator;
import com.capcorn.games.therockclimber.effects.CameraShakeEffect;
import com.capcorn.games.therockclimber.entity.TileEntity;
import com.capcorn.games.therockclimber.font.DistanceFontCreator;
import com.capcorn.games.therockclimber.font.FontUtils;
import com.capcorn.games.therockclimber.graphics.AssetsLoader;
import com.capcorn.games.therockclimber.input.InputHandler;
import com.capcorn.games.therockclimber.input.OnTouchListener;
import com.capcorn.games.therockclimber.settings.Settings;
import com.capcorn.games.therockclimber.settings.store.BestScoreStore;
import com.capcorn.games.therockclimber.sprite.BonusSprite;
import com.capcorn.games.therockclimber.sprite.BonusType;
import com.capcorn.games.therockclimber.sprite.BrillianceSprite;
import com.capcorn.games.therockclimber.sprite.CharacterSprite;
import com.capcorn.games.therockclimber.sprite.ClickToStartTextSprite;
import com.capcorn.games.therockclimber.sprite.DirectedAnimatedSprite;
import com.capcorn.games.therockclimber.sprite.GoldSprite;
import com.capcorn.games.therockclimber.sprite.LoosingGameDialog;
import com.capcorn.games.therockclimber.sprite.StoneSprite;
import com.capcorn.games.therockclimber.sprite.TileSprite;

import java.util.ArrayList;

/**
 * User: kprotasov
 * Date: 17.06.2017
 * Time: 17:00
 */

public class GameScreen implements Screen, OnTouchListener, LoosingGameDialog.OnLoosingDialogActionListener, BonusCreator.OnBonusCahcedListener{

    private static final String CLICK_TO_START_TEXT = "click to start";

    private static final float CHARACTER_MAX_UP = 50;
    private static final int CHARACTER_MAX_ROTATION_ANGLE = 20;
    private static final int TILE_WIDTH = 100;
    private static final int TILE_BEVEL_SIZE = 20;
    private static final int TILE_HEIGHT = 240;
    private static final int TILE_SPEED = 800;
    private static final float CHARACTER_TWEEN_SPEED = 2000.0f;
    //private static final float GOLD_TWEEN_SPEED = 1500.0f;
    private static int HORIZONTAL_TILE_COUNT;
    private static int VERTICAL_TILE_COUNT;
    private static float CHARACTER_Y_POSITION;
    private static final float CHARACTER_X_OFFSET = 50;
    //private static final float GOLD_SIZE = 50;
    //private static final float BRILLIANCE_SIZE = 50;
    private static final float CHARACTER_HEIGHT = 196;
    private static final float CHARACTER_FAIL_SPEED = 800.0f;

    private boolean gameIsStarted = false;
    private boolean isFailedGame = false;

    private final AssetsLoader assetsLoader;
    private final RenderLayer renderLayer;
    private final Settings.ScreenSize screenSize;
    private final OrthographicCamera camera;
    private final FPSLogger fps;
    private final ObjectPool pool;
    private final Animator animator;
    private final BitmapFont gameFont;
    private CameraShakeEffect cameraShakeEffect;
    private BestScoreStore bestScoreStore;
    private ClickToStartTextSprite clickToStartTextSprite;
    private BonusCreator bonusCreator;

    private TextSprite distanceText;
    private int distance = 0;
    private TextSprite moneyText;
    private int moneyCount = 0;

    private TweenAnimation characterLeftTween;
    private TweenAnimation characterRightTween;
    private TweenAnimation characterTween;
    private CharacterSprite character;
    private Sprite backgroundSprite;
    private LoosingGameDialog loosingGameDialog;

    private final TileCreator tileCreator;
    private ArrayList<TileEntity> tiles;
    private final ArrayList<TileSprite> tileSprites;
    private float tilesStartYPosition = TILE_HEIGHT;

    private StoneSprite createdStoneSprite;

    private BinaryRandom binaryRandom;
    private ProbabilityRandom goldBonusRandom;
    private ProbabilityRandom brillianceBonusRandom;
    private ProbabilityRandom stoneRandom;

    private static final int BACKGROUND_LAYER = 0;
    private static final int TILE_LAYER = 1;
    private static final int BONUS_LAYER = 2;
    private static final int CHARACTER_LAYER = 3;
    private static final int SPRITES_LAYER = 4;
    private static final int TEXT_LAYER = 5;
    private static final int LOOSING_GAME_DIALOG_LAYER = 6;
    private static final int CLICK_TO_START_LAYER = 7;

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
        renderLayer.showLogs(true);
        final Color topColor = new Color(70 / 255.0f, 168 / 255.0f, 255 / 255.0f, 1);
        final Color bottomColor = new Color(53 / 255.0f, 255 / 255.0f, 205 / 255.0f, 1);
        renderLayer.setColor(topColor, topColor, bottomColor, bottomColor);

        bestScoreStore = new BestScoreStore();

        pool = new ObjectPool();

        tileCreator = new TileCreator(HORIZONTAL_TILE_COUNT, VERTICAL_TILE_COUNT, pool);
        tiles = tileCreator.createTiles();
        tileSprites = new ArrayList<TileSprite>(HORIZONTAL_TILE_COUNT * VERTICAL_TILE_COUNT);

        bonusCreator = new BonusCreator(pool, renderLayer, assetsLoader, animator, BONUS_LAYER);

        initGameObjects();
        createDefaultTiles();
    }

    private void initGameObjects() {
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

            cameraShakeEffect = new CameraShakeEffect(camera.position.x, camera.position.y);

            renderLayer.addAnimatedSprite(character, true, CHARACTER_LAYER);

            pool.create(TileSprite.class, HORIZONTAL_TILE_COUNT * VERTICAL_TILE_COUNT);
            pool.create(TileEntity.class, HORIZONTAL_TILE_COUNT * VERTICAL_TILE_COUNT);
            pool.create(BonusSprite.class, 1);
            pool.create(GoldSprite.class, 1);
            pool.create(BrillianceSprite.class, 1);
            pool.create(StoneSprite.class, 1);

            goldBonusRandom = new ProbabilityRandom(15);
            brillianceBonusRandom = new ProbabilityRandom(3);
            stoneRandom = new ProbabilityRandom(15);
            binaryRandom = new BinaryRandom();

            distanceText = new TextSprite("0m", gameFont, 50, 30);
            renderLayer.addTextSprite(distanceText, TEXT_LAYER);
            moneyText = new TextSprite("0", gameFont, screenSize.WIDTH - FontUtils.getFontWidth(gameFont, "0") - 50, 30);
            renderLayer.addTextSprite(moneyText, TEXT_LAYER);

            loosingGameDialog = new LoosingGameDialog(assetsLoader, this);

            clickToStartTextSprite = new ClickToStartTextSprite(CLICK_TO_START_TEXT, screenSize);
            renderLayer.addTextSprite(clickToStartTextSprite, CLICK_TO_START_LAYER);

            bonusCreator.setOnBonusCachedListener(this);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDefaultTiles() {
        int i = 0;
        for (int y = 0; y < VERTICAL_TILE_COUNT; y++) {
            for (int x = 0; x < HORIZONTAL_TILE_COUNT; x++) {
                try {
                    final TileSprite tileSprite;
                    final float currentYPosition = screenSize.HEIGHT - (y + 1) * TILE_HEIGHT;
                    if (x == 0) {
                        final float leftXPosition = - TILE_BEVEL_SIZE * ((screenSize.HEIGHT - currentYPosition) / TILE_HEIGHT);
                        tileSprite = new TileSprite();
                        tileSprite.setTextureRegion(assetsLoader.getWhiteTileRightTextureRegion());
                        tileSprite.setX(leftXPosition);
                        tileSprite.setY(screenSize.HEIGHT - TILE_HEIGHT - y * TILE_HEIGHT);
                        tileSprite.setType(TileEntity.Type.WHITE);
                        tileSprite.setPosition(TileEntity.Position.LEFT);
                        tileSprite.setWidth(TILE_WIDTH);
                        tileSprite.setHeight(TILE_HEIGHT);
                    } else {
                        final float rightXPosition = (screenSize.WIDTH - TILE_WIDTH) + TILE_BEVEL_SIZE * ((screenSize.HEIGHT - currentYPosition) / TILE_HEIGHT);
                        tileSprite = new TileSprite();
                        tileSprite.setTextureRegion(assetsLoader.getWhiteTileLeftTextureRegion());
                        tileSprite.setX(rightXPosition);
                        tileSprite.setY(screenSize.HEIGHT - TILE_HEIGHT - y * TILE_HEIGHT);
                        tileSprite.setType(TileEntity.Type.WHITE);
                        tileSprite.setPosition(TileEntity.Position.RIGHT);
                        tileSprite.setWidth(TILE_WIDTH);
                        tileSprite.setHeight(TILE_HEIGHT);
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

    private void createStone(final TileEntity leftTileEntity, final TileEntity rightTileEntity, final float tileYPosition) throws Exception{
        if (leftTileEntity.getType().equals(TileEntity.Type.WHITE)
                && rightTileEntity.getType().equals(TileEntity.Type.WHITE)) {
            return;
        }
        final StoneSprite stoneSprite = (StoneSprite) pool.get(StoneSprite.class);
        createdStoneSprite = stoneSprite;
        stoneSprite.setWidth(100);
        stoneSprite.setHeight(100);
        stoneSprite.setLeftAnimation(assetsLoader.getStoneLeftAnimation());
        stoneSprite.setRightAnimation(assetsLoader.getStoneRightAnimation());
        final TweenAnimation stoneSpriteAnimation;
        if (stoneSprite.getTweenAnimation() != null) {
            stoneSpriteAnimation = stoneSprite.getTweenAnimation();
        } else {
            stoneSpriteAnimation = new TweenAnimation();
            stoneSprite.setTweenAnimation(stoneSpriteAnimation);
        }
        if (leftTileEntity.getType().equals(TileEntity.Type.BLACK)) { // as right
            stoneSprite.changeAnimation(DirectedAnimatedSprite.FlipDirection.RIGHT);
            stoneSpriteAnimation.restart(0, tileYPosition, 25, screenSize.HEIGHT / 2, TILE_SPEED + 100);
        } else {
            stoneSprite.changeAnimation(DirectedAnimatedSprite.FlipDirection.LEFT);
            stoneSpriteAnimation.restart(screenSize.WIDTH - stoneSprite.getWidth(), tileYPosition,
                    screenSize.WIDTH - stoneSprite.getWidth() - 25, screenSize.HEIGHT / 2, TILE_SPEED + 100);
        }
        stoneSpriteAnimation.start();
        animator.createAnimation(stoneSpriteAnimation);

        final float destinationX = screenSize.WIDTH / 2 - stoneSprite.getWidth() / 2;
        final float destinationY = screenSize.HEIGHT;
        stoneSpriteAnimation.setOnAnimationFinishListener(new TweenAnimation.OnAnimationFinishListener() {
            @Override
            public void onAnimationFinish(final float destX, final float destY) {
                if (destX == destinationX && destY == destinationY) {
                    pool.release(stoneSprite);
                    renderLayer.removeSprite(stoneSprite);
                    createdStoneSprite = null;
                } else {
                    stoneSpriteAnimation.restart(stoneSprite.getX(), stoneSprite.getY(),
                            destinationX, destinationY, 600.0f);
                    stoneSpriteAnimation.start();
                }
            }
        });

        stoneSprite.setState(AnimatedSprite.AnimationState.PLAY);
        renderLayer.addAnimatedSprite(stoneSprite, true, SPRITES_LAYER);
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
                if (tileSpriteUnderCharacter.hasBonus()) { // TODO услокие бонусов
                    //onBonusCatched(tileSpriteUnderCharacter);
                    bonusCreator.onBonusCatched(tileSpriteUnderCharacter, moneyText.getX(), moneyText.getY());
                }
                if (tileSpriteUnderCharacter.getType().equals(TileEntity.Type.BLACK)) {
                    if (!isFailedGame) {
                        isFailedGame = true;
                        onGameFail();
                    }
                }
            }
        }
    }

    private void onGameFail() {
        final TweenAnimation failTweenAnimation;
        if (character.getTweenAnimation() == null) {
            failTweenAnimation = new TweenAnimation();
            character.setTweenAnimation(failTweenAnimation);
        } else {
            failTweenAnimation = character.getTweenAnimation();
        }
        animator.createAnimation(failTweenAnimation);
        failTweenAnimation.restart(character.getX(), character.getY(), screenSize.WIDTH / 2 - character.getWidth() / 2, screenSize.HEIGHT, CHARACTER_FAIL_SPEED);
        failTweenAnimation.start();

        cameraShakeEffect.shake(5.0f, 0.15f);


        final int bestStoredScore = bestScoreStore.getBestScore();
        if (distance > bestStoredScore) {
            bestScoreStore.setBestScore(distance);
        }

        createLoosingGameDialog();
        gameIsStarted = false;
    }

    private void createLoosingGameDialog() {
        loosingGameDialog.setWidth(450);
        loosingGameDialog.setHeight(275);
        loosingGameDialog.setX(screenSize.WIDTH / 2 - loosingGameDialog.getWidth() / 2);
        loosingGameDialog.setY(screenSize.HEIGHT / 2 - loosingGameDialog.getHeight() / 2);

        loosingGameDialog.initDialog(distance, bestScoreStore.getBestScore());
        loosingGameDialog.addOnScreen(renderLayer, LOOSING_GAME_DIALOG_LAYER);
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
            final float tileYPosition = screenSize.HEIGHT - TILE_HEIGHT - (VERTICAL_TILE_COUNT - 1) * TILE_HEIGHT;
            final TileEntity newLeftTileEntity = tiles.get(tiles.size() - 2);
            final TileSprite newLeftTileSprite = TileSprite.createTileSprite(0, tileYPosition,
                    newLeftTileEntity.getType(), TileEntity.Position.LEFT, pool, assetsLoader, TILE_WIDTH, TILE_HEIGHT);
            renderLayer.addSprite(newLeftTileSprite, true, TILE_LAYER);
            tileSprites.add(newLeftTileSprite);

            final TileEntity newRightTileEntity = tiles.get(tiles.size() - 1);
            final TileSprite newRightTileSprite = TileSprite.createTileSprite(screenSize.WIDTH - TILE_WIDTH,
                    tileYPosition, newRightTileEntity.getType(), TileEntity.Position.RIGHT,
                    pool, assetsLoader, TILE_WIDTH, TILE_HEIGHT);
            renderLayer.addSprite(newRightTileSprite, true, TILE_LAYER);
            tileSprites.add(newRightTileSprite);

            // CREATE BONUS
            if (goldBonusRandom.get()) {
                bonusCreator.createBonus(BonusType.GOLD, newLeftTileSprite, newRightTileSprite);
            }
            if (brillianceBonusRandom.get()) {
                bonusCreator.createBonus(BonusType.BRILLIANCE, newLeftTileSprite, newRightTileSprite);
            }
            if (stoneRandom.get() && createdStoneSprite == null) { // за один раз только один камень
                createStone(newLeftTileEntity, newRightTileEntity, tileYPosition);
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

    private boolean characterIsReachedLeftSprite() {
        if (character.getX() <= CHARACTER_X_OFFSET + 1) {
            return true;
        }
        return false;
    }

    private boolean characterIsReachedRightSprite() {
        if (character.getX() + character.getWidth() >= screenSize.WIDTH - CHARACTER_X_OFFSET - 1) {
            return true;
        }
        return false;
    }

    private TileSprite getTileSpriteUnderCharacter() {
        final float partOfCharacter = CHARACTER_HEIGHT * 0.3f;
        final float characterStart = character.getY() + partOfCharacter;
        final float characterEnd = character.getY() + character.getHeight() - partOfCharacter;
        for (int i = 0, length = tileSprites.size(); i < length; i++) {
            final TileSprite tileSprite = tileSprites.get(i);
            final float tileStart = tileSprite.getY();
            final float tileEnd = tileSprite.getY() + tileSprite.getHeight();
            if (characterIsReachedLeftSprite()) {
                if (tileSprite.getPosition() == TileEntity.Position.LEFT) {
                    if (characterEnd < tileEnd && characterStart > tileStart) {
                        return tileSprite;
                    }
                }
            }
            if (characterIsReachedRightSprite()) {
                if (tileSprite.getPosition() == TileEntity.Position.RIGHT) {
                    if (characterEnd < tileEnd && characterStart > tileStart) {
                        return tileSprite;
                    }
                }
            }
        }
        return null;
    }

    private void restartGame() {
        for (int i = 0, length = tileSprites.size(); i < length; i++) {
            BonusSprite bonus = tileSprites.get(i).getBonus();
            if (bonus != null) {
                renderLayer.removeSprite(bonus);
                bonus = null;
                tileSprites.get(i).setBonus(null);
            }
            renderLayer.removeSprite(tileSprites.get(i));
            pool.release(tileSprites.get(i));
        }
        tileSprites.clear();
    }

    private void clearProgress() {
        distance = 0;
        moneyCount = 0;
        distanceText.setText(distance + "m");
        moneyText.setText(moneyCount + "");
        moneyText.setX(screenSize.WIDTH - FontUtils.getFontWidth(gameFont, moneyCount + "") - 50);
    }

    private void checkCharacterIsDie() {
        if (!isFailedGame && createdStoneSprite != null) {
            final float stoneX = createdStoneSprite.getX() + createdStoneSprite.getWidth() / 2;
            final float stoneY = createdStoneSprite.getY() + createdStoneSprite.getHeight() / 2;
            if (stoneX >= character.getX() && stoneX <= character.getX() + character.getWidth() &&
                    stoneY >= character.getY() && stoneY <= character.getY() + character.getHeight()) {
                isFailedGame = true;
                onGameFail();
            }
            //if (createdStoneSprite.getX())
            // check intercept stone and character
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(final float delta) {
        cameraShakeEffect.update(delta, camera);

        renderLayer.render(delta);
        animator.update(delta);

        if (!isFailedGame && gameIsStarted) {
            checkCharacterIsDie();
            moveTiles(delta);
        }
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
    public void onTouchDown(final float xPos, final float yPos) {
        final float relativeClickX = xPos * screenSize.SCALE_PARAM;
        final float relativeClickY = yPos * screenSize.SCALE_PARAM;
        if (loosingGameDialog.isShown()) {
            loosingGameDialog.checkLeftButtonClickedAndAction(relativeClickX, relativeClickY);
            loosingGameDialog.checkRightButtonClickedAndAction(relativeClickX, relativeClickY);
        } else {
            if (!isFailedGame) {
                if (gameIsStarted) {
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
                if (!gameIsStarted) {
                    gameIsStarted = true;
                    renderLayer.removeSprite(clickToStartTextSprite);
                }
            }
        }
    }

    @Override
    public void onTouchUp() {

    }

    @Override
    public void onLeftAction() {
        renderLayer.addTextSprite(clickToStartTextSprite, CLICK_TO_START_LAYER);
        loosingGameDialog.removeFromScreen(renderLayer);

        isFailedGame = false;
        gameIsStarted = false;

        restartGame();
        clearProgress();
        createDefaultTiles();
    }

    @Override
    public void onRightAction() {
        isFailedGame = false;
        loosingGameDialog.removeFromScreen(renderLayer);
        renderLayer.addTextSprite(clickToStartTextSprite, CLICK_TO_START_LAYER);

        isFailedGame = false;
        gameIsStarted = false;

        restartGame();
        createDefaultTiles();
    }

    @Override
    public void onBonusCached(BonusSprite bonusSprite) {
        moneyCount += bonusSprite.getSelfCoast();
        moneyText.setText(moneyCount + "");
        moneyText.setX(screenSize.WIDTH - FontUtils.getFontWidth(gameFont, moneyCount + "") - 50);
        renderLayer.removeSprite(bonusSprite);
        pool.release(bonusSprite);
    }
}
