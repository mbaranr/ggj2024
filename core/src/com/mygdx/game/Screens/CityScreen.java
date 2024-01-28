package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import com.mygdx.game.Scenes.CutScene;
import com.mygdx.game.Scenes.HUD;
import com.mygdx.game.Tools.Constants;
import com.mygdx.game.Tools.ResourceManager;
import java.util.ArrayList;
import java.util.LinkedList;

public class CityScreen extends GameScreen {
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private OrthogonalTiledMapRenderer renderer;
    private World world;    // World holding all the physical objects
    private Box2DDebugRenderer b2dr;
    private B2WorldHandler b2wh;
    private Buffoon buffoon;
    private LinkedList<NPC> npcs;
    private LinkedList<Item> itemList;
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

        itemList = new LinkedList<>();
        Item underwear = new Item(5700, 7420, world, 0.1f, null, null, null, null, 1, "Items/underwear.png");
        itemList.add(underwear);

        npcs = new LinkedList<>();
        shaderHandler = new ShaderHandler(game.batch);
        buffoon = new Buffoon(5640, 7520, world, resourceManager);
        npcs.add(new NPC(5700, 7000, world, "merchant", resourceManager, game));
        npcs.add(new NPC(5626, 7519, world, "guard", resourceManager, game));
        npcs.add(new NPC(5700, 7519, world, "guard", resourceManager, game));
        npcs.add(new NPC(4659, 7320, world, "farmer", resourceManager, game));

        world.setContactListener(new MyContactListener(buffoon));
        b2dr = new Box2DDebugRenderer();
        b2wh = new B2WorldHandler(world, map, resourceManager, timer, game.batch, game);     //Creating world

        HUD.start();

        game.cutScene = new CutScene(game.batch, "Go make me laugh!");
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

        if (game.cutScene != null) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
                game.cutScene = null;
            }
            return;
        }

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

        game.batch.setProjectionMatrix(gameCam.combined);

        buffoon.render(game.batch);
        for (NPC npc : npcs) {
            npc.render(game.batch);
        }

        if (game.cutScene != null) {
            game.batch.setProjectionMatrix(gameCam.combined);
            game.batch.begin();
            game.batch.draw(new Texture(Gdx.files.internal("Items/black.png")), gameCam.position.x - 2f, gameCam.position.y - 1.2f , 4, 1f);
            game.batch.end();
            game.batch.setProjectionMatrix(game.cutScene.stage.getCamera().combined);
            game.cutScene.stage.draw();
        }

        game.batch.setProjectionMatrix(gameCam.combined);
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
