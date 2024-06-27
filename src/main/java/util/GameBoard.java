package util;

import model.Player;

import java.util.Arrays;
import java.util.Random;

/**
 * @author MR.k0F31n
 */
public class GameBoard {
    private static final int SIZE = 16;
    private static final char EMPTY_CELL = '~';
    private static final char SHIP_CELL = '■';

    public static char[][] createNewBoard() {
        char[][] board = new char[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            Arrays.fill(board[row], '~');
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
        int[] shipSizes = {6, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1};
        for (int shipSize : shipSizes) {
            placeShipsOfSize(shipSize, 1, player.getGameBoard());
        }
    }

    private static void placeShipsOfSize(int shipSize, int count, char[][] board) {
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            boolean placed = false;
            int attempts = 0;

            while (!placed && attempts < 1000) {
                attempts++;
                int startRow = rand.nextInt(SIZE);
                int startCol = rand.nextInt(SIZE);
                boolean horizontal = rand.nextBoolean();

                if (canPlaceShip(startRow, startCol, shipSize, horizontal, board)) {
                    placeShip(startRow, startCol, shipSize, horizontal, board);
                    placed = true;
                }
            }

            if (!placed) {
                throw new RuntimeException("Unable to place the ship after " + 1000 + " attempts.");
            }
        }
    }

    private static boolean canPlaceShip(int startRow, int startCol, int shipSize, boolean horizontal, char[][] board) {
        if (horizontal) {
            if (startCol + shipSize > SIZE) {
                return false;
            }
            for (int row = startRow - 1; row <= startRow + 1; row++) {
                for (int col = startCol - 1; col <= startCol + shipSize; col++) {
                    if (isOutOfBounds(row, col) || board[row][col] != EMPTY_CELL) {
                        return false;
                    }
                }
            }
        } else {
            if (startRow + shipSize > SIZE) {
                return false;
            }
            for (int row = startRow - 1; row <= startRow + shipSize; row++) {
                for (int col = startCol - 1; col <= startCol + 1; col++) {
                    if (isOutOfBounds(row, col) || board[row][col] != EMPTY_CELL) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static void placeShip(int startRow, int startCol, int shipSize, boolean horizontal, char[][] board) {
        if (horizontal) {
            for (int col = startCol; col < startCol + shipSize; col++) {
                board[startRow][col] = SHIP_CELL;
            }
        } else {
            for (int row = startRow; row < startRow + shipSize; row++) {
                board[row][startCol] = SHIP_CELL;
            }
        }
    }

    private static boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= SIZE || col < 0 || col >= SIZE;
    }

}
