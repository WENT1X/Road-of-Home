package scene;

import npc.PassiveNPC;
import player.Player;
import utils.InputHandler;
import utils.RandomEvent;
import currency.*;
import nation.Nation;

public class ForestScene extends Scene {
    private Nation nation;

    public ForestScene(Player player) {
        super(player);
        this.nation = new Nation("Эльфийская Лига", new String[]{"Gold", "Herb"});
    }

    @Override
    public Scene play() {
        System.out.println("\nВы находитесь в землях " + nation.getName() + ".");
        PassiveNPC bird = new PassiveNPC("Птица на ветке", new String[]{
                "Чирп-чирп! Опасность близко!",
                "Крылья лучше ног, путник!"
        });
        bird.interact();

        if (RandomEvent.occurs(20)) {
            System.out.println("Вы нашли немного " + nation.getCurrencies()[1] + " на тропе!");
            player.addCurrency(new Herb(5));
            player.printStatus();
        }

        System.out.println("\nВы идете по лесной тропе. Вдалеке слышен вой волков.");
        System.out.println("Перед вами развилка: налево — темный лес, направо — заброшенная деревня, прямо — горная тропа.");
        System.out.println("1. Пойти налево, в темный лес.");
        System.out.println("2. Пойти направо, в заброшенную деревню.");
        System.out.println("3. Пойти прямо, по горной тропе.");
        System.out.print("Ваш выбор (1, 2 или 3): ");

        int choice = InputHandler.getChoice(1, 3);
        if (choice == 1) {
            System.out.println("\nВы вошли в темный лес. Здесь тихо, но чувствуется опасность.");
            if (RandomEvent.occurs(30) && !player.hasMap()) {
                System.out.println("Вас подстерегает стая волков!");
                return fightWolf();
            } else {
                System.out.println("Вы осторожно продвигаетесь через лес и находите заброшенную хижину.");
                System.out.println("1. Обыскать хижину.");
                System.out.println("2. Пройти мимо.");
                System.out.print("Ваш выбор (1 или 2): ");

                int subChoice = InputHandler.getChoice(1, 2);
                if (subChoice == 1) {
                    System.out.println("Внутри вы находите немного еды, " + nation.getCurrencies()[1] + " и странный амулет.");
                    player.setSupplies(player.getSupplies() + 2);
                    player.addCurrency(new Herb(3));
                    player.setAmulet(true);
                    System.out.println("Ваши припасы: " + player.getSupplies() + ", " + nation.getCurrencies()[1] + ": " + player.getCurrencyAmount(nation.getCurrencies()[1]) + ", Амулет получен.");
                    player.printStatus();
                } else {
                    System.out.println("Вы решаете не рисковать и идете дальше.");
                }
                return new RiverScene(player);
            }
        } else if (choice == 2) {
            return new VillageScene(player);
        } else {
            return new MountainScene(player);
        }
    }

    private Scene fightWolf() {
        System.out.println("1. Сражаться с волками.");
        System.out.println("2. Попытаться убежать.");
        System.out.print("Ваш выбор (1 или 2): ");

        int choice = InputHandler.getChoice(1, 2);
        int damage = player.hasBuff("DefenseBoost") ? 5 : 0; // Reduce damage if DefenseBoost
        if (choice == 1) {
            if (player.hasSword()) {
                System.out.println("С мечом вы отбиваетесь от волков, но получаете легкие раны.");
                player.setHealth(player.getHealth() - (10 - damage));
                player.setMorale(player.getMorale() + 10);
                System.out.println("Здоровье: " + player.getHealth() + ", Мораль: " + player.getMorale());
            } else {
                System.out.println("Без оружия бой тяжелый. Вы ранены.");
                player.setHealth(player.getHealth() - (30 - damage));
                player.setMorale(player.getMorale() - 10);
                System.out.println("Здоровье: " + player.getHealth() + ", Мораль: " + player.getMorale());
            }
            if (RandomEvent.occurs(30)) {
                System.out.println("Волки убегают, оставив немного " + nation.getCurrencies()[0] + "!");
                player.addCurrency(new Gold(3));
                player.printStatus();
            }
        } else {
            if (player.hasCloak()) {
                System.out.println("Плащ помогает вам скрыться в чаще.");
                player.setMorale(player.getMorale() + 5);
                System.out.println("Мораль: " + player.getMorale());
            } else {
                System.out.println("Вы убегаете, но волки ранят вас.");
                player.setHealth(player.getHealth() - (20 - damage));
                player.setMorale(player.getMorale() - 5);
                System.out.println("Здоровье: " + player.getHealth() + ", Мораль: " + player.getMorale());
            }
        }
        if (player.getHealth() <= 0) {
            gameOver("Волки оказались сильнее вас.");
            return null;
        }
        return new RiverScene(player);
    }

    private void gameOver(String reason) {
        System.out.println("\n" + reason);
        player.printStatus();
        System.out.println("Игра окончена.");
        System.exit(0);
    }
}