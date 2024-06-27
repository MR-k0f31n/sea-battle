package service;

import model.Player;
import util.GameBoard;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author MR.k0F31n
 */
public class CreateNewPlayer {
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

    public static Player createNewBot() {
        Player bot = new Player();

        bot.setName("Player#2");
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
