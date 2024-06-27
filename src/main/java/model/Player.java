package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author MR.k0F31n
 */

@Data
@NoArgsConstructor
public class Player {
    private String name;
    private char[][] gameBoard;
    private char[][] componentGameBoard;
    private Map<Integer, Integer> ships;
    private boolean isPlayerMove;
}
