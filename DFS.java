import java.util.*;

public class DFS {
    private Map<Integer, List<Integer>> adjList;

    public DFS(int vertices) {
        adjList = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            adjList.put(i, new ArrayList<>());
        }
    }

    public void addEdge(int src, int dest) {
        adjList.get(src).add(dest);
        adjList.get(dest).add(src); // For undirected graph
    }

    private void dfsUtil(int node, boolean[] visited) {
        visited[node] = true;
        System.out.print(node + " ");

        for (int neighbor : adjList.get(node)) {
            if (!visited[neighbor]) {
                dfsUtil(neighbor, visited);
            }
        }
    }

    public void dfs(int startVertex) {
        boolean[] visited = new boolean[adjList.size()];
        System.out.print("DFS traversal starting from " + startVertex + ": ");
        dfsUtil(startVertex, visited);
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of vertices: ");
        int v = sc.nextInt();

        System.out.print("Enter number of edges: ");
        int e = sc.nextInt();

        DFS graph = new DFS(v);

        System.out.println("Enter " + e + " edges (format: src dest):");
        for (int i = 0; i < e; i++) {
            int src = sc.nextInt();
            int dest = sc.nextInt();
            graph.addEdge(src, dest);
        }

        System.out.print("Enter starting vertex for DFS: ");
        int start = sc.nextInt();

        graph.dfs(start);

        sc.close();
    }
}

