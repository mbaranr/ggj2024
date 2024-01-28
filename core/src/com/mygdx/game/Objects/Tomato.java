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

public class Tomato extends B2Sprite implements Subscriber {

    private int holeID;
    private int x;
    private int y;
    private float initialX;
    private float initialY;

    private boolean tomatoHit;
    private Hole directedHole;


    private final ResourceManager resourceManager;


    public Tomato(World world, int x, int y, Hole hole) {

        this.x = x;
        this.y = y;
        this.initialX = x / Constants.PPM;
        this.initialY = y / Constants.PPM;

        this.directedHole = hole;
        this.tomatoHit = false;

        this.resourceManager = new ResourceManager();

        BodyDef bdef = new BodyDef();
        bdef.position.set(initialX, initialY);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        loadSprites();
        setAnimation(TextureRegion.split(resourceManager.getTexture("just_tomato"), 32, 32)[0], 1/20f, false, 1.25f, 1);
        

        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        //Create body fixture
        polygonShape.setAsBox(18 / Constants.PPM, 10 / Constants.PPM, new Vector2(0, 0), 0);
        fdef.shape = polygonShape;
        fdef.friction = 0;
        fdef.isSensor = true;
        //fdef.filter.categoryBits = Constants.BIT_ITEM;
        b2body.createFixture(fdef).setUserData("tomato");


    }

    public void loadSprites() {
        resourceManager.loadTexture("tomato.png", "tomato");
        resourceManager.loadTexture("just_tomato.png", "just_tomato");
    }

    public void notify(String flag) {
        tomatoHit = true;
    }

    public void launchTomato(MyTimer launchtimer, Vector2 hole, Hole thisHole) {

        directedHole = thisHole;

        Vector2 tomatoVector = tomatoVector();

        Vector2 directionVector = new Vector2((hole.x - tomatoVector.x), (hole.y - tomatoVector.y));
        directionVector = directionVector.nor().scl(14.0f);

        b2body.applyLinearImpulse(directionVector, b2body.getWorldCenter(), true);
        setAnimation(TextureRegion.split(resourceManager.getTexture("tomato"), 32, 32)[0], 1/40f, true, 1.25f, 1);

        launchtimer.start(thisHole.getDespawntime(), "", this);

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

    public Vector2 tomatoVector() {
        float x = this.getPosition().x;
        float y = this.getPosition().y;

        return new Vector2(x, y);
    }

    public boolean tomatoHit() {
        return this.tomatoHit;
    }
    
}
