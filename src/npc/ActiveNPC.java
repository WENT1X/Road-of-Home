package npc;

import player.Player;
import nation.Nation;
import buff.Buff;
import java.util.HashMap;
import java.util.Map;

public class ActiveNPC extends NPC {
    private String dialogue;
    private boolean isMerchant;
    private Nation nation;
    private Map<String, Integer> itemsForSale; // Item name -> Price in nation's primary currency
    private Map<String, Buff> buffsForSale; // Buff type -> Buff

    public ActiveNPC(String name, String dialogue, boolean isMerchant, Nation nation) {
        super(name);
        this.dialogue = dialogue;
        this.isMerchant = isMerchant;
        this.nation = nation;
        this.itemsForSale = new HashMap<>();
        this.buffsForSale = new HashMap<>();
        if (isMerchant) {
            itemsForSale.put("Меч", 10);
            itemsForSale.put("Карта", 15);
            itemsForSale.put("Плащ", 12);
            itemsForSale.put("Амулет", 20);
            buffsForSale.put("HealthBoost", new Buff("HealthBoost", 20));
            buffsForSale.put("MoraleBoost", new Buff("MoraleBoost", 15));
            buffsForSale.put("DefenseBoost", new Buff("DefenseBoost", 10));
        }
    }

    public String getDialogue() {
        return dialogue;
    }

    public boolean isMerchant() {
        return isMerchant;
    }

    public Nation getNation() {
        return nation;
    }

    @Override
    public void interact() {
        System.out.println(name + " (" + nation.getName() + ") говорит: " + dialogue);
        if (isMerchant) {
            System.out.println("Я продаю предметы и усиления за " + nation.getCurrencies()[0] + "!");
        }
    }

    public void trade(Player player) {
        if (!isMerchant) {
            System.out.println(name + " не торгует.");
            return;
        }
        System.out.println("Товары на продажу (цены в " + nation.getCurrencies()[0] + "):");
        int index = 1;
        for (Map.Entry<String, Integer> item : itemsForSale.entrySet()) {
            System.out.println(index++ + ". " + item.getKey() + " (" + item.getValue() + ")");
        }
        for (Map.Entry<String, Buff> buff : buffsForSale.entrySet()) {
            System.out.println(index++ + ". " + buff.getKey() + " (+" + buff.getValue().getValue() + ", 15)");
        }
        System.out.println(index + ". Уйти.");
        System.out.print("Ваш выбор: ");

        int choice = utils.InputHandler.getChoice(1, index);
        if (choice == index) {
            System.out.println("Вы уходите от продавца.");
            return;
        }

        int itemIndex = 1;
        for (Map.Entry<String, Integer> item : itemsForSale.entrySet()) {
            if (choice == itemIndex) {
                if (player.spendCurrency(nation.getCurrencies()[0], item.getValue())) {
                    System.out.println("Вы купили " + item.getKey() + "!");
                    switch (item.getKey()) {
                        case "Меч":
                            player.setSword(true);
                            break;
                        case "Карта":
                            player.setMap(true);
                            break;
                        case "Плащ":
                            player.setCloak(true);
                            break;
                        case "Амулет":
                            player.setAmulet(true);
                            break;
                    }
                } else {
                    System.out.println("Недостаточно " + nation.getCurrencies()[0] + "!");
                }
                return;
            }
            itemIndex++;
        }

        for (Map.Entry<String, Buff> buff : buffsForSale.entrySet()) {
            if (choice == itemIndex) {
                if (player.spendCurrency(nation.getCurrencies()[0], 15)) {
                    System.out.println("Вы купили усиление " + buff.getKey() + "!");
                    player.addBuff(buff.getValue());
                } else {
                    System.out.println("Недостаточно " + nation.getCurrencies()[0] + "!");
                }
                return;
            }
            itemIndex++;
        }
    }
}