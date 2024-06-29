package util;

import model.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author MR.k0F31n
 */
public class PlayerUtil {
    private static final GameBoard gameBoard = new GameBoard();
    /**
     * Создание игрока
     * @return возращает готовый обьект "Игрок" с пустыми полями и полным набором кораблей
     */
    public Player createNewPlayer(Scanner scanner) {
        String name;

        do {
            System.out.println("Введите своё имя: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Имя пустое! Так не пойдет");
            }
        } while (name.isEmpty());

        Player player = new Player();

        player.setName(name);
        player.setShips(newShips());
        player.setGameBoard(gameBoard.createNewBoard());
        player.setComponentGameBoard(gameBoard.createNewBoard());

        return player;
    }

    /**
     * Создаем игрока "БОТ"
     * @return возращает готовый обьект "Игрок" с пустыми полями и полным набором кораблей
     */

    public Player createNewBot() {
        Player bot = new Player();

        bot.setName("I'M_BOT");
        bot.setShips(newShips());
        bot.setGameBoard(gameBoard.createNewBoard());
        bot.setComponentGameBoard(gameBoard.createNewBoard());

        return bot;
    }

    private Map<Integer, Integer> newShips() {
        Map<Integer, Integer> ships = new HashMap<>();

        for (int i = 1; i < 7; i ++) {
            ships.put(7-i, i);
        }

        return ships;
    }
}
