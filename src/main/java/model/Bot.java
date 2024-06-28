package model;

/**
 * @author MR.k0F31n
 */

/**
 * Игровая модель БОТА, наследует все поля игрока
 * Имеет собственный "GameModel" через которую будет понимать искать корабль на всем поле или добивать имеющийся.
 */
public class Bot extends Player {
    private GameModel gameModel;
}
