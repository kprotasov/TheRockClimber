package com.capcorn.games.therockclimber.graphics;

/**
 * User: kprotasov
 * Date: 20.06.2017
 * Time: 23:40
 */

public enum StaticResourceNames {

    BACKGROUND("background.png", 1000, 1779),
    //BLACK_TILE_ATLAS("black_tile_atlas.png", 400, 480),
    //WHITE_TILE("white_tile_4.png", 200, 480),
    BLACK_TILE_ATLAS("black_tile_4_atlas.png", 400, 480),
    WHITE_TILE("white_tile_3.png", 200, 480),
    LOOSING_GAME_DIALOG_BACKGROUND("loosing_game_background.png", 900, 570),
    GOLD("gold.png", 85, 85);

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
