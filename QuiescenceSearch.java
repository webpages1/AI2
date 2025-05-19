import java.util.*;

public class QuiescenceSearch {
    
    static final int MAX_DEPTH = 3;

    static class Position {
        int value;
        boolean isCapture; // Volatile position

        Position(int value, boolean isCapture) {
            this.value = value;
            this.isCapture = isCapture;
        }

        // Simulated children
        List<Position> generateMoves() {
            List<Position> moves = new ArrayList<>();
            moves.add(new Position(value + 1, Math.random() > 0.7));
            moves.add(new Position(value - 1, Math.random() > 0.7));
            return moves;
        }

        boolean isTerminal() {
            return Math.abs(value) > 10;
        }

        @Override
        public String toString() {
            return "Value: " + value + ", Capture: " + isCapture;
        }
    }

    public static void main(String[] args) {
        Position root = new Position(0, false);
        int score = minimax(root, MAX_DEPTH, true);
        System.out.println("Final evaluated score: " + score);
    }

    static int minimax(Position pos, int depth, boolean maximizing) {
        if (depth == 0 || pos.isTerminal()) {
            return quiescence(pos, maximizing, -999, 999);
        }

        if (maximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (Position child : pos.generateMoves()) {
                int eval = minimax(child, depth - 1, false);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Position child : pos.generateMoves()) {
                int eval = minimax(child, depth - 1, true);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    static int quiescence(Position pos, boolean maximizing, int alpha, int beta) {
        int standPat = evaluate(pos);
        if (maximizing) {
            if (standPat >= beta) return beta;
            if (standPat > alpha) alpha = standPat;
        } else {
            if (standPat <= alpha) return alpha;
            if (standPat < beta) beta = standPat;
        }

        if (!pos.isCapture) return standPat;

        for (Position child : pos.generateMoves()) {
            if (!child.isCapture) continue; // Extend only volatile positions
            int score = quiescence(child, !maximizing, alpha, beta);
            if (maximizing) {
                if (score >= beta) return beta;
                if (score > alpha) alpha = score;
            } else {
                if (score <= alpha) return alpha;
                if (score < beta) beta = score;
            }
        }

        return maximizing ? alpha : beta;
    }

    static int evaluate(Position pos) {
        return pos.value; // Simple evaluation function
    }
}

