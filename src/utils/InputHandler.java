package utils;

import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static int getChoice(int min, int max) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.print("Пожалуйста, выберите число от " + min + " до " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Пожалуйста, введите число: ");
            }
        }
    }
}