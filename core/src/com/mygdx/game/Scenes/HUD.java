package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Interfaces.Subscriber;
import com.mygdx.game.Logic.MyTimer;
import com.mygdx.game.Tools.FancyFontHelper;

public class HUD implements Subscriber {
    public Stage stage;
    private Viewport viewport;
    private MyTimer timer;
    boolean paused;
    private int hour;
    private int minutes;
    private Label timeLabel;
    private Label coinLabel;
    private Image coinImage;
    private int coinCount;
    public HUD(MyTimer timer, SpriteBatch batch) {
        this.timer = timer;

        viewport = new FitViewport(1000, 1000, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        hour = 9;
        minutes = 0;

        timeLabel = new Label(String.format("%02d%s", hour % 12 == 0 ? 12 : hour % 12, hour < 12 ? " AM" : " PM"), new Label.LabelStyle(FancyFontHelper.getInstance().getFont(Color.RED, 60), new Color(0.5f, 0, 0, 1)));
        timeLabel.setAlignment(Align.left);

        coinLabel = new Label(Integer.toString(coinCount), new Label.LabelStyle(FancyFontHelper.getInstance().getFont(Color.YELLOW, 30), new Color(1, 1, 1, 1)));
        coinLabel.setAlignment(Align.right);

        coinImage = new Image(new Texture(Gdx.files.internal("Items/coin.png")));

        table.add(timeLabel).padRight(550).padTop(40).expandX();
        table.add(coinImage).width(40).height(60).padTop(40).padRight(10);
        table.add(coinLabel).padRight(40).padTop(40);
        stage.addActor(table);

        coinCount = 0;


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
            hour++;
        }
        timeLabel.setText(String.format("%02d%s", hour % 12 == 0 ? 12 : hour % 12, hour < 12 ? " AM" : " PM"));
        timer.start(1f, "minute_passes", this);
    }

    public int getTime() {
        return hour;
    }
    public void updateCoinCount() {
        coinCount++;
        coinLabel.setText(coinCount);
    }
}
