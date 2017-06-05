package com.capcorn.game.engine.utils;

import java.util.Random;

/**
 * Created by kprotasov on 21.08.2016.
 */
public class PercentRandom {

    private static final int RANDOM_MAX = 100;
    private final Random random;

    public PercentRandom() {
        random = new Random();
    }

    // возвращает true с вероятностью percent
    public boolean getRandom(final int percent) {
        final int variant = random.nextInt(RANDOM_MAX);
        if (variant <= percent) {
            return true;
        } else {
            return false;
        }
    }

}
