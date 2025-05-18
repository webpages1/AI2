import java.util.*;

public class PredicateLogic {
    Map<String, List<String>> facts = new HashMap<>();
    List<String> rules = new ArrayList<>();

    // Add a fact
    public void addFact(String predicate, String args) {
        facts.computeIfAbsent(predicate, k -> new ArrayList<>()).add(args);
    }

    // Add a rule
    public void addRule(String rule) {
        rules.add(rule);
    }

    // Query directly from facts
    public boolean query(String predicate, String args) {
        if (facts.containsKey(predicate)) {
            for (String val : facts.get(predicate)) {
                if (val.equals(args)) return true;
            }
        }
        return false;
    }

    // Infer using rules
    public boolean infer(String predicate, String args) {
        for (String rule : rules) {
            String[] parts = rule.split(":");
            if (parts.length != 2) continue;

            String head = parts[0].trim();  // Example: bird(X)
            String body = parts[1].trim();  // Example: has_feathers(X), lays_eggs(X)

            if (head.contains(predicate)) {
                String headArg = head.substring(head.indexOf("(") + 1, head.indexOf(")"));

                // Replace variable with actual argument in conditions
                String[] conditions = body.split(",");
                boolean valid = true;

                for (String cond : conditions) {
                    cond = cond.trim();
                    String condPred = cond.substring(0, cond.indexOf("("));
                    String condArg = cond.substring(cond.indexOf("(") + 1, cond.indexOf(")")).replace(headArg, args);

                    if (!query(condPred, condArg)) {
                        valid = false;
                        break;
                    }
                }

                if (valid) return true;
            }
        }
        return false;
    }

    // Check fact (either directly or by inference)
    public boolean checkFact(String predicate, String args) {
        return query(predicate, args) || infer(predicate, args);
    }

    // Main method to test
    public static void main(String[] args) {
        PredicateLogic pl = new PredicateLogic();

        // Adding facts
        pl.addFact("has_feathers", "eagle");
        pl.addFact("has_fur", "tiger");
        pl.addFact("lays_eggs", "eagle");
        pl.addFact("lays_eggs", "snake");
        pl.addFact("has_scales", "snake");

        // Adding rules
        pl.addRule("bird(X): has_feathers(X), lays_eggs(X)");
        pl.addRule("mammal(X): has_fur(X)");
        pl.addRule("reptile(X): lays_eggs(X), has_scales(X)");

        // Queries
        System.out.println("Is Eagle a Bird? " + (pl.checkFact("bird", "eagle") ? "Yes" : "No"));
        System.out.println("Is Tiger a Mammal? " + (pl.checkFact("mammal", "tiger") ? "Yes" : "No"));
        System.out.println("Is Snake a Reptile? " + (pl.checkFact("reptile", "snake") ? "Yes" : "No"));
    }
}

