package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.capcorn.game.engine.pool.ObjectPool;
import com.capcorn.game.engine.sprite.Sprite;
import com.capcorn.games.therockclimber.entity.TileEntity;
import com.capcorn.games.therockclimber.graphics.AssetsLoader;

/**
 * User: kprotasov
 * Date: 24.06.2017
 * Time: 16:19
 */

public class TileSprite extends Sprite {

    private Sprite bonus;
    private TileEntity.Type type;
    private TileEntity.Position position;

    public TileEntity.Position getPosition() {
        return position;
    }

    public void setPosition(TileEntity.Position position) {
        this.position = position;
    }

    public TileEntity.Type getType() {
        return type;
    }

    public void setType(TileEntity.Type type) {
        this.type = type;
    }

    public TileSprite() {
        super();
    }

    public TileSprite(TextureRegion textureRegion, float x, float y, float width, float height) {
        super(textureRegion, x, y, width, height);
    }

    public TileSprite(TextureRegion textureRegion, float x, float y) {
        super(textureRegion, x, y);
    }

    public Sprite getBonus() {
        return bonus;
    }

    public void setBonus(Sprite bonus) {
        this.bonus = bonus;
    }

    public boolean hasBonus() {
        return bonus != null;
    }

    public static TileSprite createTileSprite(final float xPos, final float yPos, final TileEntity.Type type, final TileEntity.Position position,
                                              final ObjectPool pool, final AssetsLoader assetsLoader, final float tileWidth, final float tileHeight) throws Exception{
        final TileSprite tileSprite = (TileSprite) pool.get(TileSprite.class);
        tileSprite.setX(xPos);
        tileSprite.setY(yPos);
        tileSprite.setWidth(tileWidth);
        tileSprite.setHeight(tileHeight);
        tileSprite.setType(type);
        tileSprite.setPosition(position);
        if (type == TileEntity.Type.BLACK) {
            if (position == TileEntity.Position.LEFT) {
                tileSprite.setTextureRegion(assetsLoader.getBlackTileRightTextureRegion());
            } else {
                tileSprite.setTextureRegion(assetsLoader.getBlackTileLeftTextureRegion());
            }
        } else {
            if (position == TileEntity.Position.LEFT) {
                tileSprite.setTextureRegion(assetsLoader.getWhiteTileRightTextureRegion());
            } else {
                tileSprite.setTextureRegion(assetsLoader.getWhiteTileLeftTextureRegion());
            }
        }
        return tileSprite;
    }

}