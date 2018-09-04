package com.capcorn.games.therockclimber.graphics;

/**
 * User: kprotasov
 * Date: 20.06.2017
 * Time: 23:40
 */

public enum StaticResourceNames {

    BACKGROUND("background.png", 1000, 1779),
    BLACK_TILE_ATLAS("black_tile_atlas.png", 800, 480),
    WHITE_TILE_ATLAS("white_tile_atlas.png", 800, 480),
    RAM("ram.png", 300, 252),
    LOOSING_GAME_DIALOG_BACKGROUND("loosing_game_background.png", 900, 550),
    DIALOG_BUTTON("dialog_button.png", 200, 50),
    GOLD("gold.png", 85, 85),
    BRILLIANCE("brilliance.png", 85, 85),
    RED_BUTTON("red_button.png", 50, 50),
    PAUSE_BUTTON("pause_button.png", 60, 60),
    SNOWFLAKE("snowflake.png", 85, 85);

    private final String name;
    private final int width;
    private final int height;

    StaticResourceNames(final String name, final int width, final int height) {
        this.name = name;
        this.width = width;
        this.height = height;
    }

    public java.lang.String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
