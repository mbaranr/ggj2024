package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Game.LOD;
import com.mygdx.game.Handlers.B2WorldHandler;
import com.mygdx.game.Logic.MyContactListener;
import com.mygdx.game.Logic.MyTimer;
import com.mygdx.game.RoleCast.Buffoon;
import com.mygdx.game.Tools.Constants;
import com.mygdx.game.Tools.ResourceManager;


import java.util.concurrent.atomic.AtomicInteger;

public class GameScreen implements Screen {
    private final MyTimer timer;
    private final LOD game;
    private final OrthographicCamera gameCam;
    private final Viewport gamePort;
    private final OrthogonalTiledMapRenderer renderer;
    private final World world;    // World holding all the physical objects
    private final Box2DDebugRenderer b2dr;
    private final Buffoon buffoon;
    public GameScreen(LOD game, ResourceManager resourceManager) {

        this.game = game;

        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());      // Full-screen

        // Creating tiled map
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("chunkyworld.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);
        world = new World(new Vector2(0, 0), true);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.TILE_SIZE * 30 / Constants.PPM, Constants.TILE_SIZE * 17 / Constants.PPM, gameCam);
        gameCam.position.set(2, 77, 0);

        AtomicInteger eidAllocator = new AtomicInteger();
        timer = new MyTimer();

        buffoon = new Buffoon(5, 5, world);

        world.setContactListener(new MyContactListener());
        b2dr = new Box2DDebugRenderer();
        new B2WorldHandler(world, map, resourceManager, timer, eidAllocator);     //Creating world
    }

    @Override
    public void show() {  }

    public void update(float delta) {
        handleInput();
        world.step(1/60f, 6, 2);
        gameCam.position.set(buffoon.getPosition().x, buffoon.getPosition().y, 0);
        gameCam.update();
        timer.update(delta);
    }

    public void handleInput() {
        boolean input = false;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            input = true;
            buffoon.moveUp();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            input = true;
            buffoon.moveDown();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            input = true;
            buffoon.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            input = true;
            buffoon.moveRight();
        }

        if (!input) buffoon.stop();
    }

    public void render(float delta) {

        update(delta);

        // Clearing the screen
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(gameCam);
        renderer.render();

        b2dr.render(world, gameCam.combined);
        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }

}
