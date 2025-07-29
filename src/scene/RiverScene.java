package scene;

import npc.ActiveNPC;
import player.Player;
import utils.InputHandler;
import utils.RandomEvent;
import currency.*;

public class RiverScene extends Scene {
    public RiverScene(Player player) {
        super(player);
    }

    @Override
    public Scene play() {
        ActiveNPC fisherman = new ActiveNPC("Рыбак", "Река опасна, но я могу помочь, если ты поделишься припасами или золотом.", false);
        ActiveNPC merchant = new ActiveNPC("Речной торговец", "У меня есть полезные вещи для путников!", true);
        System.out.println("\nВы подходите к бурной реке. Через нее перекинут старый мост, но он выглядит ненадежно.");
        fisherman.interact();
        merchant.interact();
        System.out.println("1. Рискнуть и перейти мост.");
        System.out.println("2. Искать брод.");
        System.out.println("3. Дать рыбаку припасы или золото за помощь.");
        System.out.println("4. Торговать с речным торговцем.");
        if (player.hasAmulet()) {
            System.out.println("5. Использовать амулет.");
        }
        System.out.print("Ваш выбор (1, 2, 3, 4" + (player.hasAmulet() ? " или 5" : "") + "): ");

        int maxChoice = player.hasAmulet() ? 5 : 4;
        int choice = InputHandler.getChoice(1, maxChoice);
        if (choice == 1) {
            if (player.hasMap()) {
                System.out.println("Карта подсказывает, что мост безопасен в одном месте. Вы переходите успешно!");
                player.setMorale(player.getMorale() + 10);
                System.out.println("Мораль: " + player.getMorale());
            } else if (RandomEvent.occurs(50)) {
                System.out.println("Мост трещит, но вы успеваете перебраться!");
                player.setMorale(player.getMorale() + 5);
                System.out.println("Мораль: " + player.getMorale());
            } else {
                System.out.println(" Мост ломается, и вы падаете в реку!");
                int damage = player.hasBuff("DefenseBoost") ? 15 : 25;
                player.setHealth(player.getHealth() - damage);
                System.out.println("Здоровье: " + player.getHealth());
                if (player.getHealth() <= 0) {
                    gameOver("Река унесла вас.");
                    return null;
                }
            }
        } else if (choice == 2) {
            System.out.println("Вы находите брод, но течение сильное.");
            if (player.hasCloak()) {
                System.out.println("Плащ помогает вам удержаться.");
                player.setMorale(player.getMorale() + 5);
                System.out.println("Мораль: " + player.getMorale());
            } else {
                System.out.println("Течение сбивает вас с ног, вы теряете припасы.");
                player.setSupplies(player.getSupplies() - 2);
                System.out.println("Припасы: " + player.getSupplies());
                if (player.getSupplies() <= 0) {
                    gameOver("Без припасов вы не можете продолжить.");
                    return null;
                }
            }
        } else if (choice == 3) {
            if (player.getSupplies() >= 2 || player.getCurrencyAmount("Gold") >= 8) {
                if (player.getSupplies() >= 2 && player.getCurrencyAmount("Gold") < 8) {
                    player.setSupplies(player.getSupplies() - 2);
                    System.out.println("Вы даете рыбаку припасы.");
                } else {
                    player.spendCurrency("Gold", 8);
                    System.out.println("Вы даете рыбаку золото.");
                }
                System.out.println("Рыбак переправляет вас на лодке!");
                player.setMorale(player.getMorale() + 15);
                System.out.println("Припасы: " + player.getSupplies() + ", Золото: " + player.getCurrencyAmount("Gold") + ", Мораль: " + player.getMorale());
            } else {
                System.out.println("У вас недостаточно припасов или золота. Рыбак отказывается помогать.");
                player.setMorale(player.getMorale() - 5);
                System.out.println("Мораль: " + player.getMorale());
                return this; // Repeat scene
            }
        } else if (choice == 4) {
            merchant.trade(player);
            return this; // Repeat scene after trading
        } else {
            System.out.println("Амулет светится и переносит вас через реку магическим образом!");
            player.setMorale(player.getMorale() + 20);
            System.out.println("Мораль: " + player.getMorale());
        }
        return new BanditsScene(player);
    }

    private void gameOver(String reason) {
        System.out.println("\n" + reason);
        player.printStatus();
        System.out.println("Игра окончена.");
        System.exit(0);
    }
}