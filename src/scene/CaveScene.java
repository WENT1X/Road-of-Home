package scene;

import npc.PassiveNPC;
import player.Player;
import utils.InputHandler;
import utils.RandomEvent;

public class CaveScene extends Scene {
    public CaveScene(Player player) {
        super(player);
    }

    @Override
    public Scene play() {
        PassiveNPC ghost = new PassiveNPC("Призрак пещеры", new String[]{
                "Я был путником, как ты. Не доверяй теням!",
                "Сокровища? Ха, они прокляты!"
        });
        ghost.interact();

        System.out.println("\nВ пещере темно, но вы находите старый сундук.");
        System.out.println("1. Открыть сундук.");
        System.out.println("2. Игнорировать сундук и уйти.");
        System.out.print("Ваш выбор (1 или 2): ");

        int choice = InputHandler.getChoice(1, 2);
        if (choice == 1) {
            if (RandomEvent.occurs(50)) {
                System.out.println("В сундуке вы находите магический амулет!");
                player.setAmulet(true);
                player.setMorale(player.getMorale() + 15);
                System.out.println("Мораль: " + player.getMorale());
            } else {
                System.out.println("Сундук был ловушкой! Вы ранены.");
                player.setHealth(player.getHealth() - 20);
                System.out.println("Здоровье: " + player.getHealth());
                if (player.getHealth() <= 0) {
                    gameOver("Ловушка в пещере оказалась смертельной.");
                    return null;
                }
            }
        }
        return new CliffScene(player);
    }

    private void gameOver(String reason) {
        System.out.println("\n" + reason);
        player.printStatus();
        System.out.println("Игра окончена.");
        System.exit(0);
    }
}