import java.util.Arrays;

public class CSP {
    private int N;
    private int[] queens;

    public CSP(int N) {
        this.N = N;
        queens = new int[N];
        // Initialize all rows with -1, meaning no queen is placed yet
        Arrays.fill(queens, -1);
    }

    // Checks if placing a queen at (row, col) is safe considering previous rows
    public boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col || Math.abs(queens[i] - col) == row - i) // queens[i] == col ensures no other queen in previous row share the same column
                return false;
        }
        return true;
    }

    public boolean solve() {
        return solveRow(0);
    }

    // Recursive method that attempts to place queens row by row
    private boolean solveRow(int row) {
        // If row equals N, all queens are placed without conflicts.
        if (row == N)
            return true;
        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                queens[row] = col;
                if (solveRow(row + 1))
                    return true;
                queens[row] = -1;
            }
        }
        return false;
    }

    // Prints the board configuration with queens
    public void printSolution() {
        for (int i = 0; i < N; i++) {
            // For each row, print the columns
            for (int j = 0; j < N; j++) {
                // If queen is placed in column j of row i, print "Q"
                if (queens[i] == j)
                    System.out.print("Q ");
                else
                    System.out.print(". ");
            }
            System.out.println();
        }
    }

    // Main method to run the solver
    public static void main(String[] args) {
        int N = 4;
        CSP solver = new CSP(N);
        if (solver.solve()) {
            System.out.println("Solution found:");
            solver.printSolution();
        } else {
            System.out.println("No solution found.");
        }
    }
}


