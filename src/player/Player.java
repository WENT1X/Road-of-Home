package player;

import currency.*;
import buff.Buff;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int health;
    private int supplies;
    private int morale;
    private boolean hasSword;
    private boolean hasMap;
    private boolean hasCloak;
    private boolean hasAmulet;
    private List<Currency> currencies;
    private List<Buff> buffs;

    public Player() {
        this.health = 100;
        this.supplies = 0;
        this.morale = 0;
        this.hasSword = false;
        this.hasMap = false;
        this.hasCloak = false;
        this.hasAmulet = false;
        this.currencies = new ArrayList<>();
        this.currencies.add(new Gold(0));
        this.currencies.add(new Silver(0));
        this.currencies.add(new Gem(0));
        this.currencies.add(new Herb(0));
        this.currencies.add(new Relic(0));
        this.buffs = new ArrayList<>();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSupplies() {
        return supplies;
    }

    public void setSupplies(int supplies) {
        this.supplies = supplies;
    }

    public int getMorale() {
        return morale;
    }

    public void setMorale(int morale) {
        this.morale = morale;
    }

    public boolean hasSword() {
        return hasSword;
    }

    public void setSword(boolean hasSword) {
        this.hasSword = hasSword;
    }

    public boolean hasMap() {
        return hasMap;
    }

    public void setMap(boolean hasMap) {
        this.hasMap = hasMap;
    }

    public boolean hasCloak() {
        return hasCloak;
    }

    public void setCloak(boolean hasCloak) {
        this.hasCloak = hasCloak;
    }

    public boolean hasAmulet() {
        return hasAmulet;
    }

    public void setAmulet(boolean hasAmulet) {
        this.hasAmulet = hasAmulet;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public int getCurrencyAmount(String currencyName) {
        for (Currency currency : currencies) {
            if (currency.getName().equalsIgnoreCase(currencyName)) {
                return currency.getAmount();
            }
        }
        return 0;
    }

    public void addCurrency(Currency currency) {
        for (Currency existing : currencies) {
            if (existing.getName().equalsIgnoreCase(currency.getName())) {
                existing.addAmount(currency.getAmount());
                return;
            }
        }
        currencies.add(currency);
    }

    public boolean spendCurrency(String currencyName, int amount) {
        for (Currency currency : currencies) {
            if (currency.getName().equalsIgnoreCase(currencyName) && currency.getAmount() >= amount) {
                currency.addAmount(-amount);
                return true;
            }
        }
        return false;
    }

    public void addBuff(Buff buff) {
        buffs.add(buff);
        applyBuff(buff);
    }

    private void applyBuff(Buff buff) {
        switch (buff.getType()) {
            case "HealthBoost":
                setHealth(getHealth() + buff.getValue());
                break;
            case "MoraleBoost":
                setMorale(getMorale() + buff.getValue());
                break;
            case "DefenseBoost":
                // Defense reduces damage in future scenes (handled in scenes)
                break;
        }
    }

    public boolean hasBuff(String type) {
        for (Buff buff : buffs) {
            if (buff.getType().equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    public void printStatus() {
        System.out.println("Ваши текущие показатели: Здоровье = " + health + ", Припасы = " + supplies + ", Мораль = " + morale);
        System.out.print("Валюты: ");
        for (Currency currency : currencies) {
            System.out.print(currency.getName() + " = " + currency.getAmount() + "; ");
        }
        System.out.println();
        if (!buffs.isEmpty()) {
            System.out.print("Усиления: ");
            for (Buff buff : buffs) {
                System.out.print(buff.getType() + " (+" + buff.getValue() + "); ");
            }
            System.out.println();
        }
    }
}