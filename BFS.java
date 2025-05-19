import java.util.*;

public class BFS {
    private Map<Integer, List<Integer>> adjList;

    public BFS(int vertices) {
        adjList = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
        adjList.get(dest).add(src); // Remove this for a directed graph
    }

    public void bfs(int start) {
        boolean[] explored = new boolean[adjList.size()];
        int[] distance = new int[adjList.size()];
        Arrays.fill(distance, Integer.MAX_VALUE);

        Queue<Integer> queue = new LinkedList<>();

        explored[start] = true;
        distance[start] = 0;
        queue.add(start);

        System.out.print("BFS traversal: ");

        while (!queue.isEmpty()) {
            int u = queue.poll();
            System.out.print(u + " ");

            for (int v : adjList.get(u)) {
                if (!explored[v]) {
                    explored[v] = true;
                    distance[v] = distance[u] + 1;
                    queue.add(v);
                }
            }
        }

        System.out.println("\n\nVertex Distances from Source " + start + ":");
        for (int i = 0; i < distance.length; i++) {
            System.out.println("Vertex " + i + " -> Distance: " + (distance[i] == Integer.MAX_VALUE ? "∞" : distance[i]));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int v = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();

        BFS graph = new BFS(v);

        System.out.println("Enter " + e + " edges (format: src dest):");
        for (int i = 0; i < e; i++) {
            int src = sc.nextInt();
            int dest = sc.nextInt();
            graph.addEdge(src, dest);
        }

        System.out.print("Enter starting vertex for BFS: ");
        int start = sc.nextInt();

        graph.bfs(start);

        sc.close();
    }
}

