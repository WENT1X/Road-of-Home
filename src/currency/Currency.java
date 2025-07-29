package currency;

public abstract class Currency {
    protected String name;
    protected int amount;

    public Currency(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }
}