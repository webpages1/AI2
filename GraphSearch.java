import java.util.*;

public class GraphSearch {
    private Map<Integer, List<Integer>> adjList = new HashMap<>();
    private Map<String, Integer> costMap = new HashMap<>();

    // Add undirected edge
    public void addEdge(int u, int v, int cost) {
        adjList.putIfAbsent(u, new ArrayList<>());
        adjList.putIfAbsent(v, new ArrayList<>());
        adjList.get(u).add(v);
        adjList.get(v).add(u);

        costMap.put(u + "," + v, cost);
        costMap.put(v + "," + u, cost); // Since undirected
    }

    // Print path from start to goal
    private void printPath(Map<Integer, Integer> parent, int start, int goal) {
        List<Integer> path = new ArrayList<>();
        for (int v = goal; v != -1; v = parent.get(v)) {
            path.add(v);
        }
        Collections.reverse(path);
        for (int node : path) {
            System.out.print(node + " ");
        }
        System.out.println();
    }

    // Breadth-First Search
    public void BFS(int start, int goal) {
        Queue<Integer> queue = new LinkedList<>();
        Map<Integer, Boolean> visited = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();

        queue.add(start);
        visited.put(start, true);
        parent.put(start, -1);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == goal) {
                System.out.print("BFS Path: ");
                printPath(parent, start, goal);
                return;
            }

            for (int neighbor : adjList.getOrDefault(current, new ArrayList<>())) {
                if (!visited.getOrDefault(neighbor, false)) {
                    queue.add(neighbor);
                    visited.put(neighbor, true);
                    parent.put(neighbor, current);
                }
            }
        }

        System.out.println("No path found using BFS");
    }

    // Depth-First Search
    public void DFS(int start, int goal) {
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Boolean> visited = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();

        stack.push(start);
        visited.put(start, true);
        parent.put(start, -1);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (current == goal) {
                System.out.print("DFS Path: ");
                printPath(parent, start, goal);
                return;
            }

            for (int neighbor : adjList.getOrDefault(current, new ArrayList<>())) {
                if (!visited.getOrDefault(neighbor, false)) {
                    stack.push(neighbor);
                    visited.put(neighbor, true);
                    parent.put(neighbor, current);
                }
            }
        }

        System.out.println("No path found using DFS");
    }

    // Uniform Cost Search
    public void UCS(int start, int goal) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        Map<Integer, Integer> dist = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();

        for (int node : adjList.keySet()) {
            dist.put(node, Integer.MAX_VALUE);
        }

        dist.put(start, 0);
        parent.put(start, -1);
        pq.add(new int[]{0, start});

        while (!pq.isEmpty()) {
            int[] pair = pq.poll();
            int cost = pair[0];
            int node = pair[1];

            if (node == goal) {
                System.out.print("UCS Path: ");
                printPath(parent, start, goal);
                System.out.println("Total Cost: " + cost);
                return;
            }

            for (int neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
                int edgeCost = costMap.get(node + "," + neighbor);
                if (dist.get(node) + edgeCost < dist.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    dist.put(neighbor, dist.get(node) + edgeCost);
                    parent.put(neighbor, node);
                    pq.add(new int[]{dist.get(neighbor), neighbor});
                }
            }
        }

        System.out.println("No path found using UCS");
    }

    // Main method
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GraphSearch graph = new GraphSearch();

        System.out.print("Enter number of edges: ");
        int edges = sc.nextInt();

        System.out.println("Enter edges (format: u v cost):");
        for (int i = 0; i < edges; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int c = sc.nextInt();
            graph.addEdge(u, v, c);
        }

        System.out.print("Enter start node: ");
        int start = sc.nextInt();

        System.out.print("Enter goal node: ");
        int goal = sc.nextInt();

        System.out.println("\nPerforming BFS:");
        graph.BFS(start, goal);

        System.out.println("\nPerforming DFS:");
        graph.DFS(start, goal);

        System.out.println("\nPerforming UCS:");
        graph.UCS(start, goal);

        sc.close();
    }
}

