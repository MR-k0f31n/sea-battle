package util;

import java.util.Scanner;

/**
 * @author MR.k0F31n
 */
public class GameMode {

    public static int SetGameMode(Scanner scanner) {
        int gameMode;
        do {
            System.out.print("How do you want to play? Against AI(1) or against a player(2): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter the number 1 or 2.");
                scanner.next();
            }
            gameMode = scanner.nextInt();

            if (gameMode != 1 && gameMode != 2) {
                System.out.println("Incorrect selection. Please try again.");
            }
        } while (gameMode != 1 && gameMode != 2);

        if (gameMode == 1) {
            System.out.println("You will compete against a bot.");
        } else {
            System.out.println("We are expecting an opponent");
        }

        return gameMode;
    }
}
