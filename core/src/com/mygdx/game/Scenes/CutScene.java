package com.mygdx.game.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Tools.FancyFontHelper;

public class CutScene {
    public Stage stage;
    private Viewport viewport;
    private Label textLabel;

    public CutScene(SpriteBatch batch, String text) {

        viewport = new FitViewport(1000, 1000, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        textLabel = new Label(text, new Label.LabelStyle(FancyFontHelper.getInstance().getFont(Color.WHITE, 20), new Color(1, 1, 1, 1)));

        table.add(textLabel).padBottom(300).expandX();
        stage.addActor(table);
    }
}
