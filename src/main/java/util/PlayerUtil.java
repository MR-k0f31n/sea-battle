package util;

import model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author MR.k0F31n
 */
public class PlayerUtil {
    /**
     * Создание игрока
     * @return возращает готовый обьект "Игрок" с пустыми полями и полным набором кораблей
     */
    public static Player createNewPlayer(Scanner scanner) {
        String name;

        do {
            System.out.println("Enter your name: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name can't empty.");
            }
        } while (name.isEmpty());

        Player player = new Player();

        player.setName(name);
        player.setShips(newShips());
        player.setGameBoard(GameBoard.createNewBoard());
        player.setComponentGameBoard(GameBoard.createNewBoard());

        return player;
    }

    /**
     * Создаем игрока "БОТ"
     * @return возращает готовый обьект "Игрок" с пустыми полями и полным набором кораблей
     */

    public static Player createNewBot() {
        Player bot = new Player();

        bot.setName("I'M_BOT");
        bot.setShips(newShips());
        bot.setGameBoard(GameBoard.createNewBoard());
        bot.setComponentGameBoard(GameBoard.createNewBoard());

        return bot;
    }

    private static Map<Integer, Integer> newShips() {
        Map<Integer, Integer> ships = new HashMap<>();

        for (int i = 1; i < 7; i ++) {
            ships.put(7-i, i);
        }

        return ships;
    }
}
