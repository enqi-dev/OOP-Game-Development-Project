package io.github.Project.lwjgl3.SceneManager;

import java.util.HashMap;
import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import io.github.Project.lwjgl3.EntityManager.EntityManager;

public class SceneManager {
    
    public enum SceneType {MENU, GAME1, GAME2, GAME3, POPUP, PAUSE,SETTINGS,TEMPPOPUP}
    private HashMap<SceneType, Scene> scenes;
    private Stack<Scene> sceneStack;
    private EntityManager entityManager;

    public SceneManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        scenes = new HashMap<>();
        sceneStack = new Stack<>();
    }

    // Register a scene (called once at game start)
    public void addScene(SceneType type, Scene scene) {
        scenes.put(type, scene);
    }
    
    // Switch to a new scene
    public void setScene(SceneType type) {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().dispose();
            popScene();
        }
        
        entityManager.dispose();
        
        Scene newScene = scenes.get(type);
        if (!sceneStack.contains(newScene)) {
            newScene.create();
        }
        sceneStack.push(newScene);
    }
    
    // Push a new scene onto the stack without disposing of the current scene
    public void pushScene(SceneType type) {
        Scene newScene = scenes.get(type);
        if (!sceneStack.contains(newScene)) {
            newScene.create();
        }
        sceneStack.push(newScene);
    }    
    
    // Pop the current scene from the stack
    public void popScene() {
        if (!sceneStack.isEmpty()) {
            sceneStack.pop().dispose();
            if (!sceneStack.isEmpty()) {
                // Do not call create() here, just reuse the existing scene
                // sceneStack.peek().create();
            }
        }
    }
    
    public Scene getCurrentScene() {
        return sceneStack.isEmpty() ? null : sceneStack.peek();
    }

    public void update() {
        if (!sceneStack.isEmpty()) {
            sceneStack.peek().update();
        }
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        Scene[] scenesToRender = sceneStack.toArray(new Scene[0]);
        for (Scene scene : scenesToRender) {
            scene.render(batch);
        }
        batch.end();
    }

    public void dispose() {
        while (!sceneStack.isEmpty()) {
            sceneStack.pop().dispose();
        }
    }
}