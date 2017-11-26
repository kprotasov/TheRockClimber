package com.capcorn.games.therockclimber.graphics;

/**
 * User: kprotasov
 * Date: 17.06.2017
 * Time: 15:54
 */

public enum AnimatedResourceNames {

    BASE_CHARACTER("character.png", 6, 4, 200, 393),
    FORESTER_CHARACTER("character_forester.png", 6, 4, 200, 393),
    ZOMBIE_CHARACTER("character_zombie.png", 6, 4, 200, 393),
    STONE("stone_spritesheet.png", 11, 1, 100, 100);

    public static AnimatedResourceNames getResourceByName(final String name) {
        for (AnimatedResourceNames animatedResourceNames : AnimatedResourceNames.values()) {
            if (name.equalsIgnoreCase(animatedResourceNames.getName())) {
                return animatedResourceNames;
            }
        }
        return BASE_CHARACTER;
    }

    private final String name;
    private final int horizontalCount;
    private final int verticalCount;
    private final int width;
    private final int height;

    AnimatedResourceNames(final String name, final int horizontalCount, final int verticalCount, final int width, final int height) {
        this.name = name;
        this.horizontalCount = horizontalCount;
        this.verticalCount = verticalCount;
        this.width = width;
        this.height = height;
    }

    public java.lang.String getName() {
        return name;
    }

    public int getHorizontalCount() {
        return horizontalCount;
    }

    public int getVerticalCount() {
        return verticalCount;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
