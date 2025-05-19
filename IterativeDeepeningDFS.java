import java.util.*;

public class IterativeDeepeningDFS {
    private Map<Integer, List<Integer>> adjList;

    public IterativeDeepeningDFS(int vertices) {
        adjList = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }

    // Add edge to graph
    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
        adjList.get(dest).add(src); // Remove this for directed graph
    }

    // Depth-Limited DFS utility
    private boolean dls(int current, int goal, int limit, boolean[] visited) {
        System.out.println("Visiting node: " + current + ", Depth remaining: " + limit);
        if (current == goal) {
            System.out.println("Goal node " + goal + " found!");
            return true;
        }

        if (limit <= 0) return false;

        visited[current] = true;

        for (int neighbor : adjList.get(current)) {
            if (!visited[neighbor]) {
                if (dls(neighbor, goal, limit - 1, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Iterative Deepening DFS
    public void iddfs(int start, int goal, int maxDepth) {
        for (int depth = 0; depth <= maxDepth; depth++) {
            System.out.println("\n--- Depth Level: " + depth + " ---");
            boolean[] visited = new boolean[adjList.size()];
            if (dls(start, goal, depth, visited)) {
                System.out.println("Goal found at depth " + depth);
                return;
            }
        }
        System.out.println("Goal node " + goal + " was NOT found up to depth " + maxDepth);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int v = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();

        IterativeDeepeningDFS graph = new IterativeDeepeningDFS(v);

        System.out.println("Enter " + e + " edges (format: src dest):");
        for (int i = 0; i < e; i++) {
            int src = sc.nextInt();
            int dest = sc.nextInt();
            graph.addEdge(src, dest);
        }

        System.out.print("Enter start node: ");
        int start = sc.nextInt();

        System.out.print("Enter goal node: ");
        int goal = sc.nextInt();

        System.out.print("Enter maximum depth limit: ");
        int maxDepth = sc.nextInt();

        graph.iddfs(start, goal, maxDepth);

        sc.close();
    }
}

