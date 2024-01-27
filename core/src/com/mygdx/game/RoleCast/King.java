package com.mygdx.game.RoleCast;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Objects.Item;
import com.mygdx.game.Sprites.B2Sprite;
import com.mygdx.game.Tools.Constants;
import com.mygdx.game.Tools.FunCalculator;
import java.util.Random;
import java.util.LinkedList;

public class King extends B2Sprite {
    private float laughMeter;
    private final FunCalculator funCalculator;
    private Constants.COMEDYTYPE favouredType;
    private final Constants.COMEDYTYPE[] comedytypes;

    public King(int x, int y, World world) {

        laughMeter = 0;
        funCalculator = new FunCalculator();

        comedytypes = new Constants.COMEDYTYPE[5];
        comedytypes[0] = Constants.COMEDYTYPE.ROMANTIC;
        comedytypes[1] = Constants.COMEDYTYPE.SELF_DEPRECATING;
        comedytypes[2] = Constants.COMEDYTYPE.DARK;
        comedytypes[3] = Constants.COMEDYTYPE.ABSURD;
        comedytypes[4] = Constants.COMEDYTYPE.TRAGIC;

        BodyDef bdef = new BodyDef();
        bdef.position.set(x / Constants.PPM, y / Constants.PPM);
        bdef.type = BodyDef.BodyType.StaticBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        //Create body fixture
        polygonShape.setAsBox(32 / Constants.PPM, 32 / Constants.PPM, new Vector2(0, 0), 0);
        fdef.shape = polygonShape;
        fdef.friction = 0;
        fdef.filter.maskBits = Constants.BIT_GROUND | Constants.BIT_TREE | Constants.BIT_ITEM;
        b2body.createFixture(fdef).setUserData("king");

        wakeUp();

    }

    public void loadSprites() {

    }

    public void handleAnimation() {

    }

    public void update(float delta) {

        animation.update(delta);
    }

    public void resetMeter() {
        laughMeter = 0;
    }

    public void wakeUp() {
        Random rand = new Random();
        int index = rand.nextInt(5);
        while (comedytypes[index] == null) {
            index = rand.nextInt(5);
        }
        favouredType = comedytypes[index];
        comedytypes[index] = null;
    }

    public void presentItems(LinkedList<Item> items) {
        laughMeter += funCalculator.evaluate(items, favouredType);
    }

}