package com.mygdx.game.Tools;

public class Constants {
    public static final int TILE_SIZE = 16;
    public static final float PPM = 100;
    public static final float MAX_SPEED = 1.6f;
    // Movement state Flag
    public enum MSTATE {
        LEFT, RIGHT,
        PREV, // Keep previous direction
        HSTILL, // Horizontal still
        FSTILL; // Full still
    }

    // Animation state flag
    public enum ASTATE {
        RUN, JUMP, IDLE, FALL, LAND
    }

    // Bits for collision masking
    public static final short BIT_GROUND = 2;   // Includes ground and walls (because sometimes the ground can be a wall)
    public static final short BIT_TREE = 4;

}
