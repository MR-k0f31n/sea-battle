package util;

import model.Player;

import java.util.Map;

/**
 * @author MR.k0F31n
 */
public class GameBoard {
    private static final int SIZE = 16;

    public static char[][] createNewBoard() {
        char[][] board = new char[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = '~';
            }
        }
        return board;
    }

    public static void printBoard(char[][] board) {
        // Print column labels (A-P)
        System.out.print("   ");
        for (int col = 0; col < SIZE; col++) {
            System.out.print((char) ('A' + col) + " ");
        }
        System.out.println();

        // Print board with row labels (1-16)
        for (int row = 0; row < SIZE; row++) {
            // Print row number
            System.out.printf("%2d ", row + 1);

            // Print each cell of the board
            for (int col = 0; col < SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    public static void placeShips(Player player) {
        for (Map.Entry<Integer, Integer> entry : player.getShips().entrySet()) {
            int shipSize = entry.getKey();
            int count = entry.getValue();
            placeShipsOfSize(shipSize, count, player.getGameBoard());
        }
    }
}
