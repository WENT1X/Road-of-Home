package scene;

import player.Player;

public class SceneManager {
    private Player player;
    private Scene currentScene;

    public SceneManager(Player player) {
        this.player = player;
        this.currentScene = new IntroScene(player);
    }

    public void start() {
        while (true) {
            currentScene = currentScene.play();
            if (currentScene == null) {
                break; // Game over or victory
            }
        }
    }
}