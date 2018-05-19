package com.capcorn.games.therockclimber.characters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * Created by DCOR on 29.11.2017.
 */

public class CharacterBuyStore {

    private static final String CHARACTER_BUY_STORE_FILE = "com.capcorn.games.therockclimber.characters.CHARACTER_BUY_STORE_FILE";
    private static final String RECORD_CHARACTER_BUY_STORE = "com.capcorn.games.therockclimber.characters.RECORD_CHARACTER_BUY_STORE";
    private static final String WRONG_STRING = "";

    private final Context context;
    private final Gson gson;

    public CharacterBuyStore(final Context context) {
        this.context = context;
        gson = new Gson();
    }

    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(CHARACTER_BUY_STORE_FILE, Activity.MODE_PRIVATE);
    }

    public Characters[] load() {
        final String charactersJson = getSharedPreferences().getString(RECORD_CHARACTER_BUY_STORE, WRONG_STRING);
        if (charactersJson.equals(WRONG_STRING)) {
            return Characters.values();
        }
        return convertJsonToCharacters(gson.fromJson(charactersJson, Characters.CharacterJson[].class));
    }

    public void save(final Characters[] characters) {
        final String charactersString = gson.toJson(convertCharactersToJson(characters));
        getSharedPreferences().edit().putString(RECORD_CHARACTER_BUY_STORE, charactersString).apply();
    }

    private Characters.CharacterJson[] convertCharactersToJson(final Characters[] characters) {
        final Characters.CharacterJson[] jsons = new Characters.CharacterJson[characters.length];
        for (int i = 0; i < characters.length; i++) {
            jsons[i] = characters[i].copy();
        }
        return jsons;
    }

    private Characters[] convertJsonToCharacters(final Characters.CharacterJson[] jsons) {
        final Characters[] characters = new Characters[jsons.length];
        for (int i = 0; i < jsons.length; i++) {
            characters[i] = Characters.BASE.paste(jsons[i]);
        }
        return characters;
    }

}
