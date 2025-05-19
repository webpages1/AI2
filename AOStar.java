import java.util.*;

public class AOStar {
    static class Node {
        String name;
        boolean isSolved = false;
        boolean isAnd = false;
        int heuristic;
        List<List<Edge>> children = new ArrayList<>();

        Node(String name, int heuristic, boolean isAnd) {
            this.name = name;
            this.heuristic = heuristic;
            this.isAnd = isAnd;
        }
    }

    static class Edge {
        Node child;
        int cost;

        Edge(Node child, int cost) {
            this.child = child;
            this.cost = cost;
        }
    }

    static Map<String, Node> graph = new HashMap<>();
    static Set<String> solutionPath = new HashSet<>();

    public static void main(String[] args) {
        // Step 1: Build Graph
        Node A = new Node("A", 999, false);
        Node B = new Node("B", 999, true);   // AND node: D and E
        Node C = new Node("C", 999, false);  // OR node: F or G
        Node D = new Node("D", 2, false);
        Node E = new Node("E", 3, false);
        Node F = new Node("F", 4, false);
        Node G = new Node("G", 6, false);

        // Add children
        B.children.add(Arrays.asList(new Edge(D, 1), new Edge(E, 1))); // AND: D + E
        C.children.add(Arrays.asList(new Edge(F, 1)));                 // OR: F
        C.children.add(Arrays.asList(new Edge(G, 1)));                 // OR: G
        A.children.add(Arrays.asList(new Edge(B, 1)));                 // OR: B
        A.children.add(Arrays.asList(new Edge(C, 1)));                 // OR: C

        // Add to graph
        for (Node node : Arrays.asList(A, B, C, D, E, F, G)) {
            graph.put(node.name, node);
        }

        // Step 2: Run AO* from root
        System.out.println("Starting AO* from node A...\n");
        AOStarSearch(A);

        // Step 3: Print result
        System.out.println("\nSolution Path:");
        for (String node : solutionPath) {
            System.out.print(node + " ");
        }
    }

    // AO* Algorithm
    static void AOStarSearch(Node node) {
        if (node.isSolved || node.children.isEmpty()) {
            node.isSolved = true;
            solutionPath.add(node.name);
            return;
        }

        int minCost = Integer.MAX_VALUE;
        List<Edge> bestGroup = null;

        for (List<Edge> group : node.children) {
            int cost = 0;
            for (Edge e : group) {
                cost += e.cost + e.child.heuristic;
            }
            if (cost < minCost) {
                minCost = cost;
                bestGroup = group;
            }
        }

        // Update node heuristic
        node.heuristic = minCost;

        System.out.println("Expanding node: " + node.name);
        for (Edge e : bestGroup) {
            AOStarSearch(e.child);
        }

        // Mark node as solved if all children in best group are solved
        boolean allSolved = true;
        for (Edge e : bestGroup) {
            if (!e.child.isSolved) {
                allSolved = false;
                break;
            }
        }

        if (allSolved) {
            node.isSolved = true;
            solutionPath.add(node.name);
        }
    }
}

