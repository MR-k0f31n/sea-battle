package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author MR.k0F31n
 */

/**
 * Игровая модель игрока
 * Name - игровой ник
 * gameBoard - поле игрока где расположены его корабли
 * componentGameBoard - поле игрока-соперника на нем будет игра
 * ships - нужно для отображения информации сколько каких короблей у вас и у соперника
 * isPlayerMove - чей ход
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
