import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    // Moved playerHealth to be a static field of the Main class
    public static int playerHealth;

    // Non-void method that returns the player's health
    public static int getPlayerHealth() {
        return playerHealth;
    }

    // Array method: Prints all visited locations from an ArrayList
    public static void printVisitedLocations(List<String> visited) {
        System.out.println("\n--- Visited Locations ---");
        if (visited.isEmpty()) {
            System.out.println("No locations have been visited yet.");
        } else {
            for (int i = 0; i < visited.size(); i++) {
                System.out.println((i + 1) + ". " + visited.get(i));
            }
        }
    }

    public static int bossBattle(Scanner in, String name, int pHealth, int pAttack, int pDefense, String bName, int bHealth, int bAttack, int bDefense) {
        System.out.println("\n!!! BOSS BATTLE: " + bName + " Appears !!!");
        Random rand = new Random();
        int potionsAvailable = 2;

        while (pHealth > 0 && bHealth > 0) {
            System.out.println("\n" + name + " HP: " + pHealth);
            System.out.println(bName + " HP: " + bHealth);
            System.out.println("Potions left: " + potionsAvailable);
            System.out.println("Choose your action:");
            System.out.println("1. Attack (Normal Damage)");
            System.out.println("2. Heavy Attack (High Damage, lower defense)");
            System.out.println("3. Use Potion (Heal 20 HP)");
            System.out.print("Enter your choice (1, 2, or 3): ");

            int choice = 0;
            if (in.hasNextInt()) {
                choice = in.nextInt();
                in.nextLine(); // Consume newline
            } else {
                System.out.println("Invalid input. You lose your turn.");
                in.next(); // Consume invalid input
                continue;
            }

            int damageToBoss = 0;
            int defenseMultiplier = 1;

            // Player's turn using a switch statement
            switch (choice) {
                case 1: // Attack
                    damageToBoss = Math.max(0, pAttack - bDefense);
                    bHealth -= damageToBoss;
                    System.out.println("You attack the " + bName + " and deal " + damageToBoss + " damage!");
                    break;
                case 2: // Heavy Attack
                    damageToBoss = Math.max(0, (int)(pAttack * 1.5) - bDefense); // 50% more attack
                    bHealth -= damageToBoss;
                    defenseMultiplier = 0; // Boss attack will hit harder
                    System.out.println("You unleash a heavy blow! Deal " + damageToBoss + " damage. Your guard is down!");
                    break;
                case 3: // Use Potion
                    if (potionsAvailable > 0) {
                        pHealth += 20;
                        potionsAvailable--;
                        System.out.println("You drink a potion and heal 20 HP. " + name + "'s HP is now " + pHealth + ".");
                    } else {
                        System.out.println("You are out of potions!");
                    }
                    break;
                default:
                    System.out.println("Invalid action. You hesitate.");
                    break;
            }

            // Check if boss is defeated
            if (bHealth <= 0) {
                System.out.println("\n" + name + " has defeated the mighty " + bName + "!");
                return pHealth; // Return updated health
            }

            // Boss's turn
            System.out.println("The " + bName + " retaliates...");
            int effectiveDefense = pDefense * defenseMultiplier;
            int damageToPlayer = Math.max(0, bAttack - effectiveDefense);
            pHealth -= damageToPlayer;
            System.out.println("The " + bName + " strikes, dealing " + damageToPlayer + " damage to you.");

            // Check if player is defeated
            if (pHealth <= 0) {
                System.out.println("\n" + name + " has been defeated by the " + bName + ".");
                return pHealth; // Return final health
            }
        }
        return pHealth;
    }
    // Battle method to handle combat encounters with multiple parameters for player and enemy stats
    public static int battle(Scanner in, String name, int playerHealth, int playerAttack, int playerDefense, String enemyName, int enemyHealth, int enemyAttack, int enemyDefense) {
        Random rand = new Random();
        System.out.println("\n--- A Wild " + enemyName + " Appears! ---");

        while (playerHealth > 0 && enemyHealth > 0) {
            System.out.println("\n" + name + " HP: " + playerHealth);
            System.out.println(enemyName + " HP: " + enemyHealth);
            System.out.print("What do you do? (attack/defend): ");
            String action = in.next().toLowerCase();

            // Player's turn
            if (action.equals("attack")) {
                int playerDamage = Math.max(0, playerAttack - enemyDefense); // Damage calculation
                enemyHealth -= playerDamage;
                System.out.println("You attack the " + enemyName + " and deal " + playerDamage + " damage!");
            } else if (action.equals("defend")) {
                System.out.println("You brace for the incoming attack, raising your guard.");
                // Defense will be applied when the enemy attacks
            } else {
                System.out.println("Invalid action. You lose your turn.");
            }

            // Check if enemy is defeated
            if (enemyHealth <= 0) {
                System.out.println("You have defeated the " + enemyName + "!");
                return playerHealth; // Return updated health
            }

            // Enemy's turn
            System.out.println("The " + enemyName + " prepares to strike...");
            int enemyDamage = Math.max(0, enemyAttack - (action.equals("defend") ? playerDefense * 2 : playerDefense));
            playerHealth -= enemyDamage;
            System.out.println("The " + enemyName + " strikes, dealing " + enemyDamage + " damage to you.");

            // Check if player is defeated
            if (playerHealth <= 0) {
                System.out.println("\n" + name + " has been defeated by the " + enemyName + ".");
                return playerHealth; // Return final health
            }
        }
        return playerHealth;
    }
        public static void handleSpellInput(Scanner in, String name) {
    
    }

    // Overloaded input-handling method to provide hints and spell options
    public static void handleSpellInput(Scanner in, String name, boolean provideHint) {
        System.out.println("\nAll ingredients have been gathered! Return to the unicorn.");
        System.out.println("It is time to speak the healing spell. Choose the correct spell from the options below.");

        List<String> spells = new ArrayList<>();
        spells.add("Luminara Vitae"); // Correct spell
        spells.add("Ignis Draconis");
        spells.add("Aqua Regia");
        spells.add("Terra Firma");

        Collections.shuffle(spells); // Shuffle the list to randomize options
        if (provideHint) {
            System.out.println("Hint: The spell is related to light and life.");
        }
        System.out.println("Available Spells:");
        for (int i = 0; i < spells.size(); i++) {
            System.out.println((i + 1) + ". " + spells.get(i));
        }
        System.out.print("Enter the number of the spell you wish to cast: ");
        int spellChoice = in.nextInt();
        in.nextLine(); // Consume newline

        if (spellChoice >= 1 && spellChoice <= spells.size()) {
            String chosenSpell = spells.get(spellChoice - 1);
            if (chosenSpell.equals("Luminara Vitae")) {
                System.out.println("\nYou cast " + chosenSpell + "! The unicorn's wounds begin to heal, and it stands tall once more.");
            } else {
                System.out.println("\nYou cast " + chosenSpell + ", but nothing happens. The unicorn remains injured.");
                System.out.println("Perhaps you should have chosen differently.");
            }
        } else {
            System.out.println("Invalid choice. The unicorn remains injured.");
        }
    }
    // Main method to run the game

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {

            // --- Collect basic character info ---
            System.out.print("Enter your character's name: ");
            String name = in.nextLine();
            System.out.print("Choose a pronoun subject (he/she/they): ");
            String proSubj = in.next().toLowerCase(); // e.g., he / she / they
            System.out.print("Choose a pronoun object (him/her/them): ");
            String proObj = in.next().toLowerCase(); // e.g., him / her / them
            System.out.print("Choose a possessive adjective (his/her/their): ");
            String proPossAdj = in.next().toLowerCase(); // e.g., his / her / their
            System.out.print("Enter your character's age: ");
            int age = in.nextInt();
            // An example integer you can reference later (feel free to rename/use differently)
            int gold = 12;
            // Define player combat stats
            playerHealth = 100; // Initialize the static playerHealth variable
            int playerAttack = 15;
            int playerDefense = 5;

            // Define enemy combat stats
            Random rand = new Random();
            int enemyHealth = 30 + rand.nextInt(30); // Enemy health from 30 to 60
            int enemyAttack = 10;
            int enemyDefense = 3;
            // --- Prologue paragraph (≥ 5 sentences using ≥ 5 variables) ---
            System.out.println();
            System.out.println("~ ~ ~ Adventure Prologue ~ ~ ~");
            System.out.println(name + " set out at dawn, " + proPossAdj + " pack light");
            System.out.println("At only " + age + " years old, " + proSubj + " already carries stories that most would never dare to tell.");
            System.out.println("In the pouch at " + proPossAdj + " side clinked " +
            gold + " gold coins— "
            + "not much, but enough for bread and a bed in a quiet inn.");
            System.out.println("A weathered sign pointed toward the Whispering Woods, and " + proSubj
            + " felt a shiver that had nothing to do with the cold.");
            System.out.println("Whatever waited beyond the treeline would test " +
            proObj + ", but " + name
            + " walked on without looking back.");
            // (You will add TWO more paragraphs below for your submission.)
            // TIP: Use more variables to store place names, items, stats, etc.
            System.out.println();

            // Array of ingredients and locations
            String[] requiredIngredients = {"Moonpetal", "Dwarfstone", "Elvishberry"}; // Ingredients needed to heal the unicorn.

            boolean[] foundIngredients = new boolean[requiredIngredients.length];
            String[] locations = {"Ancient Moongrove", "Dwarven Mines", "Volcanic Ruins", "Keebler Forest"};

            // Corresponding locations for ingredients.
            String[] ingredientLocations = {"Ancient Moongrove", "Dwarven Mines", "Keebler Forest"};

            // Add an ArrayList to store visited locations
            List<String> visitedLocations = new ArrayList<>();

            // Shuffle locations to add randomness
            List<String> locationList = new ArrayList<>(Arrays.asList(locations));
            Collections.shuffle(locationList);
            locations = locationList.toArray(new String[0]);
            
            // --- Game Start ---
            System.out.println("After walking many hours into the heart of the Woods you stumble upon a wounded unicorn.");
            System.out.println("The unicorn, laying badly-injured on the ground, looks up for your help.");
            System.out.println("In order to heal the Unicorn, " + name + " must find three magical ingredients: Moonpetal, Dwarfstone, and Elvishberry.");
            System.out.println("Your quest begins!");

            // Main game loop
            while (true) {
                // Check if all ingredients are found
                boolean allFound = true;
                for (boolean found : foundIngredients) {
                    if (!found) {
                        allFound = false;
                        break;
                    }
                }

                if (allFound) {
                    System.out.println("You have gathered all the ingredients! It's time to return to the unicorn.");
                    handleSpellInput(in, name);
                    break; // End the game
                }

                System.out.println("\n--- Choose Your Next Move ---");
                System.out.println("1. Explore a new location");
                System.out.println("2. Check inventory (ingredients)");
                System.out.println("3. View visited locations");
                System.out.println("4. Check player health");
                System.out.println("5. Exit game");
                System.out.print("Enter your choice: ");

                String choiceStr = in.next();
                int choice;
                try {
                    choice = Integer.parseInt(choiceStr);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    continue;
                }

                switch (choice) {
                    case 1:
                        System.out.println("\n--- Exploration ---");
                        // Choose a new location to explore
                        System.out.println("Which location will you visit?");
                        for (int i = 0; i < locations.length; i++) {
                            System.out.println((i + 1) + ". " + locations[i]);
                        }
                        System.out.print("Enter the number of the location: ");
                        int locationChoice = in.nextInt();
                        in.nextLine(); // Consume newline

                        if (locationChoice >= 1 && locationChoice <= locations.length) {
                            String chosenLocation = locations[locationChoice - 1];
                            visitedLocations.add(chosenLocation);
                            System.out.println("You travel to " + chosenLocation + ".");

                            // Check if there's an ingredient here
                            boolean ingredientFound = false;
                            for (int i = 0; i < ingredientLocations.length; i++) {
                                if (chosenLocation.equals(ingredientLocations[i]) && !foundIngredients[i]) {
                                    System.out.println("You find the " + requiredIngredients[i] + "!");
                                    foundIngredients[i] = true;
                                    ingredientFound = true;
                                    break;
                                }
                            }

                            if (!ingredientFound) {
                                System.out.println("You find nothing of value here.");
                            }

                            // Random chance for a battle
                            if (rand.nextDouble() < 0.5) { // 50% chance of a battle
                                playerHealth = battle(in, name, playerHealth, playerAttack, playerDefense, "Goblin", enemyHealth, enemyAttack, enemyDefense);
                                if (playerHealth <= 0) {
                                    System.out.println("Game Over!");
                                    return; // End the program
                                }
                            }
                        } else {
                            System.out.println("Invalid location choice.");
                        }
                        break;
                    case 2:
                        System.out.println("\n--- Inventory ---");
                        System.out.println("Ingredients Found:");
                        for (int i = 0; i < requiredIngredients.length; i++) {
                            if (foundIngredients[i]) {
                                System.out.println("- " + requiredIngredients[i]);
                            }
                        }
                        break;
                    case 3:
                        printVisitedLocations(visitedLocations);
                        break;
                    case 4:
                        System.out.println("\n" + name + "'s Current Health: " + getPlayerHealth()); // Call the new method
                        break;
                    case 5:
                        System.out.println("Thank you for playing! Goodbye.");
                        return; // Exit the main method
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                        break;
                }
            }
            // If all ingredients are found, provide a hint for the spell
            handleSpellInput(in, name, true);


            // Final Boss Battle
            System.out.println("\nAs you finish healing the unicorn, a dark presence looms nearby.");
            System.out.println("The Evil Sorcerer, the one who harmed the unicorn, emerges to challenge you!");
            bossBattle(in, name, playerHealth, playerAttack, playerDefense, "Evil Sorcerer", 61, 15, 8);

            System.out.println();
        
        }
    }
}
