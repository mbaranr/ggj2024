package com.mygdx.game.RoleCast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Sprites.B2Sprite;
import com.mygdx.game.Tools.Constants;
import com.mygdx.game.Tools.ResourceManager;

public class NPC extends B2Sprite {

    private ResourceManager resourceManager;

    public NPC(int x, int y, World world, String name, ResourceManager resourceManager) {

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
        fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_TREE | Constants.BIT_ITEM | Constants.BIT_TRANSPARENCY;
        b2body.createFixture(fdef).setUserData("buffoon");

        loadSprites(name);

    }

    public void loadSprites(String name) {
        if (name.equals("merchant")) resourceManager.loadTexture("merchant_idle.png", "merchant");
        if (name.equals("nun")) resourceManager.loadTexture("merchant_idle.png", "merchant");
    }
}
