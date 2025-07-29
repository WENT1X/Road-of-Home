package buff;

public class Buff {
    private String type;
    private int value;

    public Buff(String type, int value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}