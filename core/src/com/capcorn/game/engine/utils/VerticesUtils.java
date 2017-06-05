package com.capcorn.game.engine.utils;

/**
 * User: kprotasov
 * Date: 20.10.2016
 * Time: 11:23
 */
public class VerticesUtils {

	public static float getYPointByGivenXFromVertces(final float x, final float[] vertices) {
		// считаем что первая координата это x, а вторая - y
		if (vertices.length % 2 != 0) {
			throw new Error("size must be even number");
		}
		final int yShift = 1;
		final int shift = 2;
		// самая ближайшая точка до и после данной выше точки
		XYPair before = new XYPair(0, 0);
		XYPair resultBefore = new XYPair(0, 0);
		XYPair after = new XYPair(0, 0);
		XYPair resultAfter = new XYPair(0, 0);

		//Gdx.app.log("VerticesUtils", "getYPointByGivenXFromVertces length " + vertices.length + " xpoint " + x);
		for (int i = 0, length = vertices.length - shift; i < length; i+=shift) {
			final float xpos = vertices[i];
			final float ypos = vertices[i+yShift];
			//Gdx.app.log("VerticesUtils", "xpos " + xpos + " ypos " + ypos);
			//Gdx.app.log("VerticesUtils", "before xpos ypos " + before.getX() + " " + before.getY());
			if (before.getX() <= x && xpos > x) {
				resultBefore.setX(before.getX());
				resultBefore.setY(before.getY());
				//Gdx.app.log("VerticesUtils", "result before xpos ypos " + resultBefore.getX() + " " + resultBefore.getY());
			}
			before.setX(xpos);
			before.setY(ypos);
			//Gdx.app.log("VerticesUtils", "after xpos ypos " + after.getX() + " " + after.getY());
			if (after.getX() <= x && xpos > x) {
				resultAfter.setX(xpos);
				resultAfter.setY(ypos);
				//Gdx.app.log("VerticesUtils", "result after xpos ypos " + resultAfter.getX() + " " + resultAfter.getY());
			}
			after.setX(xpos);
			after.setY(ypos);
		}
		//Gdx.app.log("VerticesUtils", "resultBefore " + resultBefore.toString() + " resultAfter " + resultAfter.toString());
		float yResult = 0;
		if (resultBefore.getX() != 0 && resultAfter.getX() != 0 && resultBefore.getX() != resultAfter.getX()) {
			final float beforeX = resultBefore.getX();
			final float beforeY = resultBefore.getY();
			final float afterX = resultAfter.getX();
			final float afterY = resultAfter.getY();
			// формула подобия p2=(p3*n2)/n1 // new - p2 = (p1 * n2) / n1
			if (beforeY >= afterY) {
				//Gdx.app.log("VerticesUtils", "beforeY >= afterY ");
				final float yStart = afterY;
				//Gdx.app.log("VerticesUtils", "yStart " + yStart);
				final float n2 = afterX - x;
				//Gdx.app.log("VerticesUtils", "n2 " + n2);
				final float n1 = afterX - beforeX;
				//Gdx.app.log("VerticesUtils", "n1 " + n1);
				final float p1 = (float)Math.sqrt(((beforeY - yStart) * (beforeY - yStart)) + (n1 * n1));
				//Gdx.app.log("VerticesUtils", "p1 " + p1);
				final float p2 = (p1 * n2) / n1;
				//Gdx.app.log("VerticesUtils", "p2 " + p2);
				yResult = (float)Math.sqrt((p2 * p2) - (n2 * n2)) + yStart;
			} else {
				//Gdx.app.log("VerticesUtils", "beforeY < afterY ");
				final float yStart = beforeY;
				//Gdx.app.log("VerticesUtils", "yStart " + yStart);
				final float n2 = x - beforeX;
				//Gdx.app.log("VerticesUtils", "n2 " + n2);
				final float n1 = afterX - beforeX;
				//Gdx.app.log("VerticesUtils", "n1 " + n1);
				final float p1 = (float)Math.sqrt(((afterY - yStart) * (afterY - yStart)) + (n1 * n1));
				//Gdx.app.log("VerticesUtils", "p1 " + p1);
				final float p2 = (p1 * n2) / n1;
				//Gdx.app.log("VerticesUtils", "p2 " + p2);
				yResult = (float)Math.sqrt((p2 * p2) - (n2 * n2)) + yStart;
			}
		}
		//Gdx.app.log("VerticesUtils", "yResult " + yResult);
		return yResult;
	}

}
