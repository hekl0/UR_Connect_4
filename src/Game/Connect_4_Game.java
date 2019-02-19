package Game;

import Algorithm.Connect_4_AB_Minimax;
import Algorithm.Connect_4_Algorithm;
import Algorithm.Connect_4_H_Minimax;
import Algorithm.Connect_4_Minimax;
import StateSpace.Connect_4_Model;
import StateSpace.State;

import java.util.Scanner;

public class Connect_4_Game implements Game {
    Scanner scanner;
    Connect_4_Model connect_4_model;
    Connect_4_Algorithm<State, Integer> connect_4_algorithm;
    float timeUser;
    float timeAI;

    public Connect_4_Game() {
        scanner = new Scanner(System.in);

        this.gameStart();
    }

    @Override
    public void gameStart() {
        System.out.println("1. Tiny 3x3x3 Connect Three");
        System.out.println("2. Normal 6x7x4 Connect Four");
        System.out.print("Your Choice: ");
        int type = scanner.nextInt();
        if (type == 1)
            connect_4_model = new Connect_4_Model(3, 3, 3);
        else
            connect_4_model = new Connect_4_Model(7, 6, 4);

        System.out.println();
        System.out.println("Choose agent:");
        System.out.println("1. Minimax");
        System.out.println("2. Minimax with alpha beta");
        System.out.println("3. Minimax with cut off and alpha beta");
        System.out.print("Your Choice: ");
        int agent = scanner.nextInt();
        if (agent == 1)
            connect_4_algorithm = new Connect_4_Minimax(connect_4_model);
        else if (agent == 2)
            connect_4_algorithm = new Connect_4_AB_Minimax(connect_4_model);
        else if (agent == 3) {
            System.out.print("Depth limit for H-MINIMAX algorithm (Recommend 8): ");
            int depth = scanner.nextInt();
            connect_4_algorithm = new Connect_4_H_Minimax(connect_4_model, depth);
        }

        System.out.println();
        System.out.print("Do you want to go first?(1 if Yes, 2 if No) ");
        int currentPlayer = scanner.nextInt();
        connect_4_model.setPlayer(currentPlayer);

        System.out.println("You are X, AI is O");
        System.out.println();

        timeUser = 0;
        timeAI = 0;

        this.gameLoop();
    }

    @Override
    public void gameLoop() {
        while (true) {
            printGame();
            if (connect_4_model.Goal(connect_4_model.currentState) || connect_4_model.Draw(connect_4_model.currentState))
                break;

            double tStart = System.currentTimeMillis();
            int move = 0;
            if (connect_4_model.player == 1) {
                System.out.print("Your move (column 0-" + (connect_4_model.currentState.gridWidth-1) + ")? ");
                move = scanner.nextInt();
            } else {
                move = connect_4_algorithm.findBestMove(connect_4_model.currentState);
            }
            double tEnd = System.currentTimeMillis();
            System.out.println("Elapsed Time: " + ((tEnd - tStart) / 1000));

            if (connect_4_model.player == 1)
                System.out.println("User play: " + move);
            else
                System.out.println("AI play: " + move);
            System.out.println();

            connect_4_model.currentState = connect_4_model.Result(connect_4_model.currentState, move);
            if (connect_4_model.player == 1) timeUser += tEnd - tStart;
            else timeAI += tEnd - tStart;
            if (connect_4_model.player == 1) connect_4_model.setPlayer(2);
            else connect_4_model.setPlayer(1);
        }

        this.gameOver();
    }

    @Override
    public void printGame() {
        System.out.print("  ");
        for (int i = 0; i < connect_4_model.currentState.gridWidth; i++)
            System.out.print(i + " ");
        System.out.println();
        for (int j = 0; j < connect_4_model.currentState.gridHeight; j++) {
            System.out.print(j + " ");
            for (int i = 0; i < connect_4_model.currentState.gridWidth; i++)
                if (connect_4_model.currentState.grid[i][connect_4_model.currentState.gridHeight - j - 1] == 0)
                    System.out.print("  ");
                else if (connect_4_model.currentState.grid[i][connect_4_model.currentState.gridHeight - j - 1] == 1)
                    System.out.print("X ");
                else if (connect_4_model.currentState.grid[i][connect_4_model.currentState.gridHeight - j - 1] == 2)
                    System.out.print("O ");
            System.out.println(j);
        }
        System.out.print("  ");
        for (int i = 0; i < connect_4_model.currentState.gridWidth; i++)
            System.out.print(i + " ");
        System.out.println();
        System.out.println("Next to move: " + ((connect_4_model.player == 1) ? "User" : "AI"));
        System.out.println();
    }

    @Override
    public void gameOver() {
        if (connect_4_model.Goal(connect_4_model.currentState))
            System.out.println("Winner: " + ((connect_4_model.player == 1) ? "AI" : "User"));
        else if (connect_4_model.Draw(connect_4_model.currentState))
            System.out.println("Draw");
        System.out.println("Total time:");
        System.out.println("   User: " + (timeUser / 1000) + " secs");
        System.out.println("     AI: " + (timeAI / 1000) + " secs");
    }
}
