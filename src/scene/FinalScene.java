package scene;

import npc.PassiveNPC;
import player.Player;
import utils.InputHandler;

public class FinalScene extends Scene {
    public FinalScene(Player player) {
        super(player);
    }

    @Override
    public Scene play() {
        PassiveNPC child = new PassiveNPC("Ребенок", new String[]{
                "Ты видел мой дом? Он где-то там...",
                "Буря идет! Спрячься, путник!"
        });
        child.interact();

        System.out.println("\nВы видите свой дом вдалеке! Остался последний рывок.");
        player.printStatus();
        System.out.println("Внезапно начинается буря. Нужно найти укрытие или продолжить путь.");
        System.out.println("1. Искать укрытие.");
        System.out.println("2. Бежать к дому.");
        System.out.print("Ваш выбор (1 или 2): ");

        int choice = InputHandler.getChoice(1, 2);
        if (choice == 1) {
            if (player.hasCloak()) {
                System.out.println("Плащ защищает вас, пока вы ждете в укрытии. Буря проходит.");
                player.setMorale(player.getMorale() + 10);
                System.out.println("Мораль: " + player.getMorale());
            } else {
                System.out.println("Без плаща вы промокли и замерзли.");
                player.setHealth(player.getHealth() - 20);
                player.setMorale(player.getMorale() - 10);
                System.out.println("Здоровье: " + player.getHealth() + ", Мораль: " + player.getMorale());
            }
        } else {
            if (player.getHealth() >= 50 && player.getSupplies() >= 2) {
                System.out.println("Вы бежите через бурю и добираетесь до дома! Победа!");
                player.printStatus();
                System.exit(0);
            } else {
                gameOver("Буря и ваша слабость остановили вас в шаге от дома.");
                return null;
            }
        }
        if (player.getHealth() <= 0) {
            gameOver("Буря оказалась слишком сильной.");
            return null;
        }
        System.out.println("Вы пережидаете бурю и идете к дому.");
        if (player.getHealth() >= 30 && player.getSupplies() >= 1 && player.getMorale() >= 30) {
            System.out.println("Вы благополучно добираетесь до дома! Победа!");
            player.printStatus();
        } else {
            gameOver("Вы слишком слабы, чтобы дойти до дома.");
        }
        return null;
    }

    private void gameOver(String reason) {
        System.out.println("\n" + reason);
        player.printStatus();
        System.out.println("Игра окончена.");
        System.exit(0);
    }
}