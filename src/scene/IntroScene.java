package scene;

import npc.ActiveNPC;
import npc.PassiveNPC;
import player.Player;
import utils.InputHandler;

public class IntroScene extends Scene {
    public IntroScene(Player player) {
        super(player);
    }

    @Override
    public Scene play() {
        PassiveNPC wanderer = new PassiveNPC("Странник", new String[]{
                "Этот мир полон загадок, но я слишком стар, чтобы их разгадывать.",
                "Видел ли ты звезды? Они шепчут о конце времен."
        });
        wanderer.interact();

        System.out.println("\nВы стоите на краю родной деревни, собираясь в долгий путь домой.");
        ActiveNPC elder = new ActiveNPC("Старейшина", "Выбери предмет для путешествия.", false);
        elder.interact();
        System.out.println("1. Старый меч (увеличивает шансы в бою).");
        System.out.println("2. Карта (помогает избегать опасностей).");
        System.out.println("3. Плащ (защищает от непогоды).");
        System.out.print("Ваш выбор (1, 2 или 3): ");

        int choice = InputHandler.getChoice(1, 3);
        if (choice == 1) {
            player.setSword(true);
            System.out.println("Вы взяли старый меч.");
        } else if (choice == 2) {
            player.setMap(true);
            System.out.println("Вы взяли карту.");
        } else {
            player.setCloak(true);
            System.out.println("Вы взяли плащ.");
        }
        return new ForestScene(player);
    }
}