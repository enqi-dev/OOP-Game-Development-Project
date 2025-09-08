package io.github.Project.lwjgl3.EntityManager;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class UIEntity extends Entity {
    private Texture texture;
    private ProgressBar progressBar;
    private boolean visible = false;

    // Constructor for rendering a simple texture
    public UIEntity(float x, float y, float width, float height, String texturePath) {
        super(x, y, width, height);
        this.texture = new Texture(texturePath);
    }

    // Constructor for rendering a texture with a progress bar
    public UIEntity(float x, float y, float width, float height, String texturePath, String backgroundTexturePath, String progressTexturePath) {
        super(x, y, width, height);
        this.texture = new Texture(texturePath);

        // Load textures for the progress bar
        Texture backgroundTexture = new Texture(backgroundTexturePath);
        Texture progressTexture = new Texture(progressTexturePath);

        // Initialize the progress bar style
        ProgressBarStyle style = new ProgressBarStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(backgroundTexture));
        style.knob = null;
        style.knobBefore = new TextureRegionDrawable(new TextureRegion(progressTexture));

        // Initialize the progress bar
        progressBar = new ProgressBar(0, 100, 0.01f, true, style);
        progressBar.setOrigin(Align.bottom);
        progressBar.setValue(100); // Start at full progress
        progressBar.setSize(10, height); // Adjust size
        progressBar.setPosition(x+95, y); // Position below the entity
        progressBar.setDebug(true);
        
    }

    @Override
    public void render(SpriteBatch batch) {
        if (visible) {
            // Render the texture only if visible is true
            batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        }
        if (progressBar != null) {
            // Always render the progress bar if it exists
            progressBar.draw(batch, 1);
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public void updateProgress(float value) {
        if (progressBar != null) {
            progressBar.setValue(value);
        }
    }

    public float getProgress() {
        return progressBar != null ? progressBar.getValue() : 0;
    }

    public boolean isVisible() {
        return visible;
    }
    
    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}