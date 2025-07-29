package scene;

import npc.ActiveNPC;
import player.Player;
import utils.InputHandler;
import currency.*;

public class VillageScene extends Scene {
    public VillageScene(Player player) {
        super(player);
    }

    @Override
    public Scene play() {
        ActiveNPC oldMan = new ActiveNPC("Старик", "Помоги мне, и я укажу безопасный путь!", false);
        ActiveNPC merchant = new ActiveNPC("Торговец", "Купи что-нибудь, путник!", true);
        System.out.println("\nВы пришли в заброшенную деревню. Дома пусты, но вы замечаете дым из одной трубы и торговца неподалеку.");
        oldMan.interact();
        merchant.interact();
        System.out.println("1. Подойти к дому с дымом и помочь старику.");
        System.out.println("2. Обыскать другие дома.");
        System.out.println("3. Торговать с торговцем.");
        System.out.println("4. Покинуть деревню.");
        System.out.print("Ваш выбор (1, 2, 3 или 4): ");

        int choice = InputHandler.getChoice(1, 4);
        if (choice == 1) {
            System.out.println("1. Помочь старику (потратите припасы или серебро).");
            System.out.println("2. Отказаться и уйти.");
            System.out.print("Ваш выбор (1 или 2): ");
            int subChoice = InputHandler.getChoice(1, 2);
            if (subChoice == 1 && (player.getSupplies() >= 2 || player.getCurrencyAmount("Silver") >= 10)) {
                if (player.getSupplies() >= 2 && player.getCurrencyAmount("Silver") < 10) {
                    player.setSupplies(player.getSupplies() - 2);
                    System.out.println("Вы даете припасы старику.");
                } else {
                    player.spendCurrency("Silver", 10);
                    System.out.println("Вы даете серебро старику.");
                }
                player.setMorale(player.getMorale() + 20);
                System.out.println("Вы помогаете старику, и он делится историей о безопасном пути.");
                System.out.println("Ваши припасы: " + player.getSupplies() + ", Серебро: " + player.getCurrencyAmount("Silver") + ", Мораль: " + player.getMorale());
                player.setMap(true);
            } else if (subChoice == 1) {
                System.out.println("У вас недостаточно припасов или серебра. Старик прогоняет вас.");
                player.setMorale(player.getMorale() - 10);
                System.out.println("Мораль: " + player.getMorale());
            } else {
                System.out.println("Вы уходите, не помогая старику.");
                player.setMorale(player.getMorale() - 5);
                System.out.println("Мораль: " + player.getMorale());
            }
        } else if (choice == 2) {
            System.out.println("Вы находите ржавый меч, немного еды и золото.");
            player.setSword(true);
            player.setSupplies(player.getSupplies() + 1);
            player.addCurrency(new Gold(5));
            System.out.println("Ваши припасы: " + player.getSupplies() + ", Золото: " + player.getCurrencyAmount("Gold") + ", Меч получен.");
        } else if (choice == 3) {
            merchant.trade(player);
        } else {
            System.out.println("Вы покидаете деревню.");
        }
        return new RiverScene(player);
    }
}