package util;

import model.Art;

/**
 * @author MR.k0F31n
 */
public class LogoGame {
    public static void getLogoGame() {
        for (String line : Art.getArt()) {
            System.out.println(line);
        }
    }
}
