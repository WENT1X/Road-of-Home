package scene;

import npc.ActiveNPC;
import player.Player;
import utils.InputHandler;

public class BanditsScene extends Scene {
    public BanditsScene(Player player) {
        super(player);
    }

    @Override
    public Scene play() {
        ActiveNPC banditLeader = new ActiveNPC("Главарь бандитов", "Отдай припасы, или пожалеешь!" , false);
        System.out.println("\nНа другом берегу реки вы сталкиваетесь с бандитами.");
        banditLeader.interact();
        System.out.println("1. Отдать припасы.");
        System.out.println("2. Сражаться с бандитами.");
        System.out.println("3. Попытаться договориться.");
        System.out.print("Ваш выбор (1, 2 или 3): ");

        int choice = InputHandler.getChoice(1, 3);
        if (choice == 1) {
            System.out.println("Вы отдаете припасы. Бандиты уходят.");
            player.setSupplies(player.getSupplies() - 3);
            player.setMorale(player.getMorale() - 15);
            System.out.println("Припасы: " + player.getSupplies() + ", Мораль: " + player.getMorale());
            if (player.getSupplies() <= 0) {
                gameOver("Без припасов вы не можете продолжить.");
                return null;
            }
        } else if (choice == 2) {
            if (player.hasSword()) {
                System.out.println("С мечом вы побеждаете бандитов, но получаете раны.");
                player.setHealth(player.getHealth() - 15);
                player.setMorale(player.getMorale() + 15);
                System.out.println("Здоровье: " + player.getHealth() + ", Мораль: " + player.getMorale());
            } else {
                System.out.println("Без оружия бой тяжелый. Вы едва выживаете.");
                player.setHealth(player.getHealth() - 40);
                player.setMorale(player.getMorale() - 10);
                System.out.println("Здоровье: " + player.getHealth() + ", Мораль: " + player.getMorale());
            }
            if (player.getHealth() <= 0) {
                gameOver("Бандиты оказались сильнее вас.");
                return null;
            }
        } else {
            if (player.getMorale() >= 50) {
                System.out.println("Вы убедительно говорите, и бандиты отпускают вас.");
                player.setMorale(player.getMorale() + 10);
                System.out.println("Мораль: " + player.getMorale());
            } else {
                System.out.println("Ваша низкая мораль не убеждает бандитов. Они нападают.");
                player.setHealth(player.getHealth() - 20);
                System.out.println("Здоровье: " + player.getHealth());
                if (player.getHealth() <= 0) {
                    gameOver("Бандиты оказались сильнее вас.");
                    return null;
                }
            }
        }
        return new FinalScene(player);
    }

    private void gameOver(String reason) {
        System.out.println("\n" + reason);
        player.printStatus();
        System.out.println("Игра окончена.");
        System.exit(0);
    }
}