package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Interfaces.Subscriber;
import com.mygdx.game.Logic.MyTimer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.Tools.FancyFontHelper;

public class Clock implements Subscriber {

    private MyTimer timer;
    private BitmapFont time;
    boolean paused;
    private int hour;
    private int minutes;

    public Clock() {
        hour = 9;
        minutes = 0;
        time = FancyFontHelper.getInstance().getFont(Color.RED, 60);
    }

    public void render(SpriteBatch batch) {

    }

    public void start() {
        timer.start(1f, "minute_passed", this);
    }

    public void setPaused(boolean state) {
        paused = state;
    }

    @Override
    public void notify(String flag) {
        if (paused) return;
        minutes++;
        if (minutes == 60) {
            minutes = 0;
            hour++;
        }
        timer.start(1f, "minute_passes", this);
    }
}
