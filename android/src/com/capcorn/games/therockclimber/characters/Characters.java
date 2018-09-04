package com.capcorn.games.therockclimber.characters;

import com.capcorn.games.therockclimber.R;
import com.capcorn.games.therockclimber.graphics.AnimatedResourceNames;
import com.google.gson.annotations.SerializedName;

/**
 * User: kprotasov
 * Date: 06.11.2017
 * Time: 13:55
 */

public enum Characters {

    BASE(AnimatedResourceNames.BASE_CHARACTER.getName(), 0, R.drawable.character_base,
            R.drawable.character_base, true, "john", true), // as default
    FORESTER(AnimatedResourceNames.FORESTER_CHARACTER.getName(), 1000, R.drawable.character_forester,
            R.drawable.character_forester_gray, false, "forester", false),
    ZOMBIE(AnimatedResourceNames.ZOMBIE_CHARACTER.getName(), 2000, R.drawable.character_zombie,
            R.drawable.character_zombie_gray, false, "zombie", false),
    SANTA_CLAUS(AnimatedResourceNames.SANTA_CLAUS_CHARACTER.getName(), 3000, R.drawable.character_santa,
            R.drawable.character_santa_gray, false, "santa claus", false),
    DRACULA(AnimatedResourceNames.DRACULA_CHARACTER.getName(), 4000, R.drawable.character_dracula,
            R.drawable.character_dracula_gray, false, "dracula", false);

    public static String getNamesList() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        Characters[] values = Characters.values();
        for (Characters character : values) {
            result.append("\"" + character.name() + "\"" + ",");
        }
        result.replace(result.length() - 1, result.length(), "");
        result.append("]");
        return result.toString();
    }

    private String name;
    private long price;
    private int imageResourceId;
    private int notBuyedResourceId;
    private boolean selected;
    private String characterName; // depends on @AnimatedResourceNames
    private boolean isBayed;

    Characters(final String name, final long price, final int imageResourceId, final int notBuyedResourceId,
               final boolean selected, final String characterName, final boolean isBayed) {
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.notBuyedResourceId = notBuyedResourceId;
        this.selected = selected;
        this.characterName = characterName;
        this.isBayed = isBayed;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getNotBuyedResourceId() {
        return notBuyedResourceId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getCharacterName() {
        return characterName;
    }

    public boolean isBayed() {
        return isBayed;
    }

    public void setBayed(boolean bayed) {
        isBayed = bayed;
    }

    public Characters[] getValues() {
        return values();
    }

    public CharacterJson copy() {
        return new CharacterJson(this.name, this.price, this.imageResourceId, this.notBuyedResourceId,
                this.selected, this.characterName, this.isBayed);
    }

    public Characters paste(final CharacterJson characterJson) {
        Characters character;
        if (characterJson.name.equals(AnimatedResourceNames.FORESTER_CHARACTER.getName())) {
            character = Characters.FORESTER;
        } else if (characterJson.name.equals(AnimatedResourceNames.ZOMBIE_CHARACTER.getName())) {
            character = Characters.ZOMBIE;
        } else if (characterJson.name.equals(AnimatedResourceNames.SANTA_CLAUS_CHARACTER.getName())) {
            character = Characters.SANTA_CLAUS;
        } else if (characterJson.name.equals(AnimatedResourceNames.DRACULA_CHARACTER.getName())) {
            character = Characters.DRACULA;
        } else {
            character = Characters.BASE;
        }
        character.name = characterJson.name;
        character.price = characterJson.price;
        character.imageResourceId = characterJson.imageResourceId;
        character.notBuyedResourceId = characterJson.notBuyedResourceId;
        character.selected = characterJson.selected;
        character.characterName = characterJson.characterName;
        character.isBayed = characterJson.isBayed;
        return character;
    }

    public class CharacterJson {

        private String name;
        private long price;
        private int imageResourceId;
        private int notBuyedResourceId;
        private boolean selected;
        private String characterName; // depends on @AnimatedResourceNames
        private boolean isBayed;

        public CharacterJson(final String name, final long price, final int imageResourceId, final int notBuyedResourceId,
                             final boolean selected, final String characterName, final boolean isBayed) {
            this.name = name;
            this.price = price;
            this.imageResourceId = imageResourceId;
            this.notBuyedResourceId = notBuyedResourceId;
            this.selected = selected;
            this.characterName = characterName;
            this.isBayed = isBayed;
        }



        /*public Character paste(final CharacterJson characterJson) {
            return new Character(characterJson.name, characterJson.price, characterJson.imageResourceId,
                    characterJson.notBuyedResourceId, characterJson.selected, characterJson.characterName, characterJson.isBayed)
        }*/

    }
}
