package com.mygdx.game.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderHandler {

    private float time;
    private ShaderProgram itemShader;

    public ShaderHandler(SpriteBatch batch) {
        time = 0;
        itemShader = new ShaderProgram(Gdx.files.internal("Shaders/Vertex.glsl").readString(), Gdx.files.internal("Shaders/Shimmer.glsl").readString());
        ShaderProgram.pedantic = false;
        if (itemShader.isCompiled()) {
            System.out.println(itemShader.getLog());
        }
    }

    public void update(float delta) {
//        time += delta;
//        itemShader.setUniformf("u_texture", 50);
//        itemShader.setUniformf("u_speed", .5f);
//        itemShader.setUniformf("u_time", time);
    }

    public ShaderProgram getItemShader() {
        return itemShader;
    }
}
