package com.coloniarpg.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.coloniarpg.game.AssetUtils;

public class QuestionScreen1 implements Screen {
    public static float windowWidth;
    public static float windowHeight;

    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private Game game;
    private FadeScreen fadeScreen;
    private int battleScreen;

    private String[] questions;
    private String[] answers;
    private String[] correctAnswers;

    public QuestionScreen1(Game game, int battleScreen) {
        this.game = game;
        this.battleScreen = battleScreen;

        if (battleScreen == 1) {
            questions = new String[]{
                    "Qual é o nome do rio que corta a cidade de São João do Polêsine?",
                    "Em que ano São João do Polêsine foi emancipada?",
                    "Qual é a principal atração turística de São João do Polêsine?",
                    "Qual é o prato típico de São João do Polêsine?",
                    "Qual é a principal atividade econômica de São João do Polêsine?"
            };

            answers = new String[]{
                    "Rio Jacuí", "Rio Uruguai", "Rio Ibicuí", "Rio Paraná",
                    "1991", "1992", "1993", "1994",
                    "Igreja Matriz São João Batista", "Museu Municipal", "Parque Municipal", "Santuário Nossa Senhora de Fátima",
                    "Pizza", "Xis", "Galeto à moda gaúcha", "Carne de porco assada",
                    "Agricultura", "Pecuária", "Turismo", "Indústria"
            };

            correctAnswers = new String[]{
                    "Rio Ibicuí", "1992", "Igreja Matriz São João Batista", "Galeto à moda gaúcha", "Agricultura"
            };

        } else if (battleScreen == 2) {
            questions = new String[]{
                    "Em que ano Pinhal Grande foi emancipada?",
                    "Qual é a principal atividade econômica de Pinhal Grande?",
                    "Qual é a principal atração turística de Pinhal Grande?",
                    "Qual é o nome do santo padroeiro de Pinhal Grande?",
                    "Qual é o rio que forma a divisa natural de Pinhal Grande?"
            };

            answers = new String[]{
                    "1990", "1991", "1992", "1993",
                    "Agricultura", "Pecuária", "Turismo", "Indústria",
                    "Festa do trabalhador", "Festa do Colono", "Festa do Pinhão", "Festa do Peão",
                    "São José", "São João", "São Pedro", "São Paulo",
                    "Rio Ibicuí", "Rio Uruguai", "Rio Jacuí", "Rio Paraná"
            };

            correctAnswers = new String[]{
                    "1992", "Agricultura", "Festa do Pinhão", "São José", "Rio Jacuí"
            };

        } else if (battleScreen == 3) {
            questions = new String[]{
                    "Qual é o nome do engenheiro que projetou a Vila Belga ?",
                    "Em que ano a Vila Belga foi inaugurada?",
                    "Qual a ocupação dos primeiros moradores da Vila Belga?",
                    "Qual o nome da principal rua da Vila Belga?",
                    "O que acontece na Vila Belga nos segundos e terceiros domingos de cada mês?"
            };

            answers = new String[]{
                    "Gustave Vauthier", "Gustavo Walter", "George Washignton", "Gustavo Lima",
                    "1800", "1900", "1905", "1999",
                    "Artesãos", "Ferroviários", "Agricultores", "Alfaiates",
                    "Dr.Bozano", "Dr.Wauthier", "Dr.House", "Dr.Vauthier",
                    "Brique da Vila Belga", "BelgaParty", "Café Belga", "Beyblade"
            };

            correctAnswers = new String[]{
                    "Gustave Vauthier", "1905", "Ferroviários", "Dr.Wauthier", "Brique da Vila Belga"
            };
        }
    }

    private void startScreenBattleTransition(int battleScreen) {
        Screen nextBattleScreen;
        if (battleScreen == 1) {
            nextBattleScreen = new BattleScreen1(game);
        } else if (battleScreen == 2) {
            nextBattleScreen = new BattleScreen2(game);
        } else {
            nextBattleScreen = new BattleScreen3(game);
        }

        FadeScreen.FadeInfo fadeOut = new FadeScreen.FadeInfo(FadeScreen.FadeType.OUT, Color.BLACK, Interpolation.smoother, 1f);
        fadeScreen = new FadeScreen(game, fadeOut, this, nextBattleScreen);
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

        // Estilo de texto e botões
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(AssetUtils.answerButton));
        textButtonStyle.font = font;

        // Adiciona pergunta
        final int indexPergunta = battleScreen == 1 ? BattleScreen1.indexPergunta
                : battleScreen == 2 ? BattleScreen2.indexPergunta
                : BattleScreen3.indexPergunta;

        Label questionLabel = new Label(questions[indexPergunta], style);
        questionLabel.setPosition(windowWidth / 2 - questionLabel.getWidth() / 2, windowHeight - 100);
        stage.addActor(questionLabel);

        // Adiciona botões de resposta
        int height = 300;
        for (int j = 0; j < 4; j++) {
            final int answerIndex = indexPergunta * 4 + j;
            TextButton answerButton = new TextButton(answers[answerIndex], textButtonStyle);
            answerButton.setPosition(windowWidth / 2 - AssetUtils.answerButton.getWidth() / 2, windowHeight - height);

            answerButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (answers[answerIndex].equals(correctAnswers[indexPergunta])) {
                        if (battleScreen == 1) BattleScreen1.vidaInimigo--;
                        if (battleScreen == 2) BattleScreen2.vidaInimigo--;
                        if (battleScreen == 3) BattleScreen3.vidaInimigo--;
                        AssetUtils.songCorrect.play();
                    } else {
                        if (battleScreen == 1) BattleScreen1.vidaAvatar--;
                        if (battleScreen == 2) BattleScreen2.vidaAvatar--;
                        if (battleScreen == 3) BattleScreen3.vidaAvatar--;
                        AssetUtils.songWrong.play();
                    }
                    if (battleScreen == 1) BattleScreen1.indexPergunta++;
                    if (battleScreen == 2) BattleScreen2.indexPergunta++;
                    if (battleScreen == 3) BattleScreen3.indexPergunta++;

                    startScreenBattleTransition(battleScreen);
                }
            });
            stage.addActor(answerButton);
            height += 120;
        }

        Gdx.input.setInputProcessor(stage);
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
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}
