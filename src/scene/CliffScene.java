package scene;

import player.Player;
import utils.InputHandler;

public class CliffScene extends Scene {
    public CliffScene(Player player) {
        super(player);
    }

    @Override
    public Scene play() {
        System.out.println("\nВы выходите к обрыву. Внизу виднеется река, а через нее — старый мост.");
        System.out.println("1. Спуститься к мосту.");
        System.out.println("2. Искать другой путь.");
        System.out.print("Ваш выбор (1 или 2): ");

        int choice = InputHandler.getChoice(1, 2);
        if (choice == 1) {
            return new RiverScene(player);
        } else {
            System.out.println("Поиск другого пути занимает время, и вы теряете силы.");
            player.setSupplies(player.getSupplies() - 1);
            player.setMorale(player.getMorale() - 10);
            System.out.println("Припасы: " + player.getSupplies() + ", Мораль: " + player.getMorale());
            if (player.getSupplies() <= 0) {
                gameOver("Вы истощены без припасов.");
                return null;
            }
            return new RiverScene(player);
        }
    }

    private void gameOver(String reason) {
        System.out.println("\n" + reason);
        player.printStatus();
        System.out.println("Игра окончена.");
        System.exit(0);
    }
}