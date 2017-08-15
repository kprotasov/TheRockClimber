package com.capcorn.games.therockclimber.entity;

import com.capcorn.game.engine.sprite.SpriteBase;

/**
 * User: kprotasov
 * Date: 28.12.2016
 * Time: 17:06
 */
public class TileEntity implements SpriteBase{

	private Type type;
	private Position position;

	@Override
	public void release() {

	}

	public enum Type {
		BLACK,
		WHITE
	}

	public enum Position {
		LEFT,
		RIGHT
	}

	public TileEntity() {

	}

	public TileEntity(final Type type) {
		this.type = type;
	}

	public static TileEntity createTileEntity(final int position) {
		return new TileEntity(createType(position));
	}

	public Type getType() {
		return type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	public static Type createType(final int position) {
		if (position > Type.values().length - 1) {
			throw new IllegalArgumentException("wrong position");
		}
		return Type.values()[position];
	}

}
