package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Game.LOD;
import com.mygdx.game.Logic.MyTimer;
import com.mygdx.game.Scenes.CutScene;
import com.mygdx.game.Scenes.HUD;

public abstract class GameScreen implements Screen {

    protected final MyTimer timer;
    protected final LOD game;
    protected final HUD HUD;

    public GameScreen(LOD game, HUD HUD, MyTimer timer) {

        this.game = game;
        this.timer = timer;
        this.HUD = HUD;

        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());      // Full-screen
    }

    public abstract void render(float delta);
    public abstract void resize(int width, int height);
    public abstract void pause();
    public abstract void resume();
    public abstract void hide();
    public abstract void dispose();
}
