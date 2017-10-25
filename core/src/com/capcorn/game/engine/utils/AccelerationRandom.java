package com.capcorn.game.engine.utils;

/**
 * User: kprotasov
 * Date: 09.02.2017
 * Time: 17:41
 */

import java.util.Random;

/**
 * Вызывает событие с вероятностью. Вероятность увеличивается с течением времени пока не наступит событие.
 */
public class AccelerationRandom {

	private static final int RANDOM_MAX = 100;
	private final Random random;
	private boolean eventIsReached = false;
	private int defaultPercent;
	private int maxPercent;
	private int currentPercent;
	private int accelerationSpeed;

	public AccelerationRandom(final int defaultPercent, final int maxPercent, final int accelerationSpeed) {
		random = new Random();
		this.defaultPercent = defaultPercent;
		this.maxPercent = maxPercent;
		this.accelerationSpeed = accelerationSpeed;
	}

	public void reset(final int defaultPercent, final int maxPercent, final int accelerationSpeed) {
		this.defaultPercent = defaultPercent;
		this.maxPercent = maxPercent;
		this.accelerationSpeed = accelerationSpeed;
	}

	// возвращает true с вероятностью percent
	// вероятность увеличивается
	public boolean getRandom() {

		if (eventIsReached) {
			currentPercent = defaultPercent;
			eventIsReached = false;
		} else {
			currentPercent = currentPercent >= maxPercent ? maxPercent : currentPercent + accelerationSpeed;
		}
		final int variant = random.nextInt(RANDOM_MAX);
		if (variant <= currentPercent) {
			eventIsReached = true;
		} else {
			eventIsReached = false;
		}
		return eventIsReached;
	}

}
