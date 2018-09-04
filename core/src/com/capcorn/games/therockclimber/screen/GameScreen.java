package com.capcorn.games.therockclimber.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.capcorn.game.engine.animation.Animator;
import com.capcorn.game.engine.animation.TweenAnimation;
import com.capcorn.game.engine.core.RenderLayer;
import com.capcorn.game.engine.pool.ObjectPool;
import com.capcorn.game.engine.sprite.AnimatedSprite;
import com.capcorn.game.engine.sprite.Sprite;
import com.capcorn.game.engine.sprite.TextSprite;
import com.capcorn.game.engine.utils.BinaryRandom;
import com.capcorn.game.engine.utils.PositionUtils;
import com.capcorn.game.engine.utils.ProbabilityRandom;
import com.capcorn.games.therockclimber.OnShowAdListener;
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
import com.capcorn.games.therockclimber.settings.store.MoneyStore;
import com.capcorn.games.therockclimber.settings.store.SettingsStore;
import com.capcorn.games.therockclimber.settings.store.ShowedRewardedVideoStore;
import com.capcorn.games.therockclimber.sound.MusicManager;
import com.capcorn.games.therockclimber.sprite.FogBackgroundSprite;
import com.capcorn.games.therockclimber.sprite.bonus.BonusSprite;
import com.capcorn.games.therockclimber.sprite.bonus.BonusType;
import com.capcorn.games.therockclimber.sprite.bonus.BrillianceSprite;
import com.capcorn.games.therockclimber.sprite.CharacterSprite;
import com.capcorn.games.therockclimber.sprite.ClickToStartTextSprite;
import com.capcorn.games.therockclimber.sprite.DirectedAnimatedSprite;
import com.capcorn.games.therockclimber.sprite.bonus.GoldSprite;
import com.capcorn.games.therockclimber.sprite.dialogs.LoosingGameDialog;
import com.capcorn.games.therockclimber.sprite.bonus.SnowflakeSprite;
import com.capcorn.games.therockclimber.sprite.StoneSprite;
import com.capcorn.games.therockclimber.sprite.TileSprite;
import com.capcorn.settings.StringsResources;

import java.util.ArrayList;

/**
 * User: kprotasov
 * Date: 17.06.2017
 * Time: 17:00
 */

public class GameScreen implements Screen, OnTouchListener, LoosingGameDialog.OnLoosingDialogActionListener, BonusCreator.OnBonusCahcedListener {

    enum GameState {
        PAUSE,
        PLAY;
    }

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
    private static final int VIBRATION_LENGTH = 100;

    private boolean gameIsStarted = false;
    private boolean isFailedGame = false;

    private final AssetsLoader assetsLoader;
    private final RenderLayer renderLayer;
    private final Settings.ScreenSize screenSize;
    private final OrthographicCamera camera;
    //private final FPSLogger fps;
    private final ObjectPool pool;
    private final Animator animator;
    private final BitmapFont gameFont;
    private CameraShakeEffect cameraShakeEffect;
    private ClickToStartTextSprite clickToStartTextSprite;
    private BonusCreator bonusCreator;
    private Sprite pauseButton;
    private OnShowAdListener onShowAdListener;
    private GameState gameState;
    private boolean isRewardedVideoLoaded = false;
    private FogBackgroundSprite fogBackgroundSprite;
    private MusicManager musicManager;
    private SettingsStore settingsStore;
    private StringsResources stringsResources;

    private BestScoreStore bestScoreStore;
    private MoneyStore moneyStore;
    private ShowedRewardedVideoStore showedRewardedVideoStore;

    private TextSprite distanceText;
    private long distance = 0;
    private static final long MONEY_RIGHT_OFFSET = 100;
    private TextSprite moneyText;
    private GoldSprite moneyIcon;
    private long moneyCount = 0;
    private long currentGameMoneyCount = 0;

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
    private ProbabilityRandom snowflakeProbabilityRandom;
    private ProbabilityRandom stoneRandom;

    private ProbabilityRandom interstitialBinaryRandom;

    // snowflake params
    private int SNOWFLAKE_PROBABILITY = 20;
    private long SNOWFLAKE_START_OFFSET = 30;
    private long SNOWFLAKE_NEXT_OFFSET = 30;
    private long currentSnowflakeOffset = 0;

    // Gold, brilliance rain params
    private final long GOLD_REWARD_BETWEEN_DISTANCE = 100;
    private final long GOLD_REWARD_PERIOD = 20;
    private long goldRewardIncrement = -1;
    private long goldRewardPeriodIncrement = 0;
    private ProbabilityRandom goldDefaultProbabilityRandom = new ProbabilityRandom(15);
    private ProbabilityRandom goldRewardProbabilityRandom = new ProbabilityRandom(100);
    private final long BRILLIANCE_REWARD_BETWEEN_DISTANCE = 500;
    private final long BRILLIANCE_REWARD_PERIOD = 10;
    private long brillianceRewardIncrement = -1;
    private long brillianceRewardPeriodIncrement = 0;
    private ProbabilityRandom brillianceDefaultProbabilityRandom = new ProbabilityRandom(3);
    private ProbabilityRandom brillianceRewardProbabilityRandom = new ProbabilityRandom(100);

    private static final int BACKGROUND_LAYER = 0;
    private static final int TILE_LAYER = 1;
    private static final int BONUS_LAYER = 2;
    private static final int CHARACTER_LAYER = 3;
    private static final int SPRITES_LAYER = 4;
    private static final int TEXT_LAYER = 5;
    private static final int FOG_LAYER = 6;
    private static final int LOOSING_GAME_DIALOG_LAYER = 7;
    private static final int CLICK_TO_START_LAYER = 8;
    private static final int RED_BUTTON_LAYER = 9;
    private static final int PAUSE_BUTTON_LAYER = 10;

    public GameScreen(final AssetsLoader assetsLoader, final OnShowAdListener onShowAdListener, final StringsResources stringsResources) {
        this.assetsLoader = assetsLoader;
        Gdx.input.setInputProcessor(new InputHandler(this));

        this.onShowAdListener = onShowAdListener;

        //fps = new FPSLogger();
        screenSize = Settings.getScreenSize();
        camera = new OrthographicCamera();
        camera.setToOrtho(true, screenSize.WIDTH, screenSize.HEIGHT);

        gameFont = DistanceFontCreator.createFont();

        animator = new Animator();

        HORIZONTAL_TILE_COUNT = 2;
        VERTICAL_TILE_COUNT = (int) screenSize.HEIGHT / TILE_HEIGHT + 2;
        CHARACTER_Y_POSITION = screenSize.HEIGHT - 246;

        renderLayer = new RenderLayer(camera, Color.FOREST, screenSize.WIDTH, screenSize.HEIGHT);
        //renderLayer.showLogs(true);
        final Color topColor = new Color(70 / 255.0f, 168 / 255.0f, 255 / 255.0f, 1);
        final Color bottomColor = new Color(53 / 255.0f, 255 / 255.0f, 205 / 255.0f, 1);
        renderLayer.setColor(topColor, topColor, bottomColor, bottomColor);

        bestScoreStore = new BestScoreStore();
        moneyStore = new MoneyStore();
        showedRewardedVideoStore = new ShowedRewardedVideoStore();

        pool = new ObjectPool();

        tileCreator = new TileCreator(HORIZONTAL_TILE_COUNT, VERTICAL_TILE_COUNT, pool);
        tiles = tileCreator.createTiles();
        tileSprites = new ArrayList<TileSprite>(HORIZONTAL_TILE_COUNT * VERTICAL_TILE_COUNT);

        musicManager = new MusicManager(assetsLoader);
        bonusCreator = new BonusCreator(pool, renderLayer, assetsLoader, musicManager, animator, BONUS_LAYER);

        gameState = GameState.PLAY;

        musicManager.playMusicBack1();

        settingsStore = new SettingsStore();
        this.stringsResources = stringsResources;

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
            pool.create(SnowflakeSprite.class, 1);
            pool.create(BrillianceSprite.class, 1);
            pool.create(StoneSprite.class, 1);

            goldBonusRandom = new ProbabilityRandom(15);
            brillianceBonusRandom = new ProbabilityRandom(3);
            snowflakeProbabilityRandom = new ProbabilityRandom(SNOWFLAKE_PROBABILITY);
            stoneRandom = new ProbabilityRandom(15);
            binaryRandom = new BinaryRandom();

            interstitialBinaryRandom = new ProbabilityRandom(30);

            distanceText = new TextSprite("0m", gameFont, 50, 30);
            renderLayer.addTextSprite(distanceText, TEXT_LAYER);

            final float moneyFontWidth = FontUtils.getFontWidth(gameFont, "0");
            final float moneyFontXPosition = screenSize.WIDTH - moneyFontWidth - MONEY_RIGHT_OFFSET;

            moneyText = new TextSprite("0", gameFont, moneyFontXPosition, 30);
            renderLayer.addTextSprite(moneyText, TEXT_LAYER);
            moneyIcon = new GoldSprite(assetsLoader.getGoldTextureRegion(), moneyFontXPosition + moneyFontWidth + 10, 27, 25, 25);
            renderLayer.addSprite(moneyIcon, true, TEXT_LAYER);

            fogBackgroundSprite = new FogBackgroundSprite(renderLayer, FOG_LAYER, 0, 0, screenSize.WIDTH, screenSize.HEIGHT);
            fogBackgroundSprite.stopGain();

            loosingGameDialog = new LoosingGameDialog(assetsLoader, this, stringsResources);

            clickToStartTextSprite = new ClickToStartTextSprite(stringsResources.getClickToStart(), screenSize);
            renderLayer.addTextSprite(clickToStartTextSprite, CLICK_TO_START_LAYER);

            pauseButton = new Sprite(assetsLoader.getPauseButtonTextureRegion(), 50,
                    screenSize.HEIGHT - assetsLoader.getPauseButtonTextureRegion().getRegionHeight() - 50, 50, 50);
            renderLayer.addSprite(pauseButton, true, PAUSE_BUTTON_LAYER);

            bonusCreator.setOnBonusCachedListener(this);
            restartRewardedVideoState();
        } catch (Exception e) {
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
                        final float leftXPosition = -TILE_BEVEL_SIZE * ((screenSize.HEIGHT - currentYPosition) / TILE_HEIGHT);
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

    private void createStone(final TileEntity leftTileEntity, final TileEntity rightTileEntity, final float tileYPosition) throws Exception {
        if (leftTileEntity.getType().equals(TileEntity.Type.BLACK)
                || rightTileEntity.getType().equals(TileEntity.Type.BLACK)) {
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
        if (binaryRandom.getRandom()) {
            stoneSprite.changeAnimation(DirectedAnimatedSprite.FlipDirection.RIGHT);
            stoneSpriteAnimation.restart(0, tileYPosition, 25, screenSize.HEIGHT / 2, TILE_SPEED + 220);
        } else {
            stoneSprite.changeAnimation(DirectedAnimatedSprite.FlipDirection.LEFT);
            stoneSpriteAnimation.restart(screenSize.WIDTH - stoneSprite.getWidth(), tileYPosition,
                    screenSize.WIDTH - stoneSprite.getWidth() - 25, screenSize.HEIGHT / 2, TILE_SPEED + 220);
        }
        stoneSpriteAnimation.start();
        animator.createAnimation(stoneSpriteAnimation);

        musicManager.playMusicStone();

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
                        final float tileLeftXPosition = -TILE_BEVEL_SIZE * ((screenSize.HEIGHT - currentYPosition) / TILE_HEIGHT);
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
                    final BonusType bonusType = tileSpriteUnderCharacter.getBonus().getType();

                    switch (bonusType) {
                        case GOLD:
                            bonusCreator.onBonusCatched(tileSpriteUnderCharacter, moneyText.getX(), moneyText.getY());
                            break;
                        case BRILLIANCE:
                            bonusCreator.onBonusCatched(tileSpriteUnderCharacter, moneyText.getX(), moneyText.getY());
                            break;
                        case SNOWFLAKE:
                            bonusCreator.onBonusCatched(tileSpriteUnderCharacter, screenSize.WIDTH / 2, screenSize.HEIGHT / 2);
                            break;
                    }

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
        musicManager.playSoundFall();

        if (settingsStore.isVibrationEnabled()) {
            Gdx.input.vibrate(VIBRATION_LENGTH);
        }

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


        final long bestStoredScore = bestScoreStore.getBestScore();
        if (distance > bestStoredScore) {
            bestScoreStore.setBestScore(distance);
        }

        showLoosingGameDialog();
        gameIsStarted = false;
    }

    private void createNewPair() {
        distance++;
        goldRewardIncrement++;
        brillianceRewardIncrement++;
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
            // check gold reward
            if (goldRewardIncrement >= GOLD_REWARD_BETWEEN_DISTANCE) {
                goldRewardPeriodIncrement++;
                if (goldRewardPeriodIncrement >= GOLD_REWARD_PERIOD) {
                    goldRewardIncrement = -1;
                    goldRewardPeriodIncrement = 0;
                }
            }
            if (goldRewardPeriodIncrement == 0) {
                goldBonusRandom = goldDefaultProbabilityRandom;
            } else if (goldRewardPeriodIncrement == 1) {
                goldBonusRandom = goldRewardProbabilityRandom;
            }
            // check brilliance reward
            if (brillianceRewardIncrement >= BRILLIANCE_REWARD_BETWEEN_DISTANCE) {
                brillianceRewardPeriodIncrement++;
                if (brillianceRewardPeriodIncrement >= BRILLIANCE_REWARD_PERIOD) {
                    brillianceRewardIncrement = -1;
                    brillianceRewardPeriodIncrement = 0;
                }
            }
            if (brillianceRewardPeriodIncrement == 0) {
                brillianceBonusRandom = brillianceDefaultProbabilityRandom;
            } else if (brillianceRewardPeriodIncrement == 1) {
                brillianceBonusRandom = brillianceRewardProbabilityRandom;
            }

            if (goldBonusRandom.get()) {
                bonusCreator.createBonus(BonusType.GOLD, newLeftTileSprite, newRightTileSprite);
            } else if (brillianceBonusRandom.get()) {
                bonusCreator.createBonus(BonusType.BRILLIANCE, newLeftTileSprite, newRightTileSprite);
            } else if (distance > SNOWFLAKE_START_OFFSET &&
                    currentSnowflakeOffset > SNOWFLAKE_NEXT_OFFSET &&
                    snowflakeProbabilityRandom.get() && newLeftTileEntity.getType() == TileEntity.Type.WHITE
                    && newRightTileSprite.getType() == TileEntity.Type.WHITE) {
                bonusCreator.createBonus(BonusType.SNOWFLAKE, newLeftTileSprite, newRightTileSprite);
                currentSnowflakeOffset = 0;
            }
            if (stoneRandom.get() && createdStoneSprite == null) { // за один раз только один камень
                createStone(newLeftTileEntity, newRightTileEntity, tileYPosition);
            }

            currentSnowflakeOffset++;
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void rotateCharacter() {
        float rotateAngle = 0;
        final float halfWidth = screenSize.WIDTH / 2;
        if (character.getX() + character.getWidth() / 2 <= halfWidth) {
            rotateAngle = -CHARACTER_MAX_ROTATION_ANGLE * ((halfWidth - character.getX()) / halfWidth);
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

        final float moneyFontWidth = FontUtils.getFontWidth(gameFont, moneyCount + "");
        final float moneyXPosition = screenSize.WIDTH - moneyFontWidth - MONEY_RIGHT_OFFSET;
        moneyText.setX(moneyXPosition);
        moneyIcon.setX(moneyXPosition + moneyFontWidth + 10);
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
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(final float delta) {
        if (gameState == GameState.PLAY) {
            cameraShakeEffect.update(delta, camera);

            renderLayer.render(delta);
            animator.update(delta);

            if (!isFailedGame && gameIsStarted) {
                checkCharacterIsDie();
                moveTiles(delta);
            }

            if (fogBackgroundSprite != null) {
                fogBackgroundSprite.render(delta);
            }

            rotateCharacter();
            //fps.log();
        } else {
            renderLayer.render(0);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        musicManager.pauseAllSounds();
    }

    @Override
    public void resume() {
        musicManager.resumeAllSounds();
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
        if (relativeClickX > pauseButton.getX() && relativeClickX < pauseButton.getX() + pauseButton.getWidth() &&
                relativeClickY > pauseButton.getY() && relativeClickY < pauseButton.getY() + pauseButton.getHeight()) {
            if (gameState == GameState.PLAY) {
                gameState = GameState.PAUSE;
                pause();
            } else {
                gameState = GameState.PLAY;
                resume();
            }
            return;
        }
        if (loosingGameDialog.isShown()) {
            loosingGameDialog.checkRestartButtonClickedAndAction(relativeClickX, relativeClickY);
            loosingGameDialog.checkContinueButtonClickedAndAction(relativeClickX, relativeClickY);
            loosingGameDialog.checkX2ButtonClickAndAction(relativeClickX, relativeClickY);
        } else {
            if (!isFailedGame) {
                if (gameIsStarted  && gameState == GameState.PLAY) {
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
    public void onRestartAction() {
        fogBackgroundSprite.stopGain();
        restartRewardedVideoState();
        renderLayer.addTextSprite(clickToStartTextSprite, CLICK_TO_START_LAYER);
        loosingGameDialog.removeFromScreen(renderLayer);

        isFailedGame = false;
        gameIsStarted = false;

        goldRewardIncrement = -1;
        goldRewardPeriodIncrement = 0;
        goldBonusRandom = goldDefaultProbabilityRandom;

        brillianceRewardIncrement = -1;
        brillianceRewardPeriodIncrement = 0;
        brillianceBonusRandom = brillianceDefaultProbabilityRandom;

        if (interstitialBinaryRandom.get()) {
            onShowAdListener.onShowInterstitialAd();
        }
        if (createdStoneSprite != null) {
            renderLayer.removeSprite(createdStoneSprite);
        }

        restartGame();
        clearProgress();
        currentGameMoneyCount = 0;
        createDefaultTiles();
    }

    @Override
    public void onContinueAction() {
        onShowAdListener.onShowRewardedVideoForContinue();
    }

    @Override
    public void onX2Action() {
        onShowAdListener.onShowRewardedVideoForX2();
    }

    @Override
    public void onBonusCatched(BonusSprite bonusSprite) {
        if (bonusSprite.getType().equals(BonusType.GOLD) || bonusSprite.getType().equals(BonusType.BRILLIANCE)) {
            moneyCount += bonusSprite.getSelfCoast();
            currentGameMoneyCount += bonusSprite.getSelfCoast();
            moneyText.setText(moneyCount + "");

            final float moneyFontWidth = FontUtils.getFontWidth(gameFont, moneyCount + "");
            final float moneyFontXPosition = screenSize.WIDTH - moneyFontWidth - MONEY_RIGHT_OFFSET;

            moneyText.setX(moneyFontXPosition);
            moneyIcon.setX(moneyFontXPosition + moneyFontWidth + 10);
        } else if (bonusSprite.getType().equals(BonusType.SNOWFLAKE)) {
            fogBackgroundSprite.startGain();
        }
        renderLayer.removeSprite(bonusSprite);
        pool.release(bonusSprite);
    }

    public void onRewardedVideoLoaded() {
        isRewardedVideoLoaded = true;
    }

    public void onRewardedVideoUnload() {
        isRewardedVideoLoaded = false;
    }

    public void onContinueVideoRewarded() {
        showedRewardedVideoStore.setShowedRewardedVideoType(ShowedRewardedVideoStore.RewardedVideoType.CONTINUE_SHOWED);
        isFailedGame = false;
        loosingGameDialog.removeFromScreen(renderLayer);
        renderLayer.addTextSprite(clickToStartTextSprite, CLICK_TO_START_LAYER);

        isFailedGame = false;
        gameIsStarted = false;

        restartGame();
        currentGameMoneyCount = 0;
        createDefaultTiles();
    }

    public void onX2VideoRewarded() {
        showedRewardedVideoStore.setShowedRewardedVideoType(ShowedRewardedVideoStore.RewardedVideoType.X2_SHOWED);
        currentGameMoneyCount = Double.valueOf(currentGameMoneyCount * 1.5).longValue();
        moneyCount = Double.valueOf(moneyCount * 1.5).longValue();
        loosingGameDialog.removeFromScreen(renderLayer);
        showLoosingGameDialog();
    }

    private void showLoosingGameDialog() {
        loosingGameDialog.setWidth(450);
        loosingGameDialog.setHeight(275);
        loosingGameDialog.setX(screenSize.WIDTH / 2 - loosingGameDialog.getWidth() / 2);
        loosingGameDialog.setY(screenSize.HEIGHT / 2 - loosingGameDialog.getHeight() / 2);

        final long totalMoney = moneyStore.getTotalMoney() + currentGameMoneyCount;
        moneyStore.setTotalMoney(totalMoney);

        boolean canContinueGame = false;
        boolean canIncreaseMoney = false;
        if (isRewardedVideoLoaded) {
            final ShowedRewardedVideoStore.RewardedVideoType rewardedVideoType =
                    showedRewardedVideoStore.getShowedRewardedVideoType();
            if (rewardedVideoType.equals(ShowedRewardedVideoStore.RewardedVideoType.NOTHING_SHOWED)) {
                canContinueGame = true;
                canIncreaseMoney = false;
            } else if (rewardedVideoType.equals(ShowedRewardedVideoStore.RewardedVideoType.CONTINUE_SHOWED)) {
                canContinueGame = false;
                canIncreaseMoney = true;
            } else if (rewardedVideoType.equals(ShowedRewardedVideoStore.RewardedVideoType.X2_SHOWED)) {
                canContinueGame = false;
                canIncreaseMoney = false;
            }
        }
        loosingGameDialog.initDialog(distance, bestScoreStore.getBestScore(), moneyCount, canContinueGame, canIncreaseMoney);
        loosingGameDialog.addOnScreen(renderLayer, LOOSING_GAME_DIALOG_LAYER);
    }

    private void restartRewardedVideoState() {
        // переустанавливаем состояние для условий показа рекламы. какбудето не показана ранее.
        // т.е. кнопка континью и ч2 будут потом доступны
        showedRewardedVideoStore.setShowedRewardedVideoType(ShowedRewardedVideoStore.RewardedVideoType.NOTHING_SHOWED);
    }

}
