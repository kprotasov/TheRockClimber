package com.capcorn.games.therockclimber.settings.store;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * User: kprotasov
 * Date: 01.10.2017
 * Time: 11:58
 */

public class MoneyStore {

    public static final String MONEY_PREFERENCES_NAME = "com.capcorn.games.therockclimber.settings.store.MONEY_PREFERENCES_NAME";
    public static final String MONEY_KEY = "com.capcorn.games.therockclimber.settings.store.MONEY_KEY";

    public long getTotalMoney() {
        final Preferences preferences = Gdx.app.getPreferences(MONEY_PREFERENCES_NAME);
        return preferences.getLong(MONEY_KEY);
    }

    public void setTotalMoney(final long money) {
        final Preferences preferences = Gdx.app.getPreferences(MONEY_PREFERENCES_NAME);
        preferences.putLong(MONEY_KEY, money);
        preferences.flush();
    }

}
