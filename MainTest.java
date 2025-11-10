import static org.junit.jupiter.api.Assertions.*; // JUnit 5 imports
import org.junit.jupiter.api.BeforeEach; // JUnit 5 imports
import org.junit.jupiter.api.Test; // JUnit 5 imports
import java.util.ArrayList; // For creating lists
import java.util.Arrays; // For creating lists
import java.util.List; // For creating lists
import java.util.Scanner; // For simulating user input
public class MainTest { // Class to test Main.java functionalities

    @Test // Test player health getter
    public void testGetPlayerHealth() {
        Main.playerHealth = 75;
        assertEquals(75, Main.getPlayerHealth());
    }

    @Test // Empty list
    public void testPrintVisitedLocations_emptyList() {
        List<String> visited = new ArrayList<>();
        Main.printVisitedLocations(visited);
        String expectedOutput = "\n--- Visited Locations ---\nNo locations have been visited yet.\n";
        assertEquals(expectedOutput, getOutput("No locations have been visited yet."));
    }

    @Test // List with some locations
    public void testPrintVisitedLocations_withItems() {
        List<String> visited = Arrays.asList("Forest", "Mountain", "Cave");
        Main.printVisitedLocations(visited);
        String expectedOutput = "\n--- Visited Locations ---\n1. Forest\n2. Mountain\n3. Cave\n";
        assertEquals(expectedOutput, getOutput("Forest", "Mountain", "Cave"));
    }

    @Test // Player wins the battle
    public void testBattle_playerWins() {
        // The test input for the Scanner is provided as a String.
        String input = "attack\ndefend\nattack\n";
        try (Scanner testScanner = new Scanner(input)) {
            int finalHealth = Main.battle(testScanner, "Hero", 50, 10, 5, "Goblin", 20, 5, 2);
            assertTrue(finalHealth > 0);
            String output = getOutput();
            assertTrue(output.contains("You have defeated the Goblin!"));
        }
    }

    @Test // Player defends successfully
    public void testBattle_playerDefends() {
        String input = "defend\nattack\n";
        try (Scanner testScanner = new Scanner(input)) {
            int finalHealth = Main.battle(testScanner, "Hero", 50, 10, 10, "Goblin", 20, 10, 2);
            String output = getOutput();
            assertTrue(output.contains("You brace for the incoming attack"));
            assertTrue(output.contains("The Goblin strikes, dealing 0 damage to you"));
            assertTrue(finalHealth > 0);
        }
    }

    @Test // Player loses the battle
    public void testBattle_playerLoses() {
        String input = "attack\nattack\nattack\n";
        try (Scanner testScanner = new Scanner(input)) {
            int finalHealth = Main.battle(testScanner, "Hero", 10, 5, 5, "Goblin", 50, 20, 5);
            assertTrue(finalHealth <= 0);
            String output = getOutput();
            assertTrue(output.contains("Hero has been defeated by the Goblin."));
        }
    }

    @Test // Correct spell on first try
    public void testHandleSpellInput_correctChoice() {
        String input = "1\n";
        try (Scanner testScanner = new Scanner(input)) {
            Main.handleSpellInput(testScanner, "Tester");
            String output = getOutput();
            assertTrue(output.contains("You have successfully healed the unicorn!"));
        }
    }
}