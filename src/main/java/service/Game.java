package service;

import model.Art;
import model.Player;
import util.GameBoard;
import util.GameMode;
import util.LogoGame;
import util.PlayerUtil;

import java.util.Map;
import java.util.Scanner;

/**
 * @author MR.k0F31n
 */
public class Game {

    private static final GameBoard gameBoard = new GameBoard();
    private static final PlayerUtil playerUtil = new PlayerUtil();

    private static final int SIZE = 16;
    private static final char EMPTY_CELL = '~';
    private static final char SHIP_CELL = '■';
    private static final char WOUNDED_CELL = '□';
    private static final char DESTROYED_CELL = 'X';
    private static final char PAST_CELL = '*';

    /**
     * Основная игровая механика, конфигурация игры
     */
    private void startGame() {
        Player player1;
        Player player2 = null;

        Scanner scanner = new Scanner(System.in);
        //устанавливаем игрока 1
        player1 = playerUtil.createNewPlayer(scanner);
        System.out.println("Привет " + player1.getName() + " и добро пожаловать в \"Battle of the Ships v1.0\"");
        player1.setPlayerMove(true);
        // устанавливаем метод формирования игровго поля
        if (GameMode.setShipPlacementMode(scanner) == 1) {
            gameBoard.placeShips(player1, 1);
            System.out.println("Ознакомтесь с вашим полем:\n");
            gameBoard.printBoard(player1.getGameBoard(), player1.getName());
        } else {
            gameBoard.placeShips(player1, 2);
            System.out.println("Ознакомтесь с вашим полем:\n");
            gameBoard.printBoard(player1.getGameBoard(), player1.getName());
        }
        // устанавливаем игровой режим сингл или мульти
        if (GameMode.setGameMode(scanner) == 1) {
            player2 = playerUtil.createNewBot();
            gameBoard.placeShips(player2, 2);
        }
        // добро пожаловать в ад
        boolean gameOver = false;
        while (!gameOver) {
            if (player2 != null) {
                if (player1.isPlayerMove()) {
                    play(player1, player2);
                    if (player2.getShips().isEmpty()) {
                        System.out.println("Победит игрок " + player1.getName());
                        gameOver = true;
                    }
                } else if (player2.isPlayerMove()) {
                    play(player2, player1);
                    if (player1.getShips().isEmpty()) {
                        System.out.println("Победил игрок " + player2.getName());
                        gameOver = true;
                    }
                }
            }
        }
        LogoGame.getLogoGame(Art.getGameOver());
    }

    /**
     * Запускаем игру, показываем правила и по готовности игрок может вводить имя
     */
    public void gameRulesAndStartGame() {
        Player player = playerUtil.createNewBot();
        gameBoard.placeShips(player, 2);

        System.out.println("Привет дорогой игрок, ты запусти «Морской бой», кратко расскажем правила:\n" +
                "Игровое поле состоит из матрицы 16x16. \n" +
                "Колонки подписаны буквами латинского алфавита A-P. Строки подписываются цифрами 1-16. \n" +
                "Корабли 6 типов: \n6 клеток – 1 штука, \n5 клеток – 2 штуки, \n4 клетки – 3 штуки, \n" +
                "3 клетки – 4 штуки, \n" +
                "2 клетки – 5 штук, \n1 клетка – 6 штук. \n" +
                "Координаты стоит вводить стандартно 1A, 10B и так далее, " +
                "следите пожалуйста за раскладкой клавиатуры ^.^ \n" +
                "Играть можно против игрока или бота, корабли расставлять можно как в ручную так и автоматически.\n" +
                "Главное помните, когда расставляете корабли вручную должно" +
                " быть расстояние 1 клетка по всем направлениям между кораблям\n" +
                "вводимые данные проверяются, с хитрить не получиться ");

        System.out.println("Примеры игровых полей:\n");
        gameBoard.printBoard(player.getGameBoard(), "Player#1");
        System.out.println("\nТак выглядит твое поле с раставленными кораблями\n");

        System.out.println("Поле игрового опонента ниже, на коротом будут отмечаться нанесенные удары \nСиволом " + PAST_CELL +
                " если мимо, \nУбитые корабли отмечаются " + DESTROYED_CELL + ", \nРанненые коробали отмечаются символом "
                + WOUNDED_CELL);
        gameBoard.printBoard(player.getComponentGameBoard(), "Player#2");
        System.out.println("🚢 GOOD LUCK! 🚢");
        startGame();
    }

    /**
     * Основная механика игры, передается 2 игрока играющий и принимающий удар
     */
    private void play(Player player, Player opoPlayer) {
        System.out.println("\nОчередь игрока " + player.getName() + "\n");
        Scanner scanner = new Scanner(System.in);
        boolean placeAttack = false;

        gameBoard.printBoard(player.getComponentGameBoard(), opoPlayer.getName());

        System.out.println("\nУ врага осталось кораблей:");
        printCountShips(opoPlayer.getShips());
        System.out.println("Командуйте командир - куда нанести удар?");

        while (!placeAttack) {
            System.out.println("(например: 2A) 'следите за раскладкой клавиатуры' \nЖдем команды: ");
            String coordinate = scanner.nextLine().toUpperCase();
            if (!coordinate.matches("\\d{1,2}[A-P]")) {
                System.out.println("Неверная координата: " + coordinate + ". Попробуйте еще раз.");
            } else {
                int row = Integer.parseInt(coordinate.substring(0, coordinate.length() - 1)) - 1; // Получаем строку (2 -> 1)
                int col = coordinate.charAt(coordinate.length() - 1) - 'A';
                char c = opoPlayer.getGameBoard()[row][col];
                if (c == PAST_CELL || c == WOUNDED_CELL || c == DESTROYED_CELL) {
                    System.out.println("Коммандир, мы уже били по этим координатам!");
                }
                switch (c) {
                    case EMPTY_CELL:
                        System.out.println("Мимо!");
                        registerAnAttack(player, opoPlayer, row, col, PAST_CELL);
                        player.setPlayerMove(false);
                        opoPlayer.setPlayerMove(true);
                        break;
                    case SHIP_CELL:
                        System.out.println("Есть пробитие!");
                        registerAnAttack(player, opoPlayer, row, col, WOUNDED_CELL);
                        if (checkDestroyShip(row, col, opoPlayer.getGameBoard())) {
                            int destroyShips = markDestroyedShip(player, opoPlayer, row, col);
                            opoPlayer.destroyShip(destroyShips);
                            System.out.println("Убил!");
                            break;
                        }
                }
            }
            placeAttack = true;
        }
    }

    /**
     * Регистрируем попадание
     */
    private void registerAnAttack(Player player, Player opoPlayer, int row, int col, char c) {
        opoPlayer.setCharOnBoardAttack(row, col, c);
        player.setCharOnBoardMemory(row, col, c);
    }

    /**
     * Проверка на уничтоженый корабль при попадании в любое судно
     */
    private boolean checkDestroyShip(int startRow, int startCol, char[][] board) {
        // Проверка однопалубного корабля
        if ((isBorderOrEmpty(board, startRow - 1, startCol)) &&
                (isBorderOrEmpty(board, startRow + 1, startCol)) &&
                (isBorderOrEmpty(board, startRow, startCol - 1)) &&
                (isBorderOrEmpty(board, startRow, startCol + 1))) {
            return true;
        }

        // Проверка вверх
        for (int i = startRow - 1; i >= 0; i--) {
            if (board[i][startCol] == SHIP_CELL) {
                return false;
            } else if (board[i][startCol] == EMPTY_CELL || board[i][startCol] == PAST_CELL) {
                break;
            }
        }

        // Проверка вниз
        for (int i = startRow + 1; i < SIZE; i++) {
            if (board[i][startCol] == SHIP_CELL) {
                return false;
            } else if (board[i][startCol] == EMPTY_CELL || board[i][startCol] == PAST_CELL) {
                break;
            }
        }

        // Проверка влево
        for (int j = startCol - 1; j >= 0; j--) {
            if (board[startRow][j] == SHIP_CELL) {
                return false;
            } else if (board[startRow][j] == EMPTY_CELL || board[startRow][j] == PAST_CELL) {
                break;
            }
        }

        // Проверка вправо
        for (int j = startCol + 1; j < SIZE; j++) {
            if (board[startRow][j] == SHIP_CELL) {
                return false;
            } else if (board[startRow][j] == EMPTY_CELL || board[startRow][j] == PAST_CELL) {
                break;
            }
        }

        // Если все направления чисты, возвращаем true (корабль убит)
        return true;
    }

    // Метод для проверки границы или пустой ячейки
    private boolean isBorderOrEmpty(char[][] board, int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return true; // Граница считается пустой
        }
        return board[row][col] == EMPTY_CELL || board[row][col] == PAST_CELL;
    }

    /**
     * Перереисовываем уничтоженный корабль и подсчитываем какой имено был уничтожен корабль
     * Возвращаем уничтоженый корабль что бы удалить его у игрока
     */
    private int markDestroyedShip(Player player, Player opoPlayer, int row, int col) {
        int count = 0;
        char[][] board = opoPlayer.getGameBoard();

        // Определение направления корабля (горизонтальный или вертикальный)
        boolean isHorizontal = false;
        boolean isVertical = false;

        // Проверка на горизонтальный корабль
        if ((col > 0 && board[row][col - 1] == WOUNDED_CELL) || (col < SIZE - 1 && board[row][col + 1] == WOUNDED_CELL)) {
            isHorizontal = true;
        }

        // Проверка на вертикальный корабль
        if ((row > 0 && board[row - 1][col] == WOUNDED_CELL) || (row < SIZE - 1 && board[row + 1][col] == WOUNDED_CELL)) {
            isVertical = true;
        }

        // Замена горизонтального корабля
        if (isHorizontal) {
            for (int j = col; j >= 0; j--) {
                if (board[row][j] == WOUNDED_CELL) {
                    board[row][j] = DESTROYED_CELL;
                    player.setCharOnBoardMemory(row, j, DESTROYED_CELL);
                    opoPlayer.setCharOnBoardAttack(row, j, DESTROYED_CELL);
                    count++;
                } else if (board[row][j] == EMPTY_CELL) {
                    break;
                }
            }
            for (int j = col + 1; j < SIZE; j++) {
                if (board[row][j] == WOUNDED_CELL) {
                    board[row][j] = DESTROYED_CELL;
                    player.setCharOnBoardMemory(row, j, DESTROYED_CELL);
                    opoPlayer.setCharOnBoardAttack(row, j, DESTROYED_CELL);
                    count++;
                } else if (board[row][j] == EMPTY_CELL) {
                    break;
                }
            }
        }

        // Замена вертикального корабля
        if (isVertical) {
            for (int i = row; i >= 0; i--) {
                if (board[i][col] == WOUNDED_CELL) {
                    board[i][col] = DESTROYED_CELL;
                    player.setCharOnBoardMemory(i, col, DESTROYED_CELL);
                    opoPlayer.setCharOnBoardAttack(i, col, DESTROYED_CELL);
                    count++;
                } else if (board[i][col] == EMPTY_CELL) {
                    break;
                }
            }
            for (int i = row + 1; i < SIZE; i++) {
                if (board[i][col] == WOUNDED_CELL) {
                    board[i][col] = DESTROYED_CELL;
                    player.setCharOnBoardMemory(i, col, DESTROYED_CELL);
                    opoPlayer.setCharOnBoardAttack(i, col, DESTROYED_CELL);
                    count++;
                } else if (board[i][col] == EMPTY_CELL) {
                    break;
                }
            }
        }

        // Замена одиночного корабля
        if (!isHorizontal && !isVertical) {
            player.setCharOnBoardMemory(row, col, DESTROYED_CELL);
            opoPlayer.setCharOnBoardAttack(row, col, DESTROYED_CELL);
            if (row > 0) {
                player.setCharOnBoardMemory(row - 1, col, PAST_CELL);
                opoPlayer.setCharOnBoardAttack(row - 1, col, PAST_CELL);
            }
            if (row < SIZE - 1) {
                player.setCharOnBoardMemory(row + 1, col, PAST_CELL);
                opoPlayer.setCharOnBoardAttack(row + 1, col, PAST_CELL);
            }
            if (col > 0) {
                player.setCharOnBoardMemory(row, col - 1, PAST_CELL);
                opoPlayer.setCharOnBoardAttack(row, col - 1, PAST_CELL);
            }
            if (col < SIZE - 1) {
                player.setCharOnBoardMemory(row, col + 1, PAST_CELL);
                opoPlayer.setCharOnBoardAttack(row, col + 1, PAST_CELL);
            }
            count++;
        }

        return count;
    }

    /**
     * Говорящий метод, выводим информацию о оставшихся короблях
     */
    private void printCountShips(Map<Integer, Integer> ships) {
        for (Map.Entry<Integer, Integer> entry : ships.entrySet()) {
            System.out.println("Кораблей шириной " + entry.getKey() + ", осталось: " + entry.getValue());
        }
    }
}
