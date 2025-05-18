import java.util.*;

public class Predicate {
    private final Map<String, List<String>> facts = new HashMap<>();
    private final List<String> rules = new ArrayList<>();

    public void addFact(String predicate, String args) {
        facts.computeIfAbsent(predicate, k -> new ArrayList<>()).add(args);
    }

    public void addRule(String rule) {
        rules.add(rule);
    }

    public boolean query(String predicate, String args) {
        if (facts.containsKey(predicate)) {
            for (String val : facts.get(predicate)) {
                if (val.equals(args)) return true;
            }
        }
        return false;
    }

    public boolean infer(String predicate, String args) {
        for (String rule : rules) {
            String[] parts = rule.split(":");
            if (parts.length < 2) continue;

            String head = parts[0].trim();
            String body = parts[1].trim();

            if (head.contains(predicate) && head.contains("X")) {
                String[] conditions = body.split(",");
                boolean valid = true;

                for (String condition : conditions) {
                    condition = condition.trim();
                    int start = condition.indexOf("(");
                    int end = condition.indexOf(")");
                    String condPred = condition.substring(0, start);
                    String arg = condition.substring(start + 1, end);
                    if (arg.equals("X")) arg = args;

                    if (!query(condPred, arg)) {
                        valid = false;
                        break;
                    }
                }
                if (valid) return true;
            }
        }
        return false;
    }

    public boolean checkFact(String predicate, String args) {
        return query(predicate, args) || infer(predicate, args);
    }

    public static void main(String[] args) {
        Predicate pl = new Predicate();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter facts in format predicate(argument), type 'done' to stop:");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;
            if (input.contains("(") && input.contains(")")) {
                String pred = input.substring(0, input.indexOf("("));
                String arg = input.substring(input.indexOf("(") + 1, input.indexOf(")"));
                pl.addFact(pred.trim(), arg.trim());
            }
        }

        System.out.println("\nEnter rules in format head(X): cond1(X), cond2(X), type 'done' to stop:");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) break;
            pl.addRule(input.trim());
        }

        System.out.println("\nEnter query in format predicate(argument):");
        String query = scanner.nextLine();
        String pred = query.substring(0, query.indexOf("("));
        String arg = query.substring(query.indexOf("(") + 1, query.indexOf(")"));

        boolean result = pl.checkFact(pred.trim(), arg.trim());
        System.out.println("Result: " + (result ? "True" : "False"));
    }
}

