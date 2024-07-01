package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author MR.k0F31n
 */

public class Bot extends Player {
    private List<int[]> hitCoordinates = new ArrayList<>();
    private Random random = new Random();

    private static final char EMPTY_CELL = '~';

    public int[] getNextMove() {
        // Если есть координаты раненого корабля, пробуем добить
        if (!hitCoordinates.isEmpty()) {
            int[] coord = hitCoordinates.get(0);
            return findNextHitAround(coord);
        }
        // Иначе генерируем случайный ход
        return generateRandomMove();
    }

    private int[] findNextHitAround(int[] coord) {
        int row = coord[0];
        int col = coord[1];
        int[][] possibleMoves = {{row-1, col}, {row+1, col}, {row, col-1}, {row, col+1}};
        for (int[] move : possibleMoves) {
            int r = move[0];
            int c = move[1];
            if (isValidMove(r, c) && getComponentGameBoard()[r][c] == EMPTY_CELL) {
                return move;
            }
        }
        // Если не удалось найти подходящий ход рядом, удаляем координату и пробуем заново
        hitCoordinates.remove(0);
        return getNextMove();
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < 16 && col >= 0 && col < 16;
    }

    private int[] generateRandomMove() {
        int row, col;
        do {
            row = random.nextInt(16);
            col = random.nextInt(16);
        } while (getComponentGameBoard()[row][col] != EMPTY_CELL);
        return new int[]{row, col};
    }

    public void registerHit(int row, int col) {
        hitCoordinates.add(new int[]{row, col});
    }

    public void clearHits() {
        hitCoordinates.clear();
    }
}
