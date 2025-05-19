import java.util.*;

public class AlphaBetaTicTacToe {

    static final int SIZE = 3;
    static char[][] board = {
        { '_', '_', '_' },
        { '_', '_', '_' },
        { '_', '_', '_' }
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Tic Tac Toe with Alpha-Beta Pruning (You = X, AI = O)");

        printBoard();

        while (true) {
            // Player move
            System.out.print("Enter your move (row[0-2] and col[0-2]): ");
            int row = sc.nextInt();
            int col = sc.nextInt();

            if (board[row][col] != '_') {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            board[row][col] = 'X';

            if (isGameOver()) break;

            // AI move
            System.out.println("AI is thinking...");
            Move bestMove = findBestMove();
            board[bestMove.row][bestMove.col] = 'O';

            printBoard();

            if (isGameOver()) break;
        }

        sc.close();
    }

    static class Move {
        int row, col;
        Move(int r, int c) { row = r; col = c; }
    }

    static Move findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = new Move(-1, -1);

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = 'O';
                    int score = minVal(Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = '_';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new Move(i, j);
                    }
                }
            }
        }

        return bestMove;
    }

    static int maxVal(int alpha, int beta) {
        int score = evaluate();
        if (score == 10 || score == -10 || isFull()) return score;

        int value = Integer.MIN_VALUE;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = 'O';
                    value = Math.max(value, minVal(alpha, beta));
                    board[i][j] = '_';

                    if (value >= beta) return value;
                    alpha = Math.max(alpha, value);
                }
            }
        }

        return value;
    }

    static int minVal(int alpha, int beta) {
        int score = evaluate();
        if (score == 10 || score == -10 || isFull()) return score;

        int value = Integer.MAX_VALUE;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == '_') {
                    board[i][j] = 'X';
                    value = Math.min(value, maxVal(alpha, beta));
                    board[i][j] = '_';

                    if (value <= alpha) return value;
                    beta = Math.min(beta, value);
                }
            }
        }

        return value;
    }

    static int evaluate() {
        for (int i = 0; i < SIZE; i++) {
            // Rows
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == 'O') return 10;
                if (board[i][0] == 'X') return -10;
            }

            // Columns
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == 'O') return 10;
                if (board[0][i] == 'X') return -10;
            }
        }

        // Diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == 'O') return 10;
            if (board[0][0] == 'X') return -10;
        }

        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == 'O') return 10;
            if (board[0][2] == 'X') return -10;
        }

        return 0;
    }

    static boolean isFull() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] == '_') return false;
        return true;
    }

    static boolean isGameOver() {
        int score = evaluate();
        if (score == 10) {
            printBoard();
            System.out.println("AI wins!");
            return true;
        } else if (score == -10) {
            printBoard();
            System.out.println("You win!");
            return true;
        } else if (isFull()) {
            printBoard();
            System.out.println("It's a draw!");
            return true;
        }
        return false;
    }

    static void printBoard() {
        System.out.println("\nBoard:");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

