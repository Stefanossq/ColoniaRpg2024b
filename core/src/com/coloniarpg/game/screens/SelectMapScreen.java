package com.coloniarpg.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.coloniarpg.game.AssetUtils;

public class SelectMapScreen implements Screen {
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Game game;
    private FadeScreen fadeScreen;

    public SelectMapScreen(Game game) {
        this.game = game;
    }

    private void startScreenBattle1Transition() {
        BattleScreen1 battleScreenInstance = new BattleScreen1(game);
        FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
        fadeScreen = new FadeScreen(game, fadeOut, this, battleScreenInstance);
        game.setScreen(fadeScreen);
    }

    private void startScreenBattle2Transition() {
        BattleScreen2 battleScreenInstance = new BattleScreen2(game);
        FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
        fadeScreen = new FadeScreen(game, fadeOut, this, battleScreenInstance);
        game.setScreen(fadeScreen);
    }

    private void startScreenBattle3Transition() {
        BattleScreen3 battleScreenInstance = new BattleScreen3(game);
        FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
        fadeScreen = new FadeScreen(game, fadeOut, this, battleScreenInstance);
        game.setScreen(fadeScreen);
    }

    @Override
    public void show() {
        AssetUtils.initAssets();
        batch = new SpriteBatch();
        background = AssetUtils.backgroundMenu;
        stage = new Stage(new ScreenViewport());

        float scaleGrow = 1.05f;
        float scaleNormal = 1f;

        // Texto "Selecionar Nível"
        TextureRegionDrawable selectLevelDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.selectLevelText));
        Image textSelectLevel = new Image(selectLevelDrawable);

        // Botão Nível 1
        TextureRegionDrawable levelButton1Drawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.levelButton1));
        final ImageButton levelButton1 = new ImageButton(levelButton1Drawable);
        addButtonListeners(levelButton1, scaleGrow, scaleNormal, this::startScreenBattle1Transition);

        // Botão Nível 2
        TextureRegionDrawable levelButton2Drawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.levelButton2));
        final ImageButton levelButton2 = new ImageButton(levelButton2Drawable);
        addButtonListeners(levelButton2, scaleGrow, scaleNormal, this::startScreenBattle2Transition);

        // Botão Nível 3
        TextureRegionDrawable levelButton3Drawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.levelButton3));
        final ImageButton levelButton3 = new ImageButton(levelButton3Drawable);
        addButtonListeners(levelButton3, scaleGrow, scaleNormal, this::startScreenBattle3Transition);

        // Usar Table para layout
        Table table = new Table();
        table.setFillParent(true); // Ocupa a tela inteira
        stage.addActor(table);

        // Adiciona texto e botões na tabela
        table.top().padTop(20); // Centraliza e adiciona padding superior
        table.add(textSelectLevel).colspan(3).center().padBottom(50); // Texto "Selecionar Nível"
        table.row(); // Próxima linha
        table.add(levelButton1).expandX().fill().pad(10); // Botão Nível 1
        table.add(levelButton2).expandX().fill().pad(10); // Botão Nível 2
        table.add(levelButton3).expandX().fill().pad(10); // Botão Nível 3

        Gdx.input.setInputProcessor(stage);
    }

    private void addButtonListeners(ImageButton button, float scaleGrow, float scaleNormal, Runnable onClick) {
        button.addListener(new ClickListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.getImage().setScale(scaleGrow);
                button.getImage().setOrigin(button.getImage().getWidth() / 2, button.getImage().getHeight() / 2);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.getImage().setScale(scaleNormal);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AssetUtils.songButton.play();
                onClick.run();
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
