package com.capcorn.games.therockclimber.characters;

import com.capcorn.games.therockclimber.R;
import com.capcorn.games.therockclimber.graphics.AnimatedResourceNames;

/**
 * User: kprotasov
 * Date: 06.11.2017
 * Time: 13:55
 */

public enum Characters {

    BASE(AnimatedResourceNames.BASE_CHARACTER.getName(), 0, R.drawable.character_base, true, "default"), // as default
    FORESTER(AnimatedResourceNames.FORESTER_CHARACTER.getName(), 1000, R.drawable.character_forester, false, "forester"),
    ZOMBIE(AnimatedResourceNames.ZOMBIE_CHARACTER.getName(), 2000, R.drawable.character_zombie, false, "zombie");

    private final String name;
    private final int price;
    private final int imageResourceId;
    private boolean selected;
    private String characterName; // depends on @AnimatedResourceNames

    Characters(final String name, final int price, final int imageResourceId, final boolean selected, final String characterName) {
        this.name = name;
        this.price = price;
        this.imageResourceId = imageResourceId;
        this.selected = selected;
        this.characterName = characterName;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
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
}
