package util;

import java.util.Scanner;

/**
 * @author MR.k0F31n
 */
public class GameMode {

    /**
     * Метод заданаия режима игры, один или против живого игрока
     */
    public static int setGameMode(Scanner scanner) {
        int gameMode;
        do {
            System.out.print("Как вы хотите играть? \nодин(1) или с другими игроками(2) ");
            while (!scanner.hasNextInt()) {
                System.out.println("Пожалуйста введите 1 или 2");
                scanner.next();
            }
            gameMode = scanner.nextInt();

            if (gameMode != 1 && gameMode != 2) {
                System.out.println("Некорректный выбор, пожалуйста ввеите 1 или 2");
            }
        } while (gameMode != 1 && gameMode != 2);

        if (gameMode == 1) {
            System.out.println("Вы будете играть против бота");
        } else {
            System.out.println("Ожидаем опонента");
        }

        return gameMode;
    }

    /**
     * Метод задающий режим расстановки кораблей, игрок сам будет расставлять или автоматически
     */
    public static int setShipPlacementMode(Scanner scanner) {
        int gameMode;
        do {
            System.out.println("Как бы вы хотели расставить корабли? \nСамостоятельно (1) или Автоматически(2)");
            while (!scanner.hasNextInt()) {
                System.out.println("Пожалуйста введите 1 или 2");
                scanner.next();
            }
            gameMode = scanner.nextInt();

            if (gameMode != 1 && gameMode != 2) {
                System.out.println("Некорректный выбор, пожалуйста ввеите 1 или 2");
            }
        } while (gameMode != 1 && gameMode != 2);

        if (gameMode == 1) {
            System.out.println("Расставьте пожалуйста свои корабли");
        } else {
            System.out.println("Корабли расставлены автоматически");
        }

        return gameMode;
    }
}
