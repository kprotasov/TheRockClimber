package com.capcorn.games.therockclimber.graphics;

/**
 * User: kprotasov
 * Date: 20.06.2017
 * Time: 23:40
 */

public enum StaticResourceNames {

    BACKGROUND("background.png", 1000, 1779),
    BLACK_TILE("black_tile_6.png", 200, 480),
    WHITE_TILE("white_tile_4.png", 200, 480),
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
