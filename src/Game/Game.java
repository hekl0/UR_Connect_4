package Game;

public interface Game {
    /**
     * Handle interact with user at start of the game
     * Ex: ask for size of game
     **/
    void gameStart();

    /**
     * Handle interact with user each turn
     * Ex: ask for move
     **/
    void gameLoop();

    /**
     * Visual the current state of the game
     **/
    void printGame();

    /**Handle to announce user game over
     * Print result of game**/
    void gameOver();
}
