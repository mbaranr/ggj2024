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
import com.mygdx.game.Objects.Item;
import com.mygdx.game.RoleCast.Buffoon;
import com.mygdx.game.Scenes.HUD;
import com.mygdx.game.Tools.Constants;
import com.mygdx.game.Tools.ResourceManager;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ChurchScreen extends GameScreen {

    private MyTimer timer;
    private LOD game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer renderer;
    private World world;    // World holding all the physical objects
    private Box2DDebugRenderer b2dr;
    private B2WorldHandler b2wh;
    private Buffoon buffoon;
    private ArrayList<Item> itemList;
    private HUD HUD;

    public ChurchScreen(LOD game, ResourceManager resourceManager, HUD HUD, MyTimer timer) {

        super(game, HUD, timer);

        // Creating tiled map
        mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("TiledMaps/Church/church.tmx");

        AtomicInteger eidAllocator = new AtomicInteger();

        // World
        world = new World(new Vector2(0, 0), true);

        // Buffoon stuff
        itemList = new ArrayList<>();
        buffoon = new Buffoon(300, 870, world, resourceManager);

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.TILE_SIZE * 30 / Constants.PPM, Constants.TILE_SIZE * 17 / Constants.PPM, gameCam);
        gameCam.position.set(2, 77, 0);
        world.setContactListener(new MyContactListener(buffoon));
        b2dr = new Box2DDebugRenderer();
        b2wh = new B2WorldHandler(world, map, resourceManager, timer, game.batch, game);
    }


    @Override
    public void show() {  }

    public void update(float delta) {
        handleInput();
        world.step(1/60f, 6, 2);
        gameCam.position.set(buffoon.getPosition().x, buffoon.getPosition().y, 0);
        gameCam.update();
        timer.update(delta);
        buffoon.update(delta);
        System.out.println(buffoon.getPosition());
        if (HUD.getTime() == 17) game.changeScreen("castle");
    }

    public void handleInput() {
        boolean input = false;
        boolean stopX = true;
        boolean stopY = true;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            input = true;
            stopY = false;
            buffoon.moveUp();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            input = true;
            stopY = false;
            buffoon.moveDown();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            input = true;
            stopX = false;
            buffoon.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            input = true;
            stopX = false;
            buffoon.moveRight();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            for(Item item : itemList) {
                if(item.canBeGrabbed()) {
                    buffoon.getPlayerList().add(item);
                    System.out.println("Item was grabbed by the player");
                }
            }
        }

        if (!input) buffoon.stop();
        if (stopY) buffoon.stopY();
        if (stopX) buffoon.stopX();
    }

    public void render(float delta) {

        update(delta);

        // Clearing the screen
        Gdx.gl.glClearColor( 0, 0, 0, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(gameCam);
        renderer.render();

        buffoon.render(game.batch);

        b2dr.render(world, gameCam.combined);
        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.begin();
        game.batch.end();

        game.batch.setProjectionMatrix(HUD.stage.getCamera().combined);
        HUD.stage.draw();

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
