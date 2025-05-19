import java.util.*;

public class EightPuzzleHillClimbing {
    static final int N = 3; // 3x3 board
    static final int[] goalState = {1,2,3,4,5,6,7,8,0};

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
            for(int i=0; i<board.length; i++) {
                if(board[i] == 0) return i;
            }
            return -1;
        }

        // Heuristic: number of misplaced tiles (excluding blank)
        int heuristic() {
            int misplaced = 0;
            for(int i=0; i<board.length; i++) {
                if(board[i] != 0 && board[i] != goalState[i]) misplaced++;
            }
            return misplaced;
        }

        List<State> generateSuccessors() {
            List<State> successors = new ArrayList<>();
            int zeroIndex = getZeroIndex();
            int row = zeroIndex / N;
            int col = zeroIndex % N;

            int[][] moves = {{row-1,col},{row+1,col},{row,col-1},{row,col+1}};
            for(int[] move : moves) {
                int newRow = move[0];
                int newCol = move[1];
                if(newRow >=0 && newRow < N && newCol >= 0 && newCol < N) {
                    int newZeroIndex = newRow * N + newCol;
                    int[] newBoard = board.clone();

                    // swap blank with target
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
            if(parent != null) {
                parent.printPath();
                System.out.println(move);
                printBoard();
            } else {
                System.out.println("Initial State:");
                printBoard();
            }
        }

        void printBoard() {
            for(int i=0; i<board.length; i++) {
                System.out.print((board[i] == 0 ? " " : board[i]) + " ");
                if((i+1) % N == 0) System.out.println();
            }
            System.out.println();
        }
    }

    public static void hillClimbing(State initialState) {
        State current = initialState;
        current.printBoard();

        while(true) {
            if(current.isGoal()) {
                System.out.println("Goal Reached!");
                current.printPath();
                return;
            }

            List<State> successors = current.generateSuccessors();

            State next = null;
            int currentHeuristic = current.heuristic();

            for(State s : successors) {
                int h = s.heuristic();
                if(h < currentHeuristic) {
                    currentHeuristic = h;
                    next = s;
                }
            }

            if(next == null) {
                // No better neighbor found — local maxima
                System.out.println("Stopped at local maxima.");
                current.printPath();
                return;
            }

            current = next;
        }
    }

    public static void main(String[] args) {
        // Example initial state
        int[] initial = {
            1, 2, 3,
            7, 6, 0,
            4, 5, 8
        };

        State initialState = new State(initial);
        hillClimbing(initialState);
    }
}

