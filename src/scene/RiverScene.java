package scene;

import npc.ActiveNPC;
import player.Player;
import utils.InputHandler;
import utils.RandomEvent;
import currency.*;
import nation.Nation;

public class RiverScene extends Scene {
    private Nation nation;

    public RiverScene(Player player) {
        super(player);
        this.nation = new Nation("Морские Кланы", new String[]{"Silver", "Gem"});
    }

    @Override
    public Scene play() {
        System.out.println("\nВы находитесь в землях " + nation.getName() + ".");
        ActiveNPC fisherman = new ActiveNPC("Рыбак", "Река опасна, но я могу помочь, если ты поделишься припасами или " + nation.getCurrencies()[0] + ".", false, nation);
        ActiveNPC merchant = new ActiveNPC("Речной торговец", "У меня есть полезные вещи для путников!", true, nation);
        System.out.println("\nВы подходите к бурной реке. Через нее перекинут старый мост, но он выглядит ненадежно.");
        fisherman.interact();
        merchant.interact();
        System.out.println("1. Рискнуть и перейти мост.");
        System.out.println("2. Искать брод.");
        System.out.println("3. Дать рыбаку припасы или " + nation.getCurrencies()[0] + " за помощь.");
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
                System.out.println("Мост ломается, и вы падаете в реку!");
                int damage = player.hasBuff("DefenseBoost") ? 15 : 25;
                player.setHealth(player.getHealth() - damage);
                System.out.println("Здоровье: " + player.getHealth());
                if (player.getHealth() <= 0) {
                    gameOver("Река унесла вас.");
                    return null;
                }
                if (RandomEvent.occurs(20)) {
                    System.out.println("Вы находите " + nation.getCurrencies()[1] + " в воде!");
                    player.addCurrency(new Gem(2));
                }
            }
            player.printStatus();
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
            player.printStatus();
        } else if (choice == 3) {
            if (player.getSupplies() >= 2 || player.getCurrencyAmount(nation.getCurrencies()[0]) >= 8) {
                if (player.getSupplies() >= 2 && player.getCurrencyAmount(nation.getCurrencies()[0]) < 8) {
                    player.setSupplies(player.getSupplies() - 2);
                    System.out.println("Вы даете рыбаку припасы.");
                } else {
                    player.spendCurrency(nation.getCurrencies()[0], 8);
                    System.out.println("Вы даете рыбаку " + nation.getCurrencies()[0] + ".");
                }
                System.out.println("Рыбак переправляет вас на лодке!");
                player.setMorale(player.getMorale() + 15);
                System.out.println("Припасы: " + player.getSupplies() + ", " + nation.getCurrencies()[0] + ": " + player.getCurrencyAmount(nation.getCurrencies()[0]) + ", Мораль: " + player.getMorale());
                player.printStatus();
            } else {
                System.out.println("У вас недостаточно припасов или " + nation.getCurrencies()[0] + ". Рыбак отказывается помогать.");
                player.setMorale(player.getMorale() - 5);
                System.out.println("Мораль: " + player.getMorale());
                player.printStatus();
                return this; // Repeat scene
            }
        } else if (choice == 4) {
            merchant.trade(player);
            player.printStatus();
            return this; // Repeat scene after trading
        } else {
            System.out.println("Амулет светится и переносит вас через реку магическим образом!");
            player.setMorale(player.getMorale() + 20);
            System.out.println("Мораль: " + player.getMorale());
            player.printStatus();
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