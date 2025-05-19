import java.util.*;

public class EightPuzzleBestFirstSearch {
    static final int N = 3;
    static final int[] goalState = {1,2,3,4,5,6,7,8,0};

    static class State implements Comparable<State> {
        int[] board;
        State parent;
        String move;
        int heuristic;  // heuristic cost

        State(int[] board) {
            this.board = board.clone();
            this.heuristic = calculateHeuristic();
        }

        boolean isGoal() {
            return Arrays.equals(board, goalState);
        }

        int getZeroIndex() {
            for (int i=0; i<board.length; i++) {
                if (board[i] == 0) return i;
            }
            return -1;
        }

        int calculateHeuristic() {
            // Using number of misplaced tiles heuristic
            int misplaced = 0;
            for (int i=0; i<board.length; i++) {
                if (board[i] != 0 && board[i] != goalState[i]) misplaced++;
            }
            return misplaced;
        }

        List<State> generateSuccessors() {
            List<State> successors = new ArrayList<>();
            int zeroIndex = getZeroIndex();
            int row = zeroIndex / N;
            int col = zeroIndex % N;

            int[][] moves = {{row-1,col},{row+1,col},{row,col-1},{row,col+1}};
            for (int[] move : moves) {
                int newRow = move[0];
                int newCol = move[1];
                if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N) {
                    int newZeroIndex = newRow * N + newCol;
                    int[] newBoard = board.clone();

                    // Swap blank with target tile
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
                System.out.println("Initial State:");
                printBoard();
            }
        }

        void printBoard() {
            for (int i=0; i<board.length; i++) {
                System.out.print((board[i] == 0 ? " " : board[i]) + " ");
                if ((i+1) % N == 0) System.out.println();
            }
            System.out.println();
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.heuristic, other.heuristic);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            return Arrays.equals(board, ((State)o).board);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(board);
        }
    }

    public static void bestFirstSearch(State initialState) {
        PriorityQueue<State> openList = new PriorityQueue<>();
        Set<State> closedList = new HashSet<>();

        openList.add(initialState);

        while (!openList.isEmpty()) {
            State current = openList.poll();

            if (current.isGoal()) {
                System.out.println("Goal reached!");
                current.printPath();
                return;
            }

            closedList.add(current);

            List<State> successors = current.generateSuccessors();
            for (State s : successors) {
                if (!closedList.contains(s) && !openList.contains(s)) {
                    openList.add(s);
                }
            }
        }
        System.out.println("No solution found.");
    }

    public static void main(String[] args) {
        int[] initial = {
            1, 2, 3,
            4, 0, 6,
            7, 5, 8
        };

        State initialState = new State(initial);
        bestFirstSearch(initialState);
    }
}

