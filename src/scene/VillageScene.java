package scene;

import npc.ActiveNPC;
import player.Player;
import utils.InputHandler;
import utils.RandomEvent;
import currency.*;
import nation.Nation;

public class VillageScene extends Scene {
    private Nation nation;

    public VillageScene(Player player) {
        super(player);
        this.nation = new Nation("Королевство Людей", new String[]{"Gold", "Silver"});
    }

    @Override
    public Scene play() {
        System.out.println("\nВы находитесь в землях " + nation.getName() + ".");
        ActiveNPC oldMan = new ActiveNPC("Старик", "Помоги мне, и я укажу безопасный путь!", false, nation);
        ActiveNPC merchant = new ActiveNPC("Торговец", "Купи что-нибудь, путник!", true, nation);
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
            System.out.println("1. Помочь старику (потратите припасы или " + nation.getCurrencies()[1] + ").");
            System.out.println("2. Отказаться и уйти.");
            System.out.print("Ваш выбор (1 или 2): ");
            int subChoice = InputHandler.getChoice(1, 2);
            if (subChoice == 1 && (player.getSupplies() >= 2 || player.getCurrencyAmount(nation.getCurrencies()[1]) >= 10)) {
                if (player.getSupplies() >= 2 && player.getCurrencyAmount(nation.getCurrencies()[1]) < 10) {
                    player.setSupplies(player.getSupplies() - 2);
                    System.out.println("Вы даете припасы старику.");
                } else {
                    player.spendCurrency(nation.getCurrencies()[1], 10);
                    System.out.println("Вы даете " + nation.getCurrencies()[1] + " старику.");
                }
                player.setMorale(player.getMorale() + 20);
                System.out.println("Вы помогаете старику, и он делится историей о безопасном пути.");
                System.out.println("Ваши припасы: " + player.getSupplies() + ", " + nation.getCurrencies()[1] + ": " + player.getCurrencyAmount(nation.getCurrencies()[1]) + ", Мораль: " + player.getMorale());
                player.setMap(true);
                player.printStatus();
            } else if (subChoice == 1) {
                System.out.println("У вас недостаточно припасов или " + nation.getCurrencies()[1] + ". Старик прогоняет вас.");
                player.setMorale(player.getMorale() - 10);
                System.out.println("Мораль: " + player.getMorale());
                player.printStatus();
            } else {
                System.out.println("Вы уходите, не помогая старику.");
                player.setMorale(player.getMorale() - 5);
                System.out.println("Мораль: " + player.getMorale());
                player.printStatus();
            }
        } else if (choice == 2) {
            System.out.println("Вы находите ржавый меч, немного еды и " + nation.getCurrencies()[0] + ".");
            player.setSword(true);
            player.setSupplies(player.getSupplies() + 1);
            player.addCurrency(new Gold(5));
            System.out.println("Ваши припасы: " + player.getSupplies() + ", " + nation.getCurrencies()[0] + ": " + player.getCurrencyAmount(nation.getCurrencies()[0]) + ", Меч получен.");
            player.printStatus();
        } else if (choice == 3) {
            merchant.trade(player);
            player.printStatus();
        } else {
            System.out.println("Вы покидаете деревню.");
        }
        return new RiverScene(player);
    }
}