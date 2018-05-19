package com.capcorn.games.therockclimber.characters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static com.capcorn.games.therockclimber.settings.store.MoneyStore.MONEY_KEY;
import static com.capcorn.games.therockclimber.settings.store.MoneyStore.MONEY_PREFERENCES_NAME;

/**
 * User: kprotasov
 * Date: 19.11.2017
 * Time: 22:47
 */

/*
depends on com.capcorn.games.therockclimber.settings.store.AppMoneyStore
 */
public class AppMoneyStore {

    private final Context context;

    public AppMoneyStore(final Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(MONEY_PREFERENCES_NAME, Activity.MODE_PRIVATE);
    }

    public long load() {
        return getSharedPreferences().getLong(MONEY_KEY, 0);
    }

    public void save(final long totalMoney) {
        getSharedPreferences().edit().putLong(MONEY_KEY, totalMoney).apply();
    }

}
