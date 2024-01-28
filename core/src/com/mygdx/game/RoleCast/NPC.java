package com.mygdx.game.RoleCast;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Game.LOD;
import com.mygdx.game.Sprites.B2Sprite;
import com.mygdx.game.Tools.Constants;
import com.mygdx.game.Tools.ResourceManager;

public class NPC extends B2Sprite {

    private String name;
    private ResourceManager resourceManager;
    private LOD game;
    public NPC(int x, int y, World world, String name, ResourceManager resourceManager, LOD game) {

        this.resourceManager = resourceManager;
        this.name = name;
        this.game = game;

        BodyDef bdef = new BodyDef();
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        //Create body fixture
        polygonShape.setAsBox(8 / Constants.PPM, 16 / Constants.PPM, new Vector2(0, 0), 0);
        fdef.shape = polygonShape;
        fdef.friction = 0;
        fdef.filter.categoryBits = Constants.BIT_NPC;
        b2body.createFixture(fdef).setUserData("npc");

        polygonShape.setAsBox(10 / Constants.PPM, 20 / Constants.PPM, new Vector2(0, -8 / Constants.PPM), 0);
        fdef.shape = polygonShape;
        fdef.friction = 0;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BIT_NPC;
        b2body.createFixture(fdef).setUserData(this);

        loadSprites(name);

        setAnimation(TextureRegion.split(resourceManager.getTexture(name), 32, 32)[0], 1/4f, false, 1f, 1);
    }

    public void update(float delta) {
        animation.update(delta);
    }

    public void loadSprites(String name) {
        if (name.equals("merchant")) resourceManager.loadTexture("merchant_idle.png", name);
        if (name.equals("nun")) resourceManager.loadTexture("nun_idle.png", name);
        if (name.equals("farmer")) resourceManager.loadTexture("farmer_idle.png", name);
        if (name.equals("guard")) resourceManager.loadTexture("guard_idle.png", name);
    }

    public void interact() {
        if (name.equals("merchant")) resourceManager.loadTexture("merchant_idle.png", name);
        if (name.equals("nun")) resourceManager.loadTexture("nun_idle.png", name);
        if (name.equals("farmer")) game.changeScreen("tomato");
        if (name.equals("guard")) resourceManager.loadTexture("guard_idle.png", name);
    }
}
