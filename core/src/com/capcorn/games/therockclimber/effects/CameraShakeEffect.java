package com.capcorn.games.therockclimber.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * User: kprotasov
 * Date: 28.09.2017
 * Time: 16:21
 */

public class CameraShakeEffect {

    private float DISPERSION = 20;
    private float elapsed;
    private float duration;
    private float radius;
    private float randomAngle;
    private final Random random = new Random();
    private final float defaultCameraX;
    private final float defaultCameraY;

    public CameraShakeEffect(final float defaultCameraX, final float defaultCameraY) {
        this.defaultCameraX = defaultCameraX;
        this.defaultCameraY = defaultCameraY;
    }

    public void shake(final float radius, final float duration) {
        this.elapsed = 0;
        this.duration = duration;
        this.radius = radius;
        this.randomAngle = random.nextFloat() % 360f;
    }

    public void update(final float delta, final OrthographicCamera camera) {
        if (elapsed < duration) {
            radius *= 1.01f;
            randomAngle += (DISPERSION + random.nextFloat() % 60f);
            final float x = (float) (Math.sin(randomAngle) * radius);
            final float y = (float) (Math.cos(randomAngle) * radius);
            camera.translate(x, y);
            camera.update();
            elapsed += delta;
        } else {
            camera.position.x = defaultCameraX;
            camera.position.y = defaultCameraY;
            camera.update();
        }
    }

}
