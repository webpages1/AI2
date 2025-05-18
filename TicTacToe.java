import java.util.Scanner;

public class TicTacToe {
    static char[][] board = {
        {'1', '2', '3'},
        {'4', '5', '6'},
        {'7', '8', '9'}
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

    public static boolean checkWin(char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }
        // Check diagonals
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    public static boolean isDraw() {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell != 'X' && cell != 'O') return false;
            }
        }
        return true;
    }

    public static void playNonAI() {
        Scanner scanner = new Scanner(System.in);
        char player = 'X';
        int move;

        while (true) {
            printBoard();
            System.out.print("Player " + player + ", enter your move (1-9): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Consume invalid input
                continue;
            }

            move = scanner.nextInt();

            if (move < 1 || move > 9) {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            int row = (move - 1) / 3;
            int col = (move - 1) % 3;

            if (board[row][col] == 'X' || board[row][col] == 'O') {
                System.out.println("Cell already taken! Try again.");
                continue;
            }

            board[row][col] = player;

            if (checkWin(player)) {
                printBoard();
                System.out.println("Player " + player + " wins!");
                break;
            }

            if (isDraw()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }

            player = (player == 'X') ? 'O' : 'X';
        }

        scanner.close();
    }

    public static void main(String[] args) {
        System.out.println("Two Player Tic Tac Toe!");
        playNonAI();
    }
}

