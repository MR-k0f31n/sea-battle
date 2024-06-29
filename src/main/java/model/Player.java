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
    /**
     * Игровая модель игрока
     * Name - игровой ник
     * gameBoard - поле игрока где расположены его корабли
     * componentGameBoard - поле игрока-соперника на нем будет игра
     * ships - нужно для отображения информации сколько каких короблей у вас и у соперника
     * isPlayerMove - чей ход
     */
    private String name;
    private char[][] gameBoard;
    private char[][] componentGameBoard;
    private Map<Integer, Integer> ships;
    private boolean isPlayerMove;

    public void setCharOnBoardAttack(int row, int col, char c) {
        gameBoard[row][col] = c;
    }

    public void setCharOnBoardMemory(int row, int col, char c) {
        componentGameBoard[row][col] = c;
    }

    public void destroyShip(int key) {
        if (ships.containsKey(key)) {
            int value = ships.get(key);

            if (value > 1) {
                // Инкрементируем значение
                ships.put(key, value - 1);
            } else if (value == 1) {
                // Удаляем ключ из карты
                ships.remove(key);
            }
        }
    }
}
