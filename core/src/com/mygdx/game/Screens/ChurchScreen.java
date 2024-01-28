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
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

public class ChurchScreen extends GameScreen {

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private OrthogonalTiledMapRenderer renderer;
    private World world;    // World holding all the physical objects
    private Box2DDebugRenderer b2dr;
    private B2WorldHandler b2wh;
    private Buffoon buffoon;
    private LinkedList<NPC> npcs;
    private LinkedList<Item> itemList;
    private ShaderHandler shaderHandler;

    public ChurchScreen(LOD game, ResourceManager resourceManager, HUD HUD, MyTimer timer) {

        super(game, HUD, timer);

        // Creating tiled map
        TmxMapLoader mapLoader = new TmxMapLoader();
        TiledMap map = mapLoader.load("TiledMaps/Church/church.tmx");


        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);
        world = new World(new Vector2(0, 0), true);
        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.TILE_SIZE * 30 / Constants.PPM, Constants.TILE_SIZE * 17 / Constants.PPM, gameCam);
        gameCam.position.set(2, 77, 0);

        // World
        world = new World(new Vector2(0, 0), true);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / Constants.PPM);

        // Buffoon stuff
        itemList = new LinkedList<>();
        Item hooka = new Item(1025, 1126, world, 0.1f, null, null, null, null, 1, "Items/Hookah.png");
        Item ring = new Item(1025, 1126, world, 0.1f, null, null, null, null, 1, "Items/Ring_withshit.png");

        itemList.add(hooka);
        itemList.add(ring);


        buffoon = new Buffoon(300, 920, world, resourceManager);

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(Constants.TILE_SIZE * 30 / Constants.PPM, Constants.TILE_SIZE * 17 / Constants.PPM, gameCam);
        gameCam.position.set(2, 77, 0);
        world.setContactListener(new MyContactListener(itemList, buffoon, game));
        b2dr = new Box2DDebugRenderer();
        b2wh = new B2WorldHandler(world, map, resourceManager, timer, game.batch, game);
    

        npcs = new LinkedList<>();

        shaderHandler = new ShaderHandler(game.batch);
        npcs.add(new NPC(450, 1300, world, "nun", resourceManager, game));
        npcs.add(new NPC(500, 1050, world, "nun", resourceManager, game));
        npcs.add(new NPC(150, 1020, world, "nun", resourceManager, game));

        world.setContactListener(new MyContactListener(itemList, buffoon, game));
        b2dr = new Box2DDebugRenderer();
        new B2WorldHandler(world, map, resourceManager, timer, game.batch, game);     //Creating world

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
        for (NPC npc : npcs) {
            npc.update(delta);
        }
        shaderHandler.update(delta);
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
            LinkedList<Item> toRemove = new LinkedList<>();
            for(Item item : itemList) {
                if(item.canBeGrabbed()) {
                    System.out.println(2233);
                    buffoon.getPlayerList().add(item);
                    toRemove.add(item);
                }
            }
            for (Item item : toRemove) {
                itemList.remove(item);
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
        for (NPC npc : npcs) {
            npc.render(game.batch);
        }

        b2dr.render(world, gameCam.combined);
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
