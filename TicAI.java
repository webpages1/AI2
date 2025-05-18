import java.util.Scanner;

public class TicAI {
    static final char HUMAN = 'X';
    static final char COMPUTER = 'O';
    static final char EMPTY = ' ';
    static char[][] board = {
        {EMPTY, EMPTY, EMPTY},
        {EMPTY, EMPTY, EMPTY},
        {EMPTY, EMPTY, EMPTY}
    };

    public static void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }

    public static boolean isMovesLeft() {
        for (char[] row : board)
            for (char cell : row)
                if (cell == EMPTY) return true;
        return false;
    }

    public static int evaluate() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                if (board[i][0] == COMPUTER) return +10;
                if (board[i][0] == HUMAN) return -10;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                if (board[0][i] == COMPUTER) return +10;
                if (board[0][i] == HUMAN) return -10;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            if (board[0][0] == COMPUTER) return +10;
            if (board[0][0] == HUMAN) return -10;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            if (board[0][2] == COMPUTER) return +10;
            if (board[0][2] == HUMAN) return -10;
        }
        return 0;
    }

    public static int minimax(int depth, boolean isMax) {
        int score = evaluate();
        if (score == 10 || score == -10)
            return score;
        if (!isMovesLeft())
            return 0;

        if (isMax) {
            int best = -1000;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == EMPTY) {
                        board[i][j] = COMPUTER;
                        best = Math.max(best, minimax(depth + 1, false));
                        board[i][j] = EMPTY;
                    }
            return best;
        } else {
            int best = 1000;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (board[i][j] == EMPTY) {
                        board[i][j] = HUMAN;
                        best = Math.min(best, minimax(depth + 1, true));
                        board[i][j] = EMPTY;
                    }
            return best;
        }
    }

    public static int[] findBestMove() {
        int bestVal = -1000;
        int[] bestMove = {-1, -1};
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == EMPTY) {
                    board[i][j] = COMPUTER;
                    int moveVal = minimax(0, false);
                    board[i][j] = EMPTY;
                    if (moveVal > bestVal) {
                        bestMove[0] = i;
                        bestMove[1] = j;
                        bestVal = moveVal;
                    }
                }
        return bestMove;
    }

    public static boolean isGameOver() {
        return evaluate() != 0 || !isMovesLeft();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");
        printBoard();

        while (!isGameOver()) {
            System.out.print("Your turn! Enter row and column (1-3): ");
            int row = scanner.nextInt() - 1;
            int col = scanner.nextInt() - 1;

            if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != EMPTY) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            board[row][col] = HUMAN;
            printBoard();

            if (isGameOver()) break;

            System.out.println("Computer's turn:");
            int[] bestMove = findBestMove();
            board[bestMove[0]][bestMove[1]] = COMPUTER;
            printBoard();
        }

        int score = evaluate();
        if (score == 10) System.out.println("Computer wins!");
        else if (score == -10) System.out.println("You win!");
        else System.out.println("It's a draw!");
    }
}

