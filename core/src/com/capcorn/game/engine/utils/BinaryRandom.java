package com.capcorn.game.engine.utils;

import java.util.Random;

/**
 * User: kprotasov
 * Date: 09.02.2017
 * Time: 18:05
 */
public class BinaryRandom {

	private static final int RANDOM_MAX = 100;
	private static final int MIDDLE_PERCENT = 50;
	private final Random random;

	public BinaryRandom() {
		random = new Random();
	}

	public boolean getRandom() {
		final int variant = random.nextInt(RANDOM_MAX);
		if (variant <= MIDDLE_PERCENT) {
			return true;
		} else {
			return false;
		}
	}

}
