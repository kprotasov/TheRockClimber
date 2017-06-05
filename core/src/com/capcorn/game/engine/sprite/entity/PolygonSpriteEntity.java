package com.capcorn.game.engine.sprite.entity;

import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.StringBuilder;

/**
 * User: kprotasov
 * Date: 14.09.2016
 * Time: 1:08
 */
public class PolygonSpriteEntity {

	private PolygonSprite polygonSprite;
	private float[] vertices;
	private float finalVlaue;
	private Polygon polygon;

	public PolygonSpriteEntity(final PolygonSprite polygonSprite, final float[] vertices, final float finalValue) {
		this.polygonSprite = polygonSprite;
		this.vertices = vertices;
		this.finalVlaue = finalValue;
		this.polygon = new Polygon(vertices);
	}

	public PolygonSprite getPolygonSprite() {
		return polygonSprite;
	}

	public void setPolygonSprite(final PolygonSprite polygonSprite) {
		this.polygonSprite = polygonSprite;
	}

	public float[] getVertices() {
		return vertices;
	}

	public float[] getVerticesTransfermed() {
		return getPolygon().getTransformedVertices();
	}

	public void setVertices(final float[] vertices) {
		this.vertices = vertices;
	}

	public float getFinalVlaue() {
		return finalVlaue;
	}

	public void setFinalVlaue(final float finalVlaue) {
		this.finalVlaue = finalVlaue;
	}

	public Polygon getPolygon() {
		polygon.setPosition(polygonSprite.getX(), polygonSprite.getY());
		return polygon;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(vertices.length);
		for (int i = 0, length = vertices.length; i < length; i++) {
			builder.append("point: " + vertices[i] + " ");
		}
		return builder.toString();
	}

}
