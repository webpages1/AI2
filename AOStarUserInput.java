import java.util.*;

public class AOStarUserInput {

    static class Node {
        String name;
        boolean isSolved = false;
        boolean isAnd;
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
    static Set<String> solutionPath = new LinkedHashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int n = Integer.parseInt(scanner.nextLine());

        // Step 1: Create nodes
        for (int i = 0; i < n; i++) {
            System.out.println("Node " + (i + 1) + ":");
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Heuristic value: ");
            int h = Integer.parseInt(scanner.nextLine());

            System.out.print("Type (AND/OR): ");
            String type = scanner.nextLine().trim().toUpperCase();
            boolean isAnd = type.equals("AND");

            graph.put(name, new Node(name, h, isAnd));
        }

        // Step 2: Add children
        System.out.println("\nDefine edges for each node (children). Leave empty to skip.");
        for (String nodeName : graph.keySet()) {
            Node node = graph.get(nodeName);
            System.out.println("Node " + nodeName + ":");

            while (true) {
                System.out.print("Enter child group (comma-separated node names), or blank to finish: ");
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) break;

                String[] childNames = line.split(",");
                List<Edge> group = new ArrayList<>();
                for (String childNameRaw : childNames) {
                    String childName = childNameRaw.trim();
                    Node child = graph.get(childName);
                    if (child == null) {
                        System.out.println("Node " + childName + " not found. Skipping group.");
                        group.clear();
                        break;
                    }
                    System.out.print("Cost from " + nodeName + " to " + childName + ": ");
                    int cost = Integer.parseInt(scanner.nextLine());
                    group.add(new Edge(child, cost));
                }
                if (!group.isEmpty()) {
                    node.children.add(group);
                }
            }
        }

        // Step 3: Run AO* from the root
        System.out.print("\nEnter root node name: ");
        String rootName = scanner.nextLine().trim();
        Node root = graph.get(rootName);

        if (root == null) {
            System.out.println("Root node not found.");
            return;
        }

        System.out.println("\nRunning AO* Algorithm from root: " + root.name + "...\n");
        AOStarSearch(root);

        // Step 4: Display result
        System.out.println("\nSolution Path:");
        for (String name : solutionPath) {
            System.out.print(name + " ");
        }
    }

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

        node.heuristic = minCost;

        System.out.println("Expanding node: " + node.name);
        for (Edge e : bestGroup) {
            AOStarSearch(e.child);
        }

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

