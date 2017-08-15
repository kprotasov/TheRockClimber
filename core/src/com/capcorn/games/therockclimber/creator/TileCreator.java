package com.capcorn.games.therockclimber.creator;

import com.capcorn.game.engine.pool.ObjectPool;
import com.capcorn.games.therockclimber.entity.TileEntity;

import java.util.ArrayList;
import java.util.Random;

/**
 * User: kprotasov
 * Date: 28.12.2016
 * Time: 16:26
 */
public class TileCreator {

	private final Random random;
	private final int horizontalCount;
	private final int verticalCount;
	private ObjectPool pool;

	public TileCreator(final int horizontalCount, final int verticalCount, final ObjectPool objectPool) {
		this.random = new Random();
		this.horizontalCount = horizontalCount;
		this.verticalCount = verticalCount;
		this.pool = objectPool;
	}

	public ArrayList<TileEntity> createTiles() {
		final ArrayList<TileEntity> tiles = new ArrayList<TileEntity>(horizontalCount * verticalCount);
		TileEntity.Type prevType = TileEntity.Type.BLACK;
		int prevX = 0;
		for (int y = 0; y < verticalCount; y++) {
			for (int x = 0; x < horizontalCount; x++) {
				final TileEntity.Type type;
				if (prevType == TileEntity.Type.BLACK && x != prevX) {
					type = TileEntity.Type.WHITE;
				} else {
					type = createNewType();
				}
				try {
					final TileEntity newTile = (TileEntity) pool.get(TileEntity.class); //new TileEntity(type);
					newTile.setType(type);
					tiles.add(newTile);
					prevX = x;
					prevType = type;
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
		return tiles;
	}

	public ArrayList<TileEntity> setNewPair(final ArrayList<TileEntity> tiles) {
		final ArrayList<TileEntity> newTiles = tiles;
		pool.release(newTiles.get(0));
		newTiles.remove(0);
		pool.release(newTiles.get(0));
		newTiles.remove(0);

		final TileEntity.Type type = createNewType();
		final TileEntity newTile = new TileEntity(type);
		newTiles.add(newTile);

		if (type == TileEntity.Type.BLACK) {
			newTiles.add(new TileEntity(TileEntity.Type.WHITE));
		} else {
			final TileEntity.Type nextType = createNewType();
			final TileEntity nextTile = new TileEntity(nextType);
			newTiles.add(nextTile);
		}

		return newTiles;
	}

	private TileEntity.Type createNewType() {
		final int randomNum = random.nextInt(100);
		if (randomNum >= 50) {
			return TileEntity.Type.BLACK;
		} else {
			return TileEntity.Type.WHITE;
		}
	}

}
