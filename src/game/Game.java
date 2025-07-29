package game;

import scene.SceneManager;
import player.Player;

public class Game {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в игру 'Road of Home'!");
        System.out.println("Вы — путник, стремящийся вернуться домой через опасные земли.");
        Player player = new Player();
        SceneManager sceneManager = new SceneManager(player);
        sceneManager.start();
    }
}