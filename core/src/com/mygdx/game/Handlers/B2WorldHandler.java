package com.mygdx.game.Handlers;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.Logic.MyTimer;
import com.mygdx.game.Objects.Item;
import com.mygdx.game.Tools.ResourceManager;

import java.util.concurrent.atomic.AtomicInteger;

public class B2WorldHandler {

    public B2WorldHandler(World world, TiledMap map, ResourceManager resourceManager, MyTimer timer, AtomicInteger eidAllocator) {

        BodyDef bdef  = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        Item item = new Item(0, 2, world, 0.1f, null, null, null, null);

//        // Create ground
//        for (RectangleMapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
//            Rectangle rect = object.getRectangle();
//            bdef.type = BodyDef.BodyType.StaticBody;
//            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);
//            body = world.createBody(bdef);
//            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
//            fdef.shape = shape;
//            fdef.filter.categoryBits = Constants.BIT_GROUND;
//            body.createFixture(fdef).setUserData("ground");
//        }
    }

}
