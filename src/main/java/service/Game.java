package service;

import model.Player;
import util.GameBoard;
import util.GameMode;
import util.PlayerUtil;

import java.util.Scanner;

/**
 * @author MR.k0F31n
 */
public class Game {

    private static final Scanner scanner = new Scanner(System.in);
    private static Player player1;
    private static Player player2;

    private static void startGame() {
        player1 = PlayerUtil.createNewPlayer(scanner);
        System.out.println("Привет " + player1.getName() + " и добро пожаловать в \"Battle of the Ships v1.0\"");
        player1.setPlayerMove(true);

        if (GameMode.setShipPlacementMode(scanner) == 1) {
            GameBoard.placeShips(player1, 1);
        } else {
            GameBoard.placeShips(player1, 2);
            System.out.println("Ознакомтесь с вашим полем:\n");
            GameBoard.printBoard(player1.getGameBoard(), player1.getName());
        }

        if (GameMode.setGameMode(scanner) == 1) {
            player2 = PlayerUtil.createNewBot();
            GameBoard.placeShips(player2, 2);
            GameBoard.printBoard(player2.getGameBoard(), player2.getName());
        }
    }

    public static void gameRulesAndStartGame() {
        Player player = PlayerUtil.createNewBot();
        GameBoard.placeShips(player, 2);

        System.out.println("Привет дорогой игрок, ты запусти «Морской бой», кратко расскажем правила:\n" +
                "Игровое поле состоит из матрицы 16x16. \n" +
                "Колонки подписаны буквами латинского алфавита A-P. Строки подписываются цифрами 1-16. \n" +
                "Корабли 6 типов: \n6 клеток – 1 штука, \n5 клеток – 2 штуки, \n4 клетки – 3 штуки, \n" +
                "3 клетки – 4 штуки, \n" +
                "2 клетки – 5 штук, \n1 клетка – 6 штук. \n" +
                "Координаты стоит вводить стандартно 1A, 10B и так далее, " +
                "следите пожалуйста за раскладкой клавиатуры ^.^ \n" +
                "Играть можно против игрока или бота, корабли расставлять можно как в ручную так и автоматически.\n\n");

        System.out.println("Примеры игровых полей:\n\n");
        GameBoard.printBoard(player.getGameBoard(), "Player#1");
        System.out.println("\nТак выглядит твое поле с раставленными кораблями\n\n");

        System.out.println("Поле игрового опонента, на коротом будут отмечаться нанесенные удары сиволом * " +
                "и убитые или ранненые коробали символом Х");
        GameBoard.printBoard(player.getComponentGameBoard(), "Player#2");
        System.out.println("🚢 GOOD LUCK! 🚢");
        startGame();
    }
}
