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
        player1.setPlayerMove(true);
        GameBoard.placeShips(player1);

        System.out.println("Hello and welcome to \"Battle of the Ships\"");

        System.out.println("Hello " + player1.getName() + "!");

        int gameMode = GameMode.SetGameMode(scanner);

        if (gameMode == 2) {
            player2 = CreateNewPlayer.createNewBot();
        }
    }

}
