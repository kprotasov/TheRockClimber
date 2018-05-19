package com.capcorn.games.therockclimber.creator;

import com.capcorn.game.engine.animation.Animator;
import com.capcorn.game.engine.animation.TweenAnimation;
import com.capcorn.game.engine.core.RenderLayer;
import com.capcorn.game.engine.pool.ObjectPool;
import com.capcorn.game.engine.utils.BinaryRandom;
import com.capcorn.games.therockclimber.entity.TileEntity;
import com.capcorn.games.therockclimber.graphics.AssetsLoader;
import com.capcorn.games.therockclimber.sprite.bonus.BonusSprite;
import com.capcorn.games.therockclimber.sprite.bonus.BonusType;
import com.capcorn.games.therockclimber.sprite.bonus.BrillianceSprite;
import com.capcorn.games.therockclimber.sprite.bonus.GoldSprite;
import com.capcorn.games.therockclimber.sprite.bonus.SnowflakeSprite;
import com.capcorn.games.therockclimber.sprite.TileSprite;

/**
 * User: kprotasov
 * Date: 19.10.2017
 * Time: 11:58
 */

public class BonusCreator {

    private static final float GOLD_SIZE = 55;
    private static final float BRILLIANCE_SIZE = 55;
    private static final float SNOWFLAKE_SIZE = 55;
    private static final float GOLD_TWEEN_SPEED = 1500.0f;
    private static final float BRILLIANCE_TWEEN_SPEED = 1500.0f;
    private static final float SNOWFLAKE_TWEEN_SPEED = 500.0f;

    private final ObjectPool pool;
    private final RenderLayer renderLayer;
    private final AssetsLoader assetsLoader;
    private final Animator animator;
    private final int bonusLayer;
    private final BinaryRandom binaryRandom;

    private OnBonusCahcedListener onBonusCathcedListener;

    public BonusCreator(final ObjectPool pool, final RenderLayer renderLayer, final AssetsLoader assetsLoader, final Animator animator, final int bonusLayer) {
        this.pool = pool;
        this.renderLayer = renderLayer;
        this.assetsLoader = assetsLoader;
        this.animator = animator;
        this.bonusLayer = bonusLayer;
        binaryRandom = new BinaryRandom();
    }

    public void setOnBonusCachedListener(final OnBonusCahcedListener listener) {
        this.onBonusCathcedListener = listener;
    }

    public final void createBonus(final BonusType bonusType, final TileSprite leftTileSprite, final TileSprite rightTileSprite) throws IllegalAccessException, InstantiationException{
        final TileSprite notEmptyTileSprite = getCorrectTileSpriteForBonus(leftTileSprite, rightTileSprite);

        final BonusSprite bonusSprite = (BonusSprite) pool.get(BonusSprite.class);
        bonusSprite.setType(bonusType);
        switch (bonusType) {
            case GOLD:
                setupGoldBonus(bonusSprite);
                break;
            case BRILLIANCE:
                setupBrillianceBonus(bonusSprite);
                break;
            case SNOWFLAKE:
                setupSnowflakeBonus(bonusSprite);
                break;
        }

        final float goldYPos = notEmptyTileSprite.getY() + notEmptyTileSprite.getHeight() * 0.5f - bonusSprite.getHeight() * 0.5f;
        bonusSprite.setY(goldYPos);
        if (notEmptyTileSprite.getPosition().equals(TileEntity.Position.LEFT)) {
            bonusSprite.setX(notEmptyTileSprite.getX() + notEmptyTileSprite.getWidth());
        } else {
            bonusSprite.setX(notEmptyTileSprite.getX() - bonusSprite.getWidth());
        }
        notEmptyTileSprite.setBonus(bonusSprite);
        renderLayer.addSprite(bonusSprite, true, bonusLayer);
    }

    public void onBonusCatched(final TileSprite tileWithBonus, final float destinationX, final float destinationY) {
        final BonusSprite bonus = tileWithBonus.getBonus();
        try {
            switch (bonus.getType()) {
                case GOLD:
                    onGoldBonusCatched(bonus, destinationX, destinationY);
                    break;
                case BRILLIANCE:
                    onBrillianceBonusCatched(bonus, destinationX, destinationY);
                    break;
                case SNOWFLAKE:
                    onSnowflakeBonusCatched(bonus, destinationX, destinationY);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        pool.release(bonus);
        renderLayer.removeSprite(bonus);
        tileWithBonus.setBonus(null);
    }

    private void onGoldBonusCatched(final BonusSprite bonus, final float destinationXPosition, final float destinationYPosition) throws IllegalAccessException, InstantiationException{
        final GoldSprite goldSprite = (GoldSprite) pool.get(GoldSprite.class);
        goldSprite.setTextureRegion(assetsLoader.getGoldTextureRegion());
        goldSprite.setWidth(GOLD_SIZE);
        goldSprite.setHeight(GOLD_SIZE);
        final TweenAnimation goldTweenAnimation;
        if (goldSprite.getTweenAnimation() != null) {
            goldTweenAnimation = goldSprite.getTweenAnimation();
        } else {
            goldTweenAnimation = new TweenAnimation();
            goldSprite.setTweenAnimation(goldTweenAnimation);
        }
        animator.createAnimation(goldTweenAnimation);
        goldTweenAnimation.restart(bonus.getX(), bonus.getY(), destinationXPosition, destinationYPosition, GOLD_TWEEN_SPEED);
        goldTweenAnimation.start();
        goldTweenAnimation.setOnAnimationFinishListener(new TweenAnimation.OnAnimationFinishListener() {
            @Override
            public void onAnimationFinish(final float destX, final float destY) {
                if (onBonusCathcedListener != null) {
                    onBonusCathcedListener.onBonusCatched(goldSprite);
                }
            }
        });
        renderLayer.addSprite(goldSprite, true, bonusLayer);
    }

    private void onBrillianceBonusCatched(final BonusSprite bonus, final float destinationXPosition, final float destinationYPosition) throws IllegalAccessException, InstantiationException{
        final BrillianceSprite brillianceSprite = (BrillianceSprite) pool.get(BrillianceSprite.class);
        brillianceSprite.setTextureRegion(assetsLoader.getBrillianceTextureRegion());
        brillianceSprite.setWidth(BRILLIANCE_SIZE);
        brillianceSprite.setHeight(BRILLIANCE_SIZE);
        final TweenAnimation brillianceTweenAnimation;
        if (brillianceSprite.getTweenAnimation() != null) {
            brillianceTweenAnimation = brillianceSprite.getTweenAnimation();
        } else {
            brillianceTweenAnimation = new TweenAnimation();
            brillianceSprite.setTweenAnimation(brillianceTweenAnimation);
        }
        animator.createAnimation(brillianceTweenAnimation);
        brillianceTweenAnimation.restart(bonus.getX(), bonus.getY(), destinationXPosition, destinationYPosition, BRILLIANCE_TWEEN_SPEED);
        brillianceTweenAnimation.start();
        brillianceTweenAnimation.setOnAnimationFinishListener(new TweenAnimation.OnAnimationFinishListener() {
            @Override
            public void onAnimationFinish(final float destX, final float destY) {
                if (onBonusCathcedListener != null) {
                    onBonusCathcedListener.onBonusCatched(brillianceSprite);
                }
            }
        });
        renderLayer.addSprite(brillianceSprite, true, bonusLayer);
    }

    private void onSnowflakeBonusCatched(final BonusSprite bonus, final float destinationXPosition, final float destinationYPosition) throws IllegalAccessException, InstantiationException{
        final SnowflakeSprite snowflakeSprite = (SnowflakeSprite) pool.get(SnowflakeSprite.class);
        snowflakeSprite.setTextureRegion(assetsLoader.getSnowflakeTextureRegion());
        snowflakeSprite.setWidth(SNOWFLAKE_SIZE);
        snowflakeSprite.setHeight(SNOWFLAKE_SIZE);
        //setupSnowflakeBonus(snowflakeSprite);
        final TweenAnimation tweenAnimation;
        if (snowflakeSprite.getTweenAnimation() != null) {
            tweenAnimation = snowflakeSprite.getTweenAnimation();
        } else {
            tweenAnimation = new TweenAnimation();
            snowflakeSprite.setTweenAnimation(tweenAnimation);
        }
        animator.createAnimation(tweenAnimation);
        tweenAnimation.restart(bonus.getX(), bonus.getY(), destinationXPosition, destinationYPosition, SNOWFLAKE_TWEEN_SPEED);
        tweenAnimation.start();
        tweenAnimation.setOnAnimationFinishListener(new TweenAnimation.OnAnimationFinishListener() {
            @Override
            public void onAnimationFinish(final float destX, final float destY) {
                if (onBonusCathcedListener != null) {
                    onBonusCathcedListener.onBonusCatched(snowflakeSprite);
                }
            }
        });
        renderLayer.addSprite(snowflakeSprite, true, bonusLayer);
    }

    private void setupGoldBonus(final BonusSprite goldSprite){
        goldSprite.setTextureRegion(assetsLoader.getGoldTextureRegion());
        goldSprite.setWidth(GOLD_SIZE);
        goldSprite.setHeight(GOLD_SIZE);
    }

    private void setupBrillianceBonus(final BonusSprite brillianceSprite){
        brillianceSprite.setTextureRegion(assetsLoader.getBrillianceTextureRegion());
        brillianceSprite.setWidth(BRILLIANCE_SIZE);
        brillianceSprite.setHeight(BRILLIANCE_SIZE);
    }

    private void setupSnowflakeBonus(final BonusSprite snowflakeBonus) {
        snowflakeBonus.setTextureRegion(assetsLoader.getSnowflakeTextureRegion());
        snowflakeBonus.setWidth(SNOWFLAKE_SIZE);
        snowflakeBonus.setHeight(SNOWFLAKE_SIZE);
    }

    private TileSprite getCorrectTileSpriteForBonus(final TileSprite leftTileSprite, final TileSprite rightTileSprite) {
        final TileSprite notEmptyTileSprite;
        if (leftTileSprite.getType().equals(TileEntity.Type.WHITE) && rightTileSprite.getType().equals(TileEntity.Type.WHITE)) {
            if (binaryRandom.getRandom() || !leftTileSprite.hasBonus()) {
                notEmptyTileSprite = leftTileSprite;
            } else {
                notEmptyTileSprite = rightTileSprite;
            }
        } else {
            if (rightTileSprite.getType().equals(TileEntity.Type.WHITE)) {
                notEmptyTileSprite = rightTileSprite;
            } else {
                notEmptyTileSprite = leftTileSprite;
            }
        }
        return notEmptyTileSprite;
    }

    public interface OnBonusCahcedListener {

        void onBonusCatched(final BonusSprite bonusSprite);

    }

}
