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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.coloniarpg.game.AssetUtils;

// Classe que cria a tela de menu principal
public class MainMenuScreen implements Screen {
    // Variáveis estáticas para armazenar a largura e altura da janela
    public static float windowWidth;
    public static float windowHeight;

    // Variáveis privadas para armazenar o batch, background, stage e game
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Game game;
    private FadeScreen fadeScreen;

    // Construtor da classe
    public MainMenuScreen(Game game) {
        this.game = game;
    }

    // Método que inicia a transição de tela
    private void startScreenTransition() {
        SelectMapScreen SelectMapScreenInstance = new SelectMapScreen(game);
        FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
        fadeScreen = new FadeScreen(game, fadeOut, this, SelectMapScreenInstance);
        game.setScreen(fadeScreen);
    }

    @Override
    public void show() {
        AssetUtils.initAssets();
        batch = new SpriteBatch();
        background = AssetUtils.backgroundMenu;
        stage = new Stage(new ScreenViewport());
        windowWidth = Gdx.graphics.getWidth();
        windowHeight = Gdx.graphics.getHeight();
    
        // Calcula a posição do título
        float titleX = windowWidth / 2 - AssetUtils.title.getWidth() / 2;
        float titleY = windowHeight - AssetUtils.title.getHeight() - 60;
    
        // Cria o título do jogo
        Image title = new Image(new TextureRegionDrawable(new TextureRegion(AssetUtils.title)));
        title.setPosition(titleX, titleY);
        title.setScale(1.335f);
        title.setOrigin(title.getWidth() / 2, title.getHeight() / 2);
    
        // Espaçamento entre os botões
        float buttonSpacing = 50;
    
        // Calcula a largura total dos botões com o espaçamento
        float totalButtonWidth = AssetUtils.playButton.getWidth() + AssetUtils.settingsButton.getWidth() + buttonSpacing;
    
        // Calcula a posição do primeiro botão (Play)
        float playButtonX = (windowWidth - totalButtonWidth) / 2;
        float playButtonY = 85;
    
        // Cria o botão de Play
        TextureRegionDrawable playButtonDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.playButton));
        TextureRegionDrawable playButtonHighlightDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.playButtonHighlight));
        final ImageButton playButton = new ImageButton(playButtonDrawable);
    
        playButton.setPosition(playButtonX, playButtonY);
        playButton.getStyle().imageOver = playButtonHighlightDrawable;
        playButton.getStyle().imageUp = playButtonDrawable;
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                AssetUtils.songButton.play();
                startScreenTransition();
                return true;
            }
        });
    
        // Calcula a posição do botão de Configurações
        float settingsButtonX = playButtonX + AssetUtils.playButton.getWidth() + buttonSpacing;
    
        // Cria o botão de Configurações
        TextureRegionDrawable settingsButtonDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.settingsButton));
        TextureRegionDrawable settingsButtonHighlightDrawable = new TextureRegionDrawable(new TextureRegion(AssetUtils.settingsButtonHighlight));
        final ImageButton settingsButton = new ImageButton(settingsButtonDrawable);
    
        settingsButton.setPosition(settingsButtonX, playButtonY);
        settingsButton.getStyle().imageOver = settingsButtonHighlightDrawable;
        settingsButton.getStyle().imageUp = settingsButtonDrawable;
        settingsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Botão de Configurações pressionado");
                return true;
            }
        });
    
        // Adiciona os elementos na tela
        stage.addActor(title);
        stage.addActor(playButton);
        stage.addActor(settingsButton);
    
        Gdx.input.setInputProcessor(stage);
    }
    

    // Método que renderiza os elementos da tela
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); // Limpa a tela
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Limpa o buffer de cores

        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    // Método que redimensiona a tela
    @Override
    public void resize(int width, int height) { }

    // Método que pausa a tela
    @Override
    public void pause() { }

    // Método que retoma a tela
    @Override
    public void resume() { }

    // Método que esconde a tela
    @Override
    public void hide() { }

    // Método que descarta os elementos da tela
    @Override
    public void dispose() {
        AssetUtils.title.dispose();
        batch.dispose();
        stage.dispose();
    }
}
