package scene;

import player.Player;

public abstract class Scene {
    protected Player player;

    public Scene(Player player) {
        this.player = player;
    }

    public abstract Scene play();
}