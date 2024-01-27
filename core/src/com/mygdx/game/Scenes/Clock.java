package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Interfaces.Subscriber;
import com.mygdx.game.Logic.MyTimer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mygdx.game.Tools.FancyFontHelper;

public class Clock implements Subscriber {

    public Stage stage;
    private Viewport viewport;
    private MyTimer timer;
    boolean paused;
    private int hour;
    private int minutes;
    private Label timeLabel;

    public Clock(MyTimer timer, SpriteBatch batch) {
        this.timer = timer;

        viewport = new FitViewport(1000, 1000, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        hour = 9;
        minutes = 0;

        timeLabel = new Label(String.format("%02d", hour) + ":" + String.format("%02d", minutes), new Label.LabelStyle(FancyFontHelper.getInstance().getFont(Color.RED, 70), Color.BLACK));

        table.add(timeLabel).padTop(40);
        stage.addActor(table);
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
        timeLabel.setText(String.format("%02d", hour) + ":" + String.format("%02d", minutes));
        timer.start(1f, "minute_passes", this);
    }
}
