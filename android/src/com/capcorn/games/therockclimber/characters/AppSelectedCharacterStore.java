package com.capcorn.games.therockclimber.characters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import static com.capcorn.games.therockclimber.settings.store.SelectedGameCharacterStore.FILE_SELECTED_CHARACTER;
import static com.capcorn.games.therockclimber.settings.store.SelectedGameCharacterStore.RECORD_SAVED_CHARACTER;

/**
 * User: kprotasov
 * Date: 16.11.2017
 * Time: 18:36
 */

/**
 * depends on @SelectedGameCharacterStore
 */
public class AppSelectedCharacterStore {

    private final Context context;

    public AppSelectedCharacterStore(final Context context) {
        this.context = context;
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(FILE_SELECTED_CHARACTER, Activity.MODE_PRIVATE);
    }

    public String load() {
        return getSharedPreferences().getString(RECORD_SAVED_CHARACTER, Characters.BASE.getName());
    }

    public void save(final String selectedCharacter) {
        getSharedPreferences().edit().putString(RECORD_SAVED_CHARACTER, selectedCharacter).commit();
    }

}
