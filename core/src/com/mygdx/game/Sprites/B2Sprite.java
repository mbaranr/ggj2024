package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Tools.Constants;

public abstract class B2Sprite {
    protected Body b2body;
    protected Animation animation;
    protected float width;
    protected float height;
    protected boolean facingRight;
    private float resize;
    private float alpha;

    public B2Sprite() {
        animation = new Animation();
        facingRight = true;
        resize = 1;
    }

    public void setAnimation(TextureRegion[] region, float delay, boolean loopLastFrame, float resize, float alpha) {
        animation.setFrames(region, delay, loopLastFrame);
        width = region[0].getRegionWidth() * resize;
        height = region[0].getRegionWidth() * resize;
        this.resize = resize;
        this.alpha = alpha;
    }

    public void handleAnimation() { }

    public void update(float delta) {
        animation.update(delta);
    }

    public void render(SpriteBatch batch) {
        Color color = batch.getColor();//get current Color, you can't modify directly
        float oldAlpha = color.a; //save its alpha
        color.a = oldAlpha*alpha;
        batch.begin();
        batch.setColor(color);
        batch.draw(animation.getFrame(), facingRight ? b2body.getPosition().x - ((width / resize) / Constants.PPM) / 2 : b2body.getPosition().x + ((width / resize) / Constants.PPM) / 2 , b2body.getPosition().y - ((height / resize) / Constants.PPM) / 2, (facingRight ? width : -width) / Constants.PPM, height / Constants.PPM);
        color.a = oldAlpha;
        batch.setColor(color);
        batch.end();
    }

    public Vector2 getPosition() { return b2body.getPosition(); }

    public Body getB2body() { return b2body; }
}
