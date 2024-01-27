package com.mygdx.game.Objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Sprites.B2Sprite;
import com.mygdx.game.Tools.Constants;

public class Item extends B2Sprite {

    private boolean grabbable;
    private boolean grab;
    private int itemID;

    public Item(int x, int y, World world, int itemID) {

        this.itemID = itemID;
        BodyDef bdef = new BodyDef();
        grabbable = false;
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        //Create body fixture
        polygonShape.setAsBox(5 / Constants.PPM, 5 / Constants.PPM, new Vector2(0, 0), 0);
        fdef.shape = polygonShape;
        fdef.friction = 0;
        fdef.isSensor = true;
        fdef.filter.categoryBits = Constants.BIT_ITEM;
        b2body.createFixture(fdef).setUserData("item_" + itemID);
    }

    public int getItemID() {
        return itemID;
    }

    public void setGrabbable(boolean flag) {
        this.grabbable = flag;
    }

    public boolean canBeGrabbed() {
        return this.grabbable;
    }
}
