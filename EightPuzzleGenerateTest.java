import java.util.*;

public class EightPuzzleGenerateTest {
    static final int N = 3;  // Board size
    static final int[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 0}; // 0 is blank

    static class State {
        int[] board;
        State parent;
        String move;
        
        State(int[] board) {
            this.board = board.clone();
        }
        
        boolean isGoal() {
            return Arrays.equals(board, goalState);
        }

        int getZeroIndex() {
            for (int i = 0; i < board.length; i++) {
                if (board[i] == 0) return i;
            }
            return -1;
        }

        List<State> generateSuccessors() {
            List<State> successors = new ArrayList<>();
            int zeroIndex = getZeroIndex();
            int row = zeroIndex / N;
            int col = zeroIndex % N;

            int[][] moves = {{row-1, col}, {row+1, col}, {row, col-1}, {row, col+1}};
            for (int[] move : moves) {
                int newRow = move[0];
                int newCol = move[1];
                if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N) {
                    int newZeroIndex = newRow * N + newCol;
                    int[] newBoard = board.clone();
                    // Swap blank (0) with the target tile
                    newBoard[zeroIndex] = newBoard[newZeroIndex];
                    newBoard[newZeroIndex] = 0;
                    State newState = new State(newBoard);
                    newState.parent = this;
                    newState.move = "Move " + newBoard[zeroIndex] + " to (" + row + "," + col + ")";
                    successors.add(newState);
                }
            }
            return successors;
        }

        void printPath() {
            if (parent != null) {
                parent.printPath();
                System.out.println(move);
                printBoard();
            } else {
                System.out.println("Initial state:");
                printBoard();
            }
        }

        void printBoard() {
            for (int i = 0; i < board.length; i++) {
                System.out.print((board[i] == 0 ? " " : board[i]) + " ");
                if ((i + 1) % N == 0) System.out.println();
            }
            System.out.println();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof State)) return false;
            return Arrays.equals(board, ((State) obj).board);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(board);
        }
    }

    public static void generateAndTest(State initialState) {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();
        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.isGoal()) {
                System.out.println("Goal reached!");
                current.printPath();
                return;
            }

            List<State> successors = current.generateSuccessors();
            for (State next : successors) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }
        System.out.println("No solution found.");
    }

    public static void main(String[] args) {
        // Initial state example (solvable)
        int[] initial = {
            1, 2, 3,
            4, 0, 6,
            7, 5, 8
        };
        
        State initialState = new State(initial);
        generateAndTest(initialState);
    }
}

