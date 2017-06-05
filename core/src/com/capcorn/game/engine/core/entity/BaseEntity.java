package com.capcorn.game.engine.core.entity;

import com.capcorn.game.engine.sprite.SpriteBase;

/**
 * User: kprotasov
 * Date: 03.11.2016
 * Time: 17:38
 */
public class BaseEntity {

	private int layer;
	private boolean blending;
	private SpriteBase sprite;

	public void setLayer(final int index) {
		this.layer = index;
	}

	public int getLayer() {
		return layer;
	}

	public boolean getBlending() {
		return blending;
	}

	public void setBlending(boolean blending) {
		this.blending = blending;
	}

	public SpriteBase getSprite() {
		return sprite;
	}

}
