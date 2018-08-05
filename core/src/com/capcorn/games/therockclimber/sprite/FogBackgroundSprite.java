package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.capcorn.game.engine.core.RenderLayer;
import com.capcorn.game.engine.sprite.RectangleSprite;
import com.capcorn.game.engine.sprite.Sprite;

/**
 * User: kprotasov
 * Date: 19.05.2018
 * Time: 15:43
 */

public class FogBackgroundSprite extends RectangleSprite implements RenderSprite {

    private float startAlpha = 0f;
    private float stopAlpha = 0.5f;
    private float deltaAlpha = 0.002f;
    private boolean canGain = false;
    private boolean gainIsReached = false;
    private Color color = new Color(1f, 1f, 1f, startAlpha);
    private final RenderLayer renderLayer;
    private final int layer;

    public FogBackgroundSprite(final RenderLayer renderLayer, final int layer, final float x, final float y, final float width, final float height) {
        super(new Color(1f, 1f, 1f, 0.2f), x, y, width, height);
        this.renderLayer = renderLayer;
        this.layer = layer;
    }

    public void startGain() {
        canGain = true;
        gainIsReached = false;
        color.set(1f, 1f, 1f, startAlpha);
        setColor(color);
        renderLayer.addShapeSprite(this, true, layer);
    }

    public void stopGain() {
        canGain = false;
        renderLayer.removeSprite(this);
    }

    @Override
    public void render(float delta) {
        if (canGain) {
            if (!gainIsReached) {
                if (color.a < stopAlpha) {
                    setColor(color.set(1f, 1f, 1f, color.a + deltaAlpha));
                } else {
                    setColor(color.set(1f, 1f, 1f, stopAlpha));
                    gainIsReached = true;
                }
            } else {
                if (color.a > startAlpha) {
                    setColor(color.set(1f, 1f, 1f, color.a - deltaAlpha));
                } else {
                    setColor(color.set(1f, 1f, 1f, startAlpha));
                    stopGain();
                }
            }
            /*if (color.a < stopAlpha) {
                setColor(color.set(1f, 1f, 1f, color.a + deltaAlpha));
            } else {
                setColor(color.set(1f, 1f, 1f, stopAlpha));
                gainIsReached = true;
            }*/
        }
    }

}
