import java.util.*;

public class EightPuzzleAStarFixed {
    static final int N = 3;
    static final int[] goalState = {1, 2, 3, 4, 5, 6, 7, 8, 0};

    static class State implements Comparable<State> {
        int[] board;
        State parent;
        String move;
        int g, h, f;

        State(int[] board) {
            this.board = board.clone();
            this.g = 0;
            this.h = calculateHeuristic();
            this.f = g + h;
        }

        State(int[] board, int g, State parent, String move) {
            this.board = board.clone();
            this.g = g;
            this.parent = parent;
            this.move = move;
            this.h = calculateHeuristic();
            this.f = g + h;
        }

        boolean isGoal() {
            return Arrays.equals(board, goalState);
        }

        int calculateHeuristic() {
            int dist = 0;
            for (int i = 0; i < board.length; i++) {
                int val = board[i];
                if (val != 0) {
                    int targetRow = (val - 1) / N;
                    int targetCol = (val - 1) % N;
                    int row = i / N;
                    int col = i % N;
                    dist += Math.abs(row - targetRow) + Math.abs(col - targetCol);
                }
            }
            return dist;
        }

        int getZeroIndex() {
            for (int i = 0; i < board.length; i++)
                if (board[i] == 0) return i;
            return -1;
        }

        List<State> generateSuccessors() {
            List<State> successors = new ArrayList<>();
            int zeroIdx = getZeroIndex();
            int row = zeroIdx / N, col = zeroIdx % N;

            int[][] moves = {{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}};
            for (int[] m : moves) {
                int newRow = m[0], newCol = m[1];
                if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N) {
                    int newIdx = newRow * N + newCol;
                    int[] newBoard = board.clone();
                    newBoard[zeroIdx] = newBoard[newIdx];
                    newBoard[newIdx] = 0;
                    String moveDesc = "Move " + newBoard[zeroIdx] + " to position (" + row + "," + col + ")";
                    successors.add(new State(newBoard, this.g + 1, this, moveDesc));
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
            for (int i = 0; i < board.length; i++) {
                System.out.print((board[i] == 0 ? " " : board[i]) + " ");
                if ((i + 1) % N == 0) System.out.println();
            }
            System.out.println();
        }

        @Override
        public int compareTo(State o) {
            return Integer.compare(this.f, o.f);
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            return Arrays.equals(board, ((State) o).board);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(board);
        }
    }

    public static void aStarSearch(State initialState) {
        PriorityQueue<State> open = new PriorityQueue<>();
        Set<State> closed = new HashSet<>();
        open.add(initialState);

        while (!open.isEmpty()) {
            State current = open.poll();
            if (current.isGoal()) {
                System.out.println("Goal reached!");
                current.printPath();
                return;
            }

            closed.add(current);
            for (State neighbor : current.generateSuccessors()) {
                if (closed.contains(neighbor)) continue;

                boolean better = true;
                for (State openNode : open) {
                    if (openNode.equals(neighbor) && openNode.g <= neighbor.g) {
                        better = false;
                        break;
                    }
                }

                if (better) open.add(neighbor);
            }
        }

        System.out.println("No solution found.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] initial = new int[9];

        System.out.println("Enter the puzzle row-by-row (3 space-separated numbers per row, use 0 for blank):");

        int index = 0;
        while (index < 9) {
            try {
                System.out.print("Row " + (index / 3 + 1) + ": ");
                String line = scanner.nextLine().trim();
                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    System.out.println("Please enter exactly 3 space-separated numbers.");
                    continue;
                }
                for (String part : parts) {
                    int num = Integer.parseInt(part);
                    if (num < 0 || num > 8) {
                        System.out.println("Numbers must be between 0 and 8.");
                        index = 0;
                        continue;
                    }
                    initial[index++] = num;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter integers only.");
                index = 0;
            }
        }

        if (!isValidInput(initial)) {
            System.out.println("Invalid puzzle: must include all numbers from 0 to 8 without duplicates.");
        } else {
            State initialState = new State(initial);
            aStarSearch(initialState);
        }
        scanner.close();
    }

    public static boolean isValidInput(int[] board) {
        boolean[] seen = new boolean[9];
        for (int val : board) {
            if (seen[val]) return false;
            seen[val] = true;
        }
        return true;
    }
}

