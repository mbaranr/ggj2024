package com.mygdx.game.Tools;

public class Constants {
    public static final int TILE_SIZE = 16;
    public static final float PPM = 100;
    public static final float MAX_SPEED = 1.6f;

    public enum COMEDYTYPE {
        ROMANTIC, SELF_DEPRECATING, TRAGIC, DARK, ABSURD
    }

    // Animation state flag
    public enum ASTATE {
        RUN_UP, RUN_DOWN, RUN_LEFT, RUN_RIGHT, IDLE_DOWN, IDLE_UP, IDLE_LEFT, IDLE_RIGHT
    }

    // Bits for collision masking
    public static final short BIT_GROUND = 2;   // Includes ground and walls (because sometimes the ground can be a wall)
    public static final short BIT_TREE = 4;
    public static final short BIT_ITEM = 8;
    public static final short BIT_TRANSPARENCY = 16;
    public static final short BIT_NPC = 32;
    public static final short BIT_DOOR = 64;
}
