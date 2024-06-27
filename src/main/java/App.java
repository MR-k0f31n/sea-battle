import model.Player;
import service.CreateNewPlayer;
import util.GameBoard;
import util.LogoGame;
import util.GameMode;

import java.util.Scanner;

/**
 * @author MR.k0F31n
 */
public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static Player player1;
    private static Player player2;

    public static void main(String[] args) {

        LogoGame.getLogoGame();

        player1 = CreateNewPlayer.createNewPlayer(scanner);
        System.out.println("Hello " + player1.getName() + " and welcome to \"Battle of the Ships\"");

        GameBoard.placeShips(player1);
        player1.setPlayerMove(true);

        GameBoard.printBoard(player1.getGameBoard());

        int gameMode = GameMode.SetGameMode(scanner);

        if (gameMode == 1) {
            player2 = CreateNewPlayer.createNewBot();
            GameBoard.placeShips(player2);
            GameBoard.printBoard(player2.getGameBoard());
        }
    }

}
