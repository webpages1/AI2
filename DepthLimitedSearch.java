import java.util.*;

public class DepthLimitedSearch {
    private Map<Integer, List<Integer>> adjList;

    public DepthLimitedSearch(int vertices) {
        adjList = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }

    // Add an edge to the graph
    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
        adjList.get(dest).add(src); // For undirected graph
    }

    // Depth-Limited Search utility function
    private boolean dlsUtil(int current, int goal, int limit, boolean[] visited) {
        System.out.println("Visiting node: " + current + ", Depth limit: " + limit);

        if (current == goal) {
            System.out.println("Goal node " + goal + " found!");
            return true;
        }

        if (limit <= 0) {
            return false;
        }

        visited[current] = true;

        for (int neighbor : adjList.get(current)) {
            if (!visited[neighbor]) {
                if (dlsUtil(neighbor, goal, limit - 1, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Public method to initiate DLS
    public void depthLimitedSearch(int start, int goal, int limit) {
        boolean[] visited = new boolean[adjList.size()];

        System.out.println("Starting Depth-Limited Search from node " + start + " to find " + goal + " with depth limit " + limit);
        boolean found = dlsUtil(start, goal, limit, visited);

        if (!found) {
            System.out.println("Goal node " + goal + " was NOT found within the depth limit.");
        }
    }

    // Main method with user input
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int v = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();

        DepthLimitedSearch graph = new DepthLimitedSearch(v);

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

        System.out.print("Enter depth limit: ");
        int limit = sc.nextInt();

        graph.depthLimitedSearch(start, goal, limit);

        sc.close();
    }
}

