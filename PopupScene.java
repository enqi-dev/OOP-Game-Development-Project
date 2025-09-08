package io.github.Project.lwjgl3.SceneManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import io.github.Project.lwjgl3.IOManager.IOManager;

public class PopupScene extends Scene {
    private Texture popupBox;
    private BitmapFont font;
    private String text;
    private String[] messages;
    private int currentMessageIndex;
    protected IOManager ioManager;
    private Runnable onClickAction;
    

    public PopupScene(SceneManager sceneManager, IOManager ioManager, String[] messages, Runnable onClickAction) {
        super(sceneManager);
        this.messages = messages;
        this.currentMessageIndex = 0;
        this.text = messages[currentMessageIndex];  
        this.ioManager = ioManager;
        this.onClickAction = onClickAction;
    }

    @Override
    public void create() {
        popupBox = new Texture("popup_box.png");

        // Load font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        font = generator.generateFont(parameter);
        font.setColor(Color.WHITE);
        generator.dispose();
    }

    @Override
    public void update() {
        if (ioManager.getInputHandler().isSpacePressed()) {
            if (currentMessageIndex < messages.length - 1) {
                currentMessageIndex++;
                text = messages[currentMessageIndex];
            } else {
                sceneManager.popScene();
                if (onClickAction != null) {
                    onClickAction.run();
                }
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        float scale = 1f; // Scale factor
        float popupWidth = popupBox.getWidth() * scale;
        float popupHeight = popupBox.getHeight() * scale;
        float x = (Gdx.graphics.getWidth() - popupWidth) / 2;
        float y = (Gdx.graphics.getHeight() - popupHeight) / 2;

        batch.draw(popupBox, x, y, popupWidth, popupHeight);

        // Calculate the position to center the text
        GlyphLayout layout = new GlyphLayout(font, text);
        float textWidth = layout.width;
        float textHeight = layout.height;
        float textX = x + (popupWidth - textWidth) / 2;
        float textY = y + (popupHeight + textHeight) / 2;
        
        font.draw(batch, text, textX, textY);
    }
    
    @Override
    public void dispose() {
        popupBox.dispose();
        font.dispose();
    }
}