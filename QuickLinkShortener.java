import java.io.*;
import java.util.*;

public class QuickLinkShortener {
    private static final String FILE_PATH = "url_mappings.txt";
    private static final String BASE_URL = "http://short.ly/";
    private static Map<String, String> urlMappings = new HashMap<>();

    public static void main(String[] args) {
        loadUrlMappings();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to QuickLink Shortener!");
        System.out.println("Available commands: shorten, retrieve, exit");

        while (true) {
            System.out.print("Enter command: ");
            command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "shorten":
                    shortenUrl(scanner);
                    break;
                case "retrieve":
                    retrieveUrl(scanner);
                    break;
                case "exit":
                    saveUrlMappings();
                    System.out.println("Exiting the URL shortener. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        }
    }

    // Load URL mappings from file
    private static void loadUrlMappings() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                urlMappings.put(parts[0], parts[1]);
            }
        } catch (IOException e) {
            System.out.println("No previous URL mappings found.");
        }
    }

    // Save URL mappings to file
    private static void saveUrlMappings() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Map.Entry<String, String> entry : urlMappings.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving URL mappings to file.");
        }
    }

    // Shorten a long URL
    private static void shortenUrl(Scanner scanner) {
        System.out.print("Enter long URL: ");
        String longUrl = scanner.nextLine();

        if (urlMappings.containsValue(longUrl)) {
            System.out.println("This URL has already been shortened.");
            return;
        }

        String shortUrl = BASE_URL + generateShortCode();
        urlMappings.put(shortUrl, longUrl);
        System.out.println("Shortened URL: " + shortUrl);
    }

    // Retrieve the original URL
    private static void retrieveUrl(Scanner scanner) {
        System.out.print("Enter shortened URL: ");
        String shortUrl = scanner.nextLine();

        if (urlMappings.containsKey(shortUrl)) {
            System.out.println("Original URL: " + urlMappings.get(shortUrl));
        } else {
            System.out.println("Shortened URL not found.");
        }
    }

    // Generate a unique short code for the URL
    private static String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6); // Shortened version of UUID
    }
}
