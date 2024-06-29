import model.Art;
import service.Game;
import util.LogoGame;

/**
 * @author MR.k0F31n
 */
public class App {

    private static final Game game = new Game();

    public static void main(String[] args) {

        LogoGame.getLogoGame(Art.getLogo());
        game.gameRulesAndStartGame();

    }

}
