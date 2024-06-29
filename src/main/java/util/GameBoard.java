package util;

import model.Player;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * @author MR.k0F31n
 */
public class GameBoard {
    private static final int SIZE = 16;
    private static final char EMPTY_CELL = '~';
    private static final char SHIP_CELL = '■';

    /**
     * Создаем пустую матрицу "Моря"
     *
     * @return возвращаем матрицу
     */
    public char[][] createNewBoard() {
        char[][] board = new char[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            Arrays.fill(board[row], '~');
        }
        return board;
    }

    /**
     * Метод вывода игровых полей
     *
     * @param board - передается матрица игрого поля
     */
    public void printBoard(char[][] board, String name) {
        System.out.println("Поле игрока: " + name + " \n");
        // Печать координат по горизонтали A-P
        System.out.print("   ");
        for (int col = 0; col < SIZE; col++) {
            System.out.print((char) ('A' + col) + " ");
        }
        System.out.println();

        // Печать координат по вертикали 1-16
        for (int row = 0; row < SIZE; row++) {
            System.out.printf("%2d ", row + 1);

            // Печатаем ячейки
            for (int col = 0; col < SIZE; col++) {
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Автоматическая расстановка кораблей
     *
     * @param player - передаем обьект игрока, нужно для получения игрового поля.
     */
    public void placeShips(Player player, int mode) {
        int[] shipSizes = {6, 5, 5, 4, 4, 4, 3, 3, 3, 3, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1};
        for (int shipSize : shipSizes) {
            if (mode == 2) {
                placeShipsOfSize(shipSize, 1, player.getGameBoard());
            } else {
                placeShipByPlayer(shipSize, player.getGameBoard(), player.getName());
            }
        }
    }

    /**
     * Ручной метод расстановки короблей, с проверкой корректности внесенных координат защищая границы по 1-16, A-P
     */
    private void placeShipByPlayer(int shipSize, char[][] board, String name) {
        Scanner scanner = new Scanner(System.in);
        printBoard(board, name);
        boolean placed = false;
        while (!placed) {

            System.out.println("Введите координаты для корабля размером " + shipSize + " (например, 2A, 2B, 2C, 2D, 2E, 2F):");
            String input = scanner.nextLine().toUpperCase();
            String[] coordinates = input.split(",");

            if (coordinates.length != shipSize) {
                System.out.println("Неверное количество координат. Попробуйте еще раз.");
                continue;
            }

            int[] rows = new int[shipSize];
            int[] cols = new int[shipSize];

            boolean validInput = true;
            for (int i = 0; i < shipSize; i++) {
                String coordinate = coordinates[i].trim();
                if (!coordinate.matches("\\d{1,2}[A-P]")) {
                    System.out.println("Неверная координата: " + coordinate + ". Попробуйте еще раз.");
                    validInput = false;
                    break;
                }
                rows[i] = Integer.parseInt(coordinate.substring(0, coordinate.length() - 1)) - 1;
                cols[i] = coordinate.charAt(coordinate.length() - 1) - 'A';

                if (isOutOfBounds(rows[i], cols[i])) {
                    System.out.println("Координаты за пределами границ: " + coordinate + ". Попробуйте еще раз.");
                    validInput = false;
                    break;
                }
            }

            if (validInput && canPlaceShipWithBuffer(rows, cols, shipSize, board)) {
                placeShip(rows, cols, shipSize, board);
                placed = true;
            } else if (validInput) {
                System.out.println("Невозможно разместить корабль по этим координатам. Попробуйте еще раз.");
            }
        }
    }

    /**
     * Проверка возможности разместить корабль, учитывая правило между короблями по всем направлениям 1 клетка
     */
    private boolean canPlaceShipWithBuffer(int[] rows, int[] cols, int shipSize, char[][] board) {
        for (int i = 0; i < shipSize; i++) {
            int row = rows[i];
            int col = cols[i];
            for (int r = row - 1; r <= row + 1; r++) {
                for (int c = col - 1; c <= col + 1; c++) {
                    if (!isOutOfBounds(r, c) && board[r][c] != EMPTY_CELL) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Рисуем кораблик
     */
    private void placeShip(int[] rows, int[] cols, int shipSize, char[][] board) {
        for (int i = 0; i < shipSize; i++) {
            board[rows[i]][cols[i]] = SHIP_CELL;
        }
    }

    /**
     * Расставляем корабли автоматически
     *
     * @param shipSize - размер корабля (палубность)
     * @param count    - количество таких кораблей
     * @param board    - передаем игровое поле где их нужно установить
     */
    private void placeShipsOfSize(int shipSize, int count, char[][] board) {
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            boolean placed = false;
            int attempts = 0;
            while (!placed && attempts < 1000) {
                attempts++;
                //выбираем 2 координаты
                int startRow = rand.nextInt(SIZE);
                int startCol = rand.nextInt(SIZE);
                //не стал мудрить и 1 размерный корабль выбираем как с ним поступить
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


    /**
     * Проверка на возмодности размещение коробля для автоматического режима
     *
     * @param startRow   - начальная позиция по 1-16
     * @param startCol   - начальная позиция по A-P
     * @param shipSize   - сколько клеток нужно кораблю
     * @param horizontal - горизонтально\вертикальное распложение корабля
     * @param board      - игровое поле
     * @return - возвращает возможность размещение корабля
     */
    private boolean canPlaceShip(int startRow, int startCol, int shipSize, boolean horizontal, char[][] board) {
        if (horizontal) {
            // Проверка что корабль помещается в игровое поле
            if (startCol + shipSize > SIZE) {
                return false;
            }
            // Проверка на пересечение с другими кораблями
            for (int row = startRow - 1; row <= startRow + 1; row++) {
                for (int col = startCol - 1; col <= startCol + shipSize; col++) {
                    // Вмещается ли корабль
                    if (isOutOfBounds(row, col) || board[row][col] != EMPTY_CELL) {
                        return false;
                    }
                }
            }
        } else {
            if (startRow + shipSize > SIZE) {
                return false;
            }
            // Проверка на пересечение с другими кораблями
            for (int row = startRow - 1; row <= startRow + shipSize; row++) {
                for (int col = startCol - 1; col <= startCol + 1; col++) {
                    // Вмещается ли корабль
                    if (isOutOfBounds(row, col) || board[row][col] != EMPTY_CELL) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    /**
     * Размещение корабля автоматически
     *
     * @param startRow   - начальная координата по 1-16
     * @param startCol   - начальная кордината по A-P
     * @param shipSize   - размер корабля
     * @param horizontal - горизонтально\вертикальное распложение корабля
     * @param board      - игровое поле
     */
    private void placeShip(int startRow, int startCol, int shipSize, boolean horizontal, char[][] board) {
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

    /**
     * Проверка вместимости корабля
     */
    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= SIZE || col < 0 || col >= SIZE;
    }

}
