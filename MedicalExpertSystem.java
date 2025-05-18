import java.io.*;
import java.util.*;

public class MedicalExpertSystem {

    // Writes the user's symptoms as Prolog facts to facts.pl
    private static void writeFacts(List<String> symptoms) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("facts.pl"))) {
            for (String symptom : symptoms) {
                writer.write("symptom(" + symptom + ").\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing facts.pl: " + e.getMessage());
        }
    }

    // Runs SWI-Prolog and captures the diagnosis
    private static String runProlog() {
        String diagnosis = "No diagnosis found.";
        try {
            // Start Prolog with both files
            ProcessBuilder builder = new ProcessBuilder(
                    "swipl", "-q", "-s", "facts.pl", "-s", "medical_expert.pl",
                    "-g", "diagnosis(D), write(D), nl.", "-t", "halt"
            );

            builder.redirectErrorStream(true); // Include stderr
            Process process = builder.start();

            // Read the output from Prolog
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    diagnosis = line.trim();
                    break;
                }
            }

            reader.close();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error running Prolog: " + e.getMessage());
        }
        return diagnosis;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> symptoms = new ArrayList<>();
        String input;

        System.out.println("=== Medical Diagnosis Expert System ===");
        System.out.println("Enter symptoms from the list below (type 'done' to finish):");
        System.out.println("fever, headache, cough, body_ache, sneezing,");
        System.out.println("sore_throat, chills, sweating, abdominal_pain,");
        System.out.println("fatigue, loss_of_appetite\n");

        while (true) {
            System.out.print("Enter symptom: ");
            input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;
            symptoms.add(input);
        }

        writeFacts(symptoms);
        String diagnosis = runProlog();

        System.out.println("\nDiagnosis: " + diagnosis);
        scanner.close();
    }
}

