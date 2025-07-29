package npc;

public abstract class NPC {
    protected String name;

    public NPC(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void interact();
}