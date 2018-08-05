package com.capcorn.game.engine.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * User: kprotasov
 * Date: 21.10.2017
 * Time: 23:50
 */

public class ProbabilityRandom {

    private static final int MAX_PROBABILITY = 100;
    private final List<Integer> probabilitiesList;
    private List<Integer> randomList;
    private final Random random;

    public ProbabilityRandom(int probability) {
        random = new Random();
        probabilitiesList = new ArrayList<Integer>(MAX_PROBABILITY);
        probabilitiesList.clear();
        for (int i = 0; i < MAX_PROBABILITY; i++) {
            probabilitiesList.add(i);
        }
        Collections.shuffle(probabilitiesList);
        createRandomList(probability);
    }

    public void changeProbability(final int probability) {
        createRandomList(probability);
    }

    public boolean get() {
        final Integer variant = random.nextInt(MAX_PROBABILITY);
        return randomList.contains(variant);
    }

    private void createRandomList(final int probability) {
        randomList = probabilitiesList.subList(0, probability);
    }
}
