package com.mygdx.game.Objects;

import javax.swing.Timer;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Interfaces.Subscriber;
import com.mygdx.game.Sprites.B2Sprite;
import com.mygdx.game.Tools.Constants;
import com.mygdx.game.Tools.ResourceManager;
import com.mygdx.game.Logic.MyTimer;

public class Hole extends B2Sprite implements Subscriber {

    private boolean buffoonHead;
    private int holeID;
    private int x;
    private int y;
    private float despawnTime;

    private final ResourceManager resourceManager;


    public Hole(World world, int x, int y, boolean head, int holeID, float despawnTime) {
        
        this.buffoonHead = head;
        this.holeID = holeID;
        this.x = x;
        this.y = y;
        this.despawnTime = despawnTime; 
        this.resourceManager = new ResourceManager();

        BodyDef bdef = new BodyDef();
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        loadSprites();
        setAnimation(TextureRegion.split(resourceManager.getTexture("empty_hole"), 32, 32)[0], 1/20f, false, 1.25f, 1);
        

        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        //Create body fixture
        polygonShape.setAsBox(18 / Constants.PPM, 10 / Constants.PPM, new Vector2(0, 0), 0);
        fdef.shape = polygonShape;
        fdef.friction = 0;
        fdef.isSensor = true;
        //fdef.filter.categoryBits = Constants.BIT_ITEM;
        b2body.createFixture(fdef).setUserData("hole_" + holeID);

    }

    public void loadSprites() {
        resourceManager.loadTexture("hole.png", "empty_hole");
        resourceManager.loadTexture("head.png", "jester_peek");
        resourceManager.loadTexture("tomato_head.png", "jester_tomatoed");
    }

    public void notify(String flag) {
        setAnimation(TextureRegion.split(resourceManager.getTexture("empty_hole"), 32, 32)[0], 1/20f, false, 1.25f, 1);
        buffoonHead = false;
    }

    public void peek(MyTimer peekTimer_) {
        buffoonHead = true;
        setAnimation(TextureRegion.split(resourceManager.getTexture("jester_peek"), 32, 32)[0], 1/27f, false, 1.25f, 1);
        peekTimer_.start(1/1.2f, "", this);
    }

    public void get_tomatoed(MyTimer peekTimer_) {
        setAnimation(TextureRegion.split(resourceManager.getTexture("jester_tomatoed"), 32, 32)[0], 1/27f, false, 1.25f, 1);
        peekTimer_.start(1f, "", this);
    }

    public int getHoleID() {
        return this.holeID;
    }

    public int getHoleX() {
        return this.x;
    }

    public int getHoleY() {
        return this.y;
    }

    public boolean isBuffoonHere() {
        return this.buffoonHead;
    }
    public float getDespawntime() {
        return this.despawnTime;
    }
}