package scene;

import npc.PassiveNPC;
import player.Player;
import utils.InputHandler;

public class MountainScene extends Scene {
    public MountainScene(Player player) {
        super(player);
    }

    @Override
    public Scene play() {
        PassiveNPC hermit = new PassiveNPC("Отшельник", new String[]{
                "Горы хранят тайны, но они не для слабых!",
                "Я видел снег, что поет. А ты?"
        });
        hermit.interact();

        System.out.println("\nГорная тропа крутая и холодная. Ветер усиливается.");
        if (!player.hasCloak()) {
            System.out.println("Без плаща вы замерзаете.");
            player.setHealth(player.getHealth() - 15);
            player.setMorale(player.getMorale() - 10);
            System.out.println("Здоровье: " + player.getHealth() + ", Мораль: " + player.getMorale());
        } else {
            System.out.println("Плащ защищает вас от холода.");
            player.setMorale(player.getMorale() + 5);
            System.out.println("Мораль: " + player.getMorale());
        }
        if (player.getHealth() <= 0) {
            gameOver("Холод гор оказался смертельным.");
            return null;
        }
        System.out.println("Вы находите пещеру для укрытия.");
        System.out.println("1. Зайти в пещеру.");
        System.out.println("2. Продолжить путь по тропе.");
        System.out.print("Ваш выбор (1 или 2): ");
        int choice = InputHandler.getChoice(1, 2);
        if (choice == 1) {
            return new CaveScene(player);
        } else {
            return new CliffScene(player);
        }
    }

    private void gameOver(String reason) {
        System.out.println("\n" + reason);
        player.printStatus();
        System.out.println("Игра окончена.");
        System.exit(0);
    }
}