package npc;

import java.util.Random;

public class PassiveNPC extends NPC {
    private String[] dialogues;
    private Random random = new Random();

    public PassiveNPC(String name, String[] dialogues) {
        super(name);
        this.dialogues = dialogues;
    }

    @Override
    public void interact() {
        if (dialogues.length > 0) {
            String dialogue = dialogues[random.nextInt(dialogues.length)];
            System.out.println(name + " говорит: " + dialogue);
        }
    }
}