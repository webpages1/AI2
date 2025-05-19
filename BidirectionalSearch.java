import java.util.*;

public class BidirectionalSearch {
    private Map<Integer, List<Integer>> adjList;
    private int V; // number of vertices

    public BidirectionalSearch(int V) {
        this.V = V;
        adjList = new HashMap<>();
        for (int i = 0; i < V; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }

    // Add edge
    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
        adjList.get(dest).add(src); // For undirected graph
    }

    // Perform bidirectional search
    public boolean bidirectionalSearch(int src, int dest) {
        if (src == dest) {
            System.out.println("Source is the same as destination.");
            return true;
        }

        boolean[] visitedFromSrc = new boolean[V];
        boolean[] visitedFromDest = new boolean[V];

        Queue<Integer> queueSrc = new LinkedList<>();
        Queue<Integer> queueDest = new LinkedList<>();

        queueSrc.add(src);
        visitedFromSrc[src] = true;

        queueDest.add(dest);
        visitedFromDest[dest] = true;

        while (!queueSrc.isEmpty() && !queueDest.isEmpty()) {
            if (bfsStep(queueSrc, visitedFromSrc, visitedFromDest)) {
                System.out.println("Path found!");
                return true;
            }

            if (bfsStep(queueDest, visitedFromDest, visitedFromSrc)) {
                System.out.println("Path found!");
                return true;
            }
        }

        System.out.println("No path found between " + src + " and " + dest);
        return false;
    }

    // Single BFS step
    private boolean bfsStep(Queue<Integer> queue, boolean[] visitedThisSide, boolean[] visitedOtherSide) {
        int current = queue.poll();
        System.out.println("Visiting: " + current);

        for (int neighbor : adjList.get(current)) {
            if (!visitedThisSide[neighbor]) {
                visitedThisSide[neighbor] = true;
                queue.add(neighbor);
                if (visitedOtherSide[neighbor]) {
                    System.out.println("Meeting point found at node: " + neighbor);
                    return true;
                }
            }
        }
        return false;
    }

    // Main method with user input
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int v = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();

        BidirectionalSearch graph = new BidirectionalSearch(v);

        System.out.println("Enter " + e + " edges (format: src dest):");
        for (int i = 0; i < e; i++) {
            int src = sc.nextInt();
            int dest = sc.nextInt();
            graph.addEdge(src, dest);
        }

        System.out.print("Enter source node: ");
        int src = sc.nextInt();

        System.out.print("Enter destination node: ");
        int dest = sc.nextInt();

        graph.bidirectionalSearch(src, dest);

        sc.close();
    }
}

