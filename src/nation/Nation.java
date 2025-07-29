package nation;

public class Nation {
    private String name;
    private String[] currencies;

    public Nation(String name, String[] currencies) {
        this.name = name;
        this.currencies = currencies;
    }

    public String getName() {
        return name;
    }

    public String[] getCurrencies() {
        return currencies;
    }
}