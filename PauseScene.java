package io.github.Project.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import ScoreManager.ScoreManager;
import io.github.Project.lwjgl3.EntityManager.Entity;
import io.github.Project.lwjgl3.EntityManager.EntityFactory;
import io.github.Project.lwjgl3.EntityManager.EntityManager;
import io.github.Project.lwjgl3.EntityManager.EntityType;
import io.github.Project.lwjgl3.IOManager.IOManager;

public class PauseScene extends Scene {
    private Texture popupBox;
    private BitmapFont font;
    private Entity homeButton;
    private Entity musicButton;
    private String line1 = "Game Paused";
    private String line2 = "Press R to return";
    protected EntityManager entityManager;
    protected IOManager ioManager;
    protected ScoreManager scoreManager;
    protected EntityFactory entityFactory;


    public PauseScene(SceneManager sceneManager, EntityManager entityManager, IOManager ioManager, ScoreManager scoreManager, EntityFactory entityFactory) {
        super(sceneManager);
        this.entityManager = entityManager;
        this.ioManager = ioManager;
        this.scoreManager = scoreManager;
        this.entityFactory = entityFactory;
    }

    @Override
    public void create() {
        popupBox = new Texture("popup_box.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        generator.dispose();

        homeButton = entityFactory.createEntity(EntityType.BUTTON, 375, 175, 198, 48, 
            (Runnable) () -> {
                sceneManager.popScene(); 
                sceneManager.setScene(SceneManager.SceneType.MENU);
                scoreManager.resetgame();
            }, "home.png");
        entityManager.addEntity(homeButton);

        // Verify if music is playing and set the initial image of the music button accordingly
        boolean isMusicPlaying = ioManager.getOutputHandler().isMusicPlaying();
        String musicButtonImage = isMusicPlaying ? "soundon.png" : "soundoff.png";
        String musicButtonToggleImage = isMusicPlaying ? "soundoff.png" : "soundon.png";

        // Add toggle music button
        musicButton = entityFactory.createEntity(EntityType.BUTTON, 622, 175, 200, 50, 
            (Runnable) () -> ioManager.getOutputHandler().toggleMusic(), musicButtonImage, musicButtonToggleImage);
        entityManager.addEntity(musicButton);
    }

    @Override
    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            sceneManager.popScene();
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float scale = 0.7f; // Scale factor
        float popupWidth = popupBox.getWidth() * scale;
        float popupHeight = popupBox.getHeight() * scale;
        float x = (Gdx.graphics.getWidth() - popupWidth) / 2;
        float y = (Gdx.graphics.getHeight() - popupHeight) / 2;
        
        entityManager.render(batch);

        batch.draw(popupBox, x, y, popupWidth, popupHeight);


        // Calculate the position to center the text
        GlyphLayout layout1 = new GlyphLayout(font, line1);
        float textWidth1 = layout1.width;
        float textHeight1 = layout1.height;
        float textX1 = (width - textWidth1) / 2;
        float textY1 = (height + textHeight1) / 2 + 20;

        GlyphLayout layout2 = new GlyphLayout(font, line2);
        float textWidth2 = layout2.width;
        float textHeight2 = layout2.height;
        float textX2 = (width - textWidth2) / 2;
        float textY2 = (height + textHeight2) / 2 - 10;

        font.draw(batch, line1, textX1, textY1);
        font.draw(batch, line2, textX2, textY2);
        
    }

    @Override
    public void dispose() {
        popupBox.dispose();
        font.dispose();
        if (homeButton != null) {
            entityManager.removeEntity(homeButton);
            homeButton = null;
        }
        if (musicButton != null) {
            entityManager.removeEntity(musicButton);
            musicButton = null;
        }
    }

    @Override
    public boolean usesClickEvents() {
        return true;
    }

    
}