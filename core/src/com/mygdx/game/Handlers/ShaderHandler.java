package com.mygdx.game.Handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShaderHandler {

    private ShaderProgram itemShader;

    public void initShader(SpriteBatch batch) {
        itemShader = new ShaderProgram(batch.getShader().getVertexShaderSource(), Gdx.files.internal("Shaders/Shimmer.frag").readString());
        if (itemShader.isCompiled()) {
            System.out.println(itemShader.getLog());
        }
    }

    public void setShaderVariables() {
        itemShader.setUniformf("u_amount");
    }
}
