package com.capcorn.game.engine.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.capcorn.game.engine.core.entity.AnimatedSpriteEntity;
import com.capcorn.game.engine.core.entity.BaseEntity;
import com.capcorn.game.engine.core.entity.PolygonSpriteEntity;
import com.capcorn.game.engine.core.entity.ShapeSpriteEntity;
import com.capcorn.game.engine.core.entity.SpriteEntity;
import com.capcorn.game.engine.core.entity.TextEntity;
import com.capcorn.game.engine.sprite.AnimatedPolygonSprite;
import com.capcorn.game.engine.sprite.AnimatedSprite;
import com.capcorn.game.engine.sprite.CircleSprite;
import com.capcorn.game.engine.sprite.LineSprite;
import com.capcorn.game.engine.sprite.ShapeSprite;
import com.capcorn.game.engine.sprite.Sprite;
import com.capcorn.game.engine.sprite.SpriteBase;
import com.capcorn.game.engine.sprite.TextSprite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by kprotasov on 26.04.2016.
 */
public class RenderLayer {

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;
    private PolygonSpriteBatch polygonSpriteBatch;

    //TODO test variant with layers
    private ArrayList<BaseEntity> spritesList;
    private ArrayList<Integer> layersList;
    private ArrayList<SpriteBase> removedSpritesList;

    private float gameWidth;
    private float gameHeight;

    private float runTime = 0;

    private boolean showLogs = false;
    private BitmapFont font;
    private Color[] backgroundColors = new Color[4];

    public RenderLayer(final OrthographicCamera camera, final Color baseColor, final float gameWidth, final float gameHeight) {
        spritesList = new ArrayList<BaseEntity>();
        layersList = new ArrayList<Integer>();
        removedSpritesList = new ArrayList<SpriteBase>();

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;

        setColor(baseColor);

        spriteBatch = new SpriteBatch();
        spriteBatch.setProjectionMatrix(camera.combined);

        polygonSpriteBatch = new PolygonSpriteBatch();
        polygonSpriteBatch.setProjectionMatrix(camera.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
    }

    public void setColor(final Color color) {
        setColor(color, color, color, color);
    }

    public void setColor(final Color leftTopColor, final Color rightTopColor, final Color rightBottomColor, final Color leftBottomColor) {
        this.backgroundColors[0] = leftTopColor;
        this.backgroundColors[1] = rightTopColor;
        this.backgroundColors[2] = rightBottomColor;
        this.backgroundColors[3] = leftBottomColor;
    }

    public void showLogs(final boolean showLogs) {
        this.showLogs = showLogs;
        if (showLogs == true) {
            font = new BitmapFont(true);
            font.setColor(Color.BLACK);
            font.getData().setScale(2);
        }
    }

    public void render(final float delta) {
        runTime += delta;
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.rect(0, 0, gameWidth, gameHeight, backgroundColors[0], backgroundColors[1], backgroundColors[2], backgroundColors[3]);
        shapeRenderer.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glDisable(GL20.GL_BLEND); // TODO test

        int pos = 0;
        final Iterator layersIterator = layersList.iterator();
        while (layersIterator.hasNext()) {
            final Integer layer = (Integer)layersIterator.next();

            final Iterator spritesIterator = spritesList.iterator();
            while (spritesIterator.hasNext()) {
                final BaseEntity entity = (BaseEntity)spritesIterator.next();

                if (entity.getLayer() == layer) {
                    if (entity instanceof SpriteEntity) {
                        spriteBatch.begin();
                        drawSprite(((SpriteEntity) entity).getSprite(), entity.getBlending());
                        spriteBatch.end();
                    }
                    if (entity instanceof AnimatedSpriteEntity) {
                        spriteBatch.begin();
                        drawAnimatedSprite(((AnimatedSpriteEntity) entity).getSprite(), entity.getBlending(), delta);
                        spriteBatch.end();
                    }
                    if (entity instanceof PolygonSpriteEntity) {
                        polygonSpriteBatch.begin();
                        drawAnimatedPolygonSprite(((PolygonSpriteEntity) entity).getSprite());
                        polygonSpriteBatch.end();
                    }
                    if (entity instanceof ShapeSpriteEntity) {
                        drawShapeSprite(((ShapeSpriteEntity) entity).getSprite());
                    }
                    if (entity instanceof TextEntity) {
                        spriteBatch.begin();
                        drawTextSprite(((TextEntity)entity).getSprite());
                        spriteBatch.end();
                    }
                    pos++;
                }

            }
        }

        final Iterator iterator = removedSpritesList.iterator();
        while (iterator.hasNext()){
            final SpriteBase sprite = (SpriteBase) iterator.next();
            removeSpriteSafely(sprite);
            iterator.remove();
        }

        if (showLogs == true) {
            spriteBatch.begin();
            spriteBatch.enableBlending();
            font.draw(spriteBatch, "fps: " + Gdx.graphics.getFramesPerSecond(), 50, 50);
            spriteBatch.disableBlending();
            spriteBatch.end();
        }
    }

    private void drawShapeSprite(final ShapeSprite sprite) {
        switch (sprite.getType()) {
            case CIRCLE:
                final CircleSprite circleSprite = (CircleSprite)sprite;
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(circleSprite.getColor());
                shapeRenderer.circle(circleSprite.getX(), circleSprite.getY(), circleSprite.getRadius());
                shapeRenderer.end();
                break;
            case LINE:
                final LineSprite lineSprite = (LineSprite)sprite;
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(lineSprite.getColor());
                shapeRenderer.rectLine(lineSprite.getX(), lineSprite.getY(), lineSprite.getEndX(), lineSprite.getEndY(), lineSprite.getDepth());
                shapeRenderer.end();
                break;
        }
    }

    private void drawTextSprite(final TextSprite sprite) {
        spriteBatch.enableBlending();
        sprite.getFont().draw(spriteBatch, sprite.getText(), sprite.getX(), sprite.getY());
        spriteBatch.disableBlending();
    }

    private void drawSprite(final Sprite sprite, final boolean blending) {
        if (blending) {
            spriteBatch.enableBlending();
        }
        if (sprite.getTweenAnimation() != null) {
            sprite.setX(sprite.getTweenAnimation().getX());
            sprite.setY(sprite.getTweenAnimation().getY());
        }
        spriteBatch.draw(sprite.getTexture(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        spriteBatch.disableBlending();
    }

    private void drawAnimatedSprite(final AnimatedSprite animatedSprite, final boolean blending, final float delta) {
        if (animatedSprite != null) {
            if (blending) {
                spriteBatch.enableBlending();
            }
            animatedSprite.addRunTime(delta);
            if (animatedSprite.getTweenAnimation() != null) {
                animatedSprite.setX(animatedSprite.getTweenAnimation().getX());
                animatedSprite.setY(animatedSprite.getTweenAnimation().getY());
            }
            final float originX = animatedSprite.getWidth() / 2;
            final float originY = animatedSprite.getHeight() / 2;
            final float scaleX = 1.0f;
            final float scaleY = 1.0f;
            spriteBatch.draw(animatedSprite.getTextureRegion(), animatedSprite.getX(), animatedSprite.getY(),
                    originX, originY, animatedSprite.getWidth(), animatedSprite.getHeight(), scaleX, scaleY, animatedSprite.getRotateAngle());
            spriteBatch.disableBlending();
        }
    }

    private void drawAnimatedPolygonSprite(final AnimatedPolygonSprite animatedPolygonSprite) {
        animatedPolygonSprite.getSprite().draw(polygonSpriteBatch);
        if (animatedPolygonSprite.getTweenAnimation() != null) {
            animatedPolygonSprite.getSprite().setX(animatedPolygonSprite.getTweenAnimation().getX());
            animatedPolygonSprite.getSprite().setY(animatedPolygonSprite.getTweenAnimation().getY());
        }
    }

    public void removeSprite(final SpriteBase sprite) {
        if (!removedSpritesList.contains(sprite)) {
            removedSpritesList.add(sprite);
        }
    }

    private void removeSpriteSafely(final SpriteBase sprite) {
        int removedIndex = -1;
        BaseEntity removedEntity = null;
        for (int i = 0, length = spritesList.size(); i < length; i++) {
            final BaseEntity entity = spritesList.get(i);
            if (entity.getSprite().equals(sprite)) {
                removedIndex = i;
                removedEntity = entity;
            }
        }
        if (removedIndex != -1) {
            spritesList.remove(removedIndex);
            removeLayer(removedEntity.getLayer());
        }
    }

    public void addSprite(final Sprite sprite, final boolean blending, final int layer) {
        final SpriteEntity entity = new SpriteEntity(sprite);
        entity.setLayer(layer);
        entity.setBlending(blending);
        addLayer(layer);
        spritesList.add(entity);
    }

    public void addShapeSprite(final ShapeSprite sprite, final boolean blending, final int layer) {
        final ShapeSpriteEntity entity = new ShapeSpriteEntity(sprite);
        entity.setLayer(layer);
        entity.setBlending(blending);
        addLayer(layer);
        spritesList.add(entity);
    }

    public void addPolygonSprite(final AnimatedPolygonSprite sprite, final int layer) {
        final PolygonSpriteEntity entity = new PolygonSpriteEntity(sprite);
        entity.setLayer(layer);
        addLayer(layer);
        spritesList.add(entity);
    }

    public void addAnimatedSprite(final AnimatedSprite sprite, final boolean blending, final int layer) {
        final AnimatedSpriteEntity entity = new AnimatedSpriteEntity(sprite);
        entity.setLayer(layer);
        entity.setBlending(blending);
        addLayer(layer);
        spritesList.add(entity);
    }

    public void addTextSprite(final TextSprite sprite, final int layer) {
        final TextEntity entity = new TextEntity(sprite);
        entity.setLayer(layer);
        addLayer(layer);
        spritesList.add(entity);
    }

    private void addLayer(final Integer layer) {
        if (!layersList.contains(layer)) {
            layersList.add(layer);
            Collections.sort(layersList);
        }
    }

    private void removeLayer(final Integer layer) {
        boolean contains = false;
        for (int i = 0, length = spritesList.size(); i < length; i++) {
            if (spritesList.get(i).getLayer() == layer) {
                contains = true;
            }
        }
        if (!contains) {
            layersList.remove(layer);
        }
    }

}
