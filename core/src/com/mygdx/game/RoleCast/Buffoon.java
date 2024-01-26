package com.mygdx.game.RoleCast;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Objects.Item;
import com.mygdx.game.Sprites.B2Sprite;
import com.mygdx.game.Tools.Constants;
import com.mygdx.game.Tools.ResourceManager;
import java.util.LinkedList;

public class Buffoon extends B2Sprite {

    LinkedList<Item> items;
    private Constants.ASTATE currAState;     // Current animation state
    private Constants.ASTATE prevAState;     // Previous animation state
    private ResourceManager resourceManager;

    public Buffoon(int x, int y, World world, ResourceManager resourceManager) {

        this.resourceManager = resourceManager;
        currAState = Constants.ASTATE.RUN_UP;
        prevAState = Constants.ASTATE.RUN_UP;

        loadSprites();

        setAnimation(TextureRegion.split(resourceManager.getTexture("player_run"), 32, 32)[0], 1/14f, false, 1.25f);

        BodyDef bdef = new BodyDef();
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        //Create body fixture
        polygonShape.setAsBox(8 / Constants.PPM, 16 / Constants.PPM, new Vector2(0, 0), 0);
        fdef.shape = polygonShape;
        fdef.friction = 0;
        fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_TREE | Constants.BIT_ITEM;
        b2body.createFixture(fdef).setUserData("buffoon");
    }

    public void loadSprites() {
        resourceManager.loadTexture("player_run.png", "player_run");
    }

    public void handleAnimation() {
        switch (currAState) {
            case RUN_UP:
                setAnimation(TextureRegion.split(resourceManager.getTexture("player_run"), 32, 32)[0], 1/14f, false, 1.25f);
                break;
            case RUN_DOWN:
                setAnimation(TextureRegion.split(resourceManager.getTexture("player_run"), 32, 32)[0], 1/14f, false, 1.25f);
                break;
        }
    }

    public void update(float delta) {
        if (currAState != prevAState) handleAnimation();
        animation.update(delta);
    }

    public void moveUp() {
        b2body.setLinearVelocity(b2body.getLinearVelocity().x, Constants.MAX_SPEED);
    }

    public void moveDown() {
        b2body.setLinearVelocity(b2body.getLinearVelocity().x, -Constants.MAX_SPEED);
    }

    public void moveLeft() {
        b2body.setLinearVelocity(-Constants.MAX_SPEED, b2body.getLinearVelocity().y);
    }

    public void moveRight() {
        b2body.setLinearVelocity(Constants.MAX_SPEED, b2body.getLinearVelocity().y);
    }

    public void stop() {
        b2body.setLinearVelocity(0, 0);
    }


}
