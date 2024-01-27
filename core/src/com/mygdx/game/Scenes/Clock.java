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
    private int hour;

    public Clock() {
        hour = 9;
        time = FancyFontHelper.getInstance().getFont(Color.RED, 60);
    }

    public void render(SpriteBatch batch) {

    }

    @Override
    public void notify(String flag) {

    }
}
