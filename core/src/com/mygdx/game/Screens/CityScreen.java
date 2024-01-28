package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
import com.mygdx.game.Handlers.ShaderHandler;
import com.mygdx.game.Logic.MyContactListener;
import com.mygdx.game.Logic.MyTimer;
import com.mygdx.game.Objects.Item;
import com.mygdx.game.RoleCast.Buffoon;
import com.mygdx.game.RoleCast.NPC;
import com.mygdx.game.Scenes.HUD;
import com.mygdx.game.Tools.Constants;
import com.mygdx.game.Tools.ResourceManager;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CityScreen extends GameScreen {
    private LOD game;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private OrthogonalTiledMapRenderer renderer;
    private World world;    // World holding all the physical objects
    private Box2DDebugRenderer b2dr;
    private B2WorldHandler b2wh;
    private Buffoon buffoon;
    private MyTimer timer;
    private HUD HUD;
    private NPC merchant;
    private NPC guard1;
    private NPC guard2;
    private ArrayList<Item> itemList;
    private ShaderHandler shaderHandler;

    public CityScreen(LOD game, ResourceManager resourceManager, HUD HUD, MyTimer timer) {

        super(game, HUD, timer);

        // Creating tiled map
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("TiledMaps/City/chunkyworld.tmx");

        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);
        world = new World(new Vector2(0, 0), true);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.TILE_SIZE * 30 / Constants.PPM, Constants.TILE_SIZE * 17 / Constants.PPM, gameCam);
        gameCam.position.set(2, 77, 0);

        itemList = new ArrayList<>();
        Item underwear = new Item(5700, 7420, world, 0.1f, null, null, null, null, 1, "Items/underwear.png");
        itemList.add(underwear);

        shaderHandler = new ShaderHandler(game.batch);
        buffoon = new Buffoon(5640, 7520, world, resourceManager);
        merchant = new NPC(5700, 7000, world, "merchant", resourceManager, game);
        guard1 = new NPC(5626, 7519, world, "guard", resourceManager, game);
        guard2 = new NPC(5700, 7519, world, "guard", resourceManager, game);

        world.setContactListener(new MyContactListener(itemList, buffoon));
        b2dr = new Box2DDebugRenderer();
        b2wh = new B2WorldHandler(world, map, resourceManager, timer, game.batch, game);     //Creating world

        HUD.start();
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
        merchant.update(delta);
        guard1.update(delta);
        guard2.update(delta);
        shaderHandler.update(delta);
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
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            input = true;
            stopX = false;
            stopY = false;
            buffoon.moveUpLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            input = true;
            stopX = false;
            stopY = false;
            buffoon.moveUpRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            input = true;
            stopX = false;
            stopY = false;
            buffoon.moveDownLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            input = true;
            stopX = false;
            stopY = false;
            buffoon.moveDownRight();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            for(Item item : itemList) {
                if(item.canBeGrabbed()) {
                    buffoon.getPlayerList().add(item);
                    System.out.println("Item was grabbed by the player");
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            if (buffoon.getTargetnpc() != null) buffoon.getTargetnpc().interact();
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

        game.batch.setProjectionMatrix(HUD.stage.getCamera().combined);
        HUD.stage.draw();

        game.batch.setProjectionMatrix(gameCam.combined);

        game.batch.setShader(shaderHandler.getItemShader());
        for (Item item : itemList) {
            item.render(game.batch);
        }
        game.batch.setShader(null);

        buffoon.render(game.batch);
        merchant.render(game.batch);
        guard1.render(game.batch);
        guard2.render(game.batch);


        //b2dr.render(world, gameCam.combined);
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
