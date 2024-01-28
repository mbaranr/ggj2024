package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Game.LOD;
import com.mygdx.game.Interfaces.Subscriber;
import com.mygdx.game.Logic.MyTimer;
import com.mygdx.game.Tools.Constants;
import com.mygdx.game.Tools.ResourceManager;
import com.mygdx.game.Objects.Hole;
import com.mygdx.game.Objects.Tomato;
import com.mygdx.game.Scenes.HUD;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class OpenScreen extends GameScreen {

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private World world;    // World holding all the physical objects
    private ArrayList<Hole> holeList;
    
    private Texture backgroundTexture;
    private Sprite backgroundSprite;


    public OpenScreen(LOD game, ResourceManager resourceManager, HUD HUD, MyTimer timer) {

        super(game, HUD, timer);


        // Creating tiled map
        backgroundTexture = new Texture("background_here.png"); 
        backgroundSprite = new Sprite(backgroundTexture);


        world = new World(new Vector2(0, 0), true);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.TILE_SIZE * 30 / Constants.PPM, Constants.TILE_SIZE * 17 / Constants.PPM, gameCam);
        gameCam.position.set(2, 77, 0);
        
    }

    public void update(float delta) {

        handleInput();
        world.step(1/60f, 6, 2);
        gameCam.position.set(0, 0, 0);
        gameCam.update();
        timer.update(delta);
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            System.exit(0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            game.changeScreen("city");
        }
    }

    @Override
    public void render(float delta) {

        update(delta);

        // Clearing the screen
        Gdx.gl.glClearColor( 1, 1, 1, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);
    

        game.batch.begin();
        game.batch.draw(backgroundSprite, -2*1.3f, -1*1.3f, 5*1.1f, 3*0.88f);
        game.batch.end();
        

        //b2dr.render(world, gameCam.combined);
        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}

}
