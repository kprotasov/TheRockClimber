package com.capcorn.games.therockclimber.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.capcorn.game.engine.sprite.Sprite;
import com.capcorn.games.therockclimber.graphics.AssetsLoader;

/**
 * User: kprotasov
 * Date: 10.09.2017
 * Time: 17:47
 */

public class LoosingGameDialog extends Sprite {

    public LoosingGameDialog(final AssetsLoader assetsLoader) {
        super(null, 0, 0);
        init(assetsLoader);
    }

    private void init(final AssetsLoader assetsLoader) {
        this.setTextureRegion(createDialogTextureRegion(assetsLoader));
    }

    private static TextureRegion createDialogTextureRegion(final AssetsLoader assetsLoader) {
        return assetsLoader.getLoosingGameBackgroundTextureRegion();
    }

}
