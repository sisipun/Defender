package io.cucumber.utils.constants;

public class Constants {

    // Screen
    public static final float SCREEN_WIDTH = 1600f;
    public static final float SCREEN_HEIGHT = 1000f;

    // Map
    public static final int MAP_BORDER_SIZE = 2;
    public static final float BLOCK_SIZE = 50;
    public static final float BLOCK_ZONE_SIZE = 3;

    // Defender
    public static final float DEFENDER_SIZE = 50;
    public static final int DEFENDER_COST = 100;
    public static final float DEFENDER_ZONE_SIZE = 200;
    public static final float DEFENDER_BULLET_SIZE = 10;
    public static final float DEFENDER_BULLET_SPEED = 5;
    public static final float DEFENDER_BULLET_POWER = 2;

    public static final float DEFENDER_SMALL_SIZE = 25;
    public static final int DEFENDER_SMALL_COST = 50;
    public static final float DEFENDER_SMALL_ZONE_SIZE = 100;
    public static final float DEFENDER_SMALL_BULLET_SIZE = 10;
    public static final float DEFENDER_SMALL_BULLET_SPEED = 5;
    public static final float DEFENDER_SMALL_BULLET_POWER = 5;

    // Enemy
    public static final float ENEMY_SIZE = 50;
    public static final float ENEMY_SPEED = 100;
    public static final float ENEMY_HEALTH = 20;
    public static final float ENEMY_POWER = 10;
    public static final int ENEMY_COST = 50;

    public static final float ENEMY_SMALL_SIZE = 25;
    public static final float ENEMY_SMALL_SPEED = 150;
    public static final float ENEMY_SMALL_HEALTH = 10;
    public static final float ENEMY_SMALL_POWER = 40;
    public static final int ENEMY_SMALL_COST = 25;

    // Zone
    public static final float ZONE_ALPHA = 0.5f;

    // UI
    public static final float HEALTH_HEIGHT = SCREEN_HEIGHT / 64;
    public static final float TIMER_HEIGHT = SCREEN_HEIGHT / 64;
    public static final float TIMER_EVENT_SIZE = SCREEN_HEIGHT / 64;
    public static final float TIMER_ALPHA = 0.5f;
    public static final float MENU_HEIGHT = SCREEN_HEIGHT / 8 - HEALTH_HEIGHT;
    public static final float GAME_UI_HEIGHT = MENU_HEIGHT + HEALTH_HEIGHT;

    // Fonts
    public static final String DEFAULT_FONT = "fonts/font.ttf";


    private Constants() {
    }

}
