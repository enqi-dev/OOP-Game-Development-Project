package io.github.Project.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import io.github.Project.lwjgl3.EntityManager.Entity;
import io.github.Project.lwjgl3.EntityManager.EntityFactory;
import io.github.Project.lwjgl3.EntityManager.EntityManager;
import io.github.Project.lwjgl3.EntityManager.EntityType;
import io.github.Project.lwjgl3.IOManager.IOManager;

public class SettingScene extends Scene {
    private BitmapFont font;
    private Texture background;
    protected EntityManager entityManager;
    protected IOManager ioManager;
    protected EntityFactory entityFactory;


    public SettingScene(SceneManager sceneManager, EntityManager entityManager, IOManager ioManager, EntityFactory entityFactory) {
        super(sceneManager);
        this.entityManager = entityManager;
        this.ioManager = ioManager;
        this.entityFactory = entityFactory;
    }

    @Override
    public void create() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK); // Set font color to black
        generator.dispose();

        background = new Texture("settings.png");


        // Add home button
        Entity homeButton = entityFactory.createEntity(EntityType.BUTTON, 327, 300, 500, 140, 
            (Runnable) () -> {
                sceneManager.setScene(SceneManager.SceneType.MENU);
            }, "home.png");
        entityManager.addEntity(homeButton);

        // Verify if music is playing and set the initial image of the music button accordingly
        boolean isMusicPlaying = ioManager.getOutputHandler().isMusicPlaying();
        String musicButtonImage = isMusicPlaying ? "soundon.png" : "soundoff.png";
        String musicButtonToggleImage = isMusicPlaying ? "soundoff.png" : "soundon.png";


        // Add toggle music button
        Entity musicButton = entityFactory.createEntity(EntityType.BUTTON, 335, 450, 500, 140, 
            (Runnable) () -> ioManager.getOutputHandler().toggleMusic(), musicButtonImage, musicButtonToggleImage);
        entityManager.addEntity(musicButton);
    }

    @Override
    public void update() {

    }

	    @Override
	    public void render(SpriteBatch batch) {
	        float width = Gdx.graphics.getWidth();
	        float height = Gdx.graphics.getHeight();
	
	        batch.draw(background, 0, 0, width, height);
	        
	
	        entityManager.render(batch);
	    }

    @Override
    public void dispose() {
        if (font != null) {
            font.dispose();
            font = null;
        }
    }

    @Override
    public boolean usesClickEvents() {
        return true;
    }
}
