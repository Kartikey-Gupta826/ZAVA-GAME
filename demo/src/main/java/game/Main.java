package game;

import java.util.Random;
import java.util.Scanner;

public class Main {

    // Player Info
    static String myWeapon = "Sword";
    static String myArmor = "Shield";
    static int hp = 100;
    static String gamestateChange = "";
    static int score = 0;

    public static void description() {
        String temp = "";
        double desire_score = 0;
        int attempts = 0;
        int maxAttempts = 6; // try up to 6 times

        while (attempts < maxAttempts) {
            attempts++;

            temp = GeminiChat.narrate(
                    "Write a 40-word RPG game intro." +
                    "A lone warrior fights through cursed lands, defeats villains, earns glory." +
                    "Use short, punchy words. Favour consonant-heavy words like: grim, dark, quest, fight, blade, hunt, cursed, blood, trek, foe, wrath, dusk." + 
                    "Avoid long vowel-heavy words like: illuminate, adventure, experience, opportunity." +
                    "End with a call to action. No bullet points. No questions."
                        )
                    + " ";

            // z → s replacement
            if (temp.contains("z")) {
                int i = temp.length();
                while (i > 0) {
                    if (temp.indexOf('z', i - 1) < temp.indexOf(' ', i - 1) && temp.indexOf('z', i - 1) != -1) {
                        int z_index = temp.indexOf('z', i - 1);
                        temp = temp.substring(0, z_index) + 's' + temp.substring(z_index + 1);
                    }
                    i--;
                }
            }

            temp = temp.replace("!", ":)");

            // ── Scoring ──
            int length = temp.length();
            char letter = temp.charAt(length / 2);
            int index_of_adventure_word = temp.indexOf("adventure");
            int lastIndexof_space = temp.lastIndexOf(" ");
            int firstIndexof_space = temp.indexOf(" ");
            String tem = temp.toUpperCase();

            int count = 0;
            for (int i = 0; i < length; i++) {
                if (tem.charAt(i) == 'A' || tem.charAt(i) == 'E' || tem.charAt(i) == 'I'
                        || tem.charAt(i) == 'O' || tem.charAt(i) == 'U') {
                    count++;
                }
            }

            int desire_score_vowels = 0;
            if (Character.toUpperCase(letter) == 'A' || Character.toUpperCase(letter) == 'E'
                    || Character.toUpperCase(letter) == 'I' || Character.toUpperCase(letter) == 'O'
                    || Character.toUpperCase(letter) == 'U') {
                desire_score_vowels = -1;
            }

            int adventure_word_score = (index_of_adventure_word >= length / 2) ? 1 : -1;

            desire_score = (1 - (count / (double) length)) * 0.5
                    + desire_score_vowels * 0.2
                    + adventure_word_score * 0.3
                    + ((length - lastIndexof_space) + firstIndexof_space) * 0.05;

            // ── Pass? Use it. Fail? Try again ──
            if (desire_score >= 0.25 && !temp.isEmpty()) {
                System.out.println(temp.trim());
                return;
            }

            System.out.println(
                    "[Attempt " + attempts + " score: " + String.format("%.2f", desire_score) + " — retrying...]");
        }

        // All attempts failed — use fallback
        System.out.println("Adventure begins here :)");
    }

    public static int movement(String InGameMovement, int x_Coordinate, int y_Coordinate, String StartingPoint,
            Scanner scanner, int coordinates_vector) {

        System.out.println("**********************************************************");

        // Movement System

        if (x_Coordinate == 0 && y_Coordinate == 0) {
            System.out.println("You are at the " + StartingPoint);
        }

        System.out.println("Current Position: (" + x_Coordinate + ", " + y_Coordinate + ")");
        System.out.println(
                "Where do you want to move? (Type 'up', 'down', 'left', or 'right' first alphabet only)(max x and y are (5,5) and min x and y are (0,0))");
        InGameMovement = scanner.nextLine();

        switch (InGameMovement.toLowerCase()) {

            case "d":
            case "down":
                if (y_Coordinate > 0) {
                    y_Coordinate = y_Coordinate - 1;
                }
                break;
            case "l":
            case "left":
                if (x_Coordinate > 0) {
                    x_Coordinate = x_Coordinate - 1;
                }
                break;
            case "r":
            case "right":
                if (x_Coordinate < 5) {
                    x_Coordinate = x_Coordinate + 1;
                }
                break;
            case "u":
            case "up":
                if (y_Coordinate < 5) {
                    y_Coordinate = y_Coordinate + 1;
                }
                break;
            default:
                System.out.println("Invalid movement, try again.");
        }
        coordinates_vector = x_Coordinate * 10 + y_Coordinate;
        return coordinates_vector;
    }

    public static void villain_action(int x_Coordinate, int y_Coordinate, int counter,
            String PLAYERNAME, Scanner scanner, Random random) {

        System.out.println("**********************************************************");

        // Fresh history, set player name
        GeminiChat.resetVillain(PLAYERNAME);

        int villain_level = random.nextInt(1, 11);
        System.out.println("A level " + villain_level + " villain has appeared. BE PREPARED!!!");

        // Step 1: Villain intro
        String intro = GeminiChat.villainIntro();
        System.out.println(intro);
        System.out.println();

        // Step 2: Player replies
        System.out.print(PLAYERNAME + " : ");
        String reply = scanner.nextLine();

        // Step 3: Villain replies — history handled internally
        System.out.println(GeminiChat.villainReply(reply));
        System.out.println();

        // Step 4: Combat
        System.out.println("Do you want to attack or defend? (Type 'attack' or 'defend')");
        String action = scanner.nextLine().trim();

        int damage;
        String closingPrompt;

        if (action.equalsIgnoreCase("attack")) {
            damage = 10 * villain_level;
            hp -= damage;
            score += 5 * villain_level;
            closingPrompt = "The villain flees after taking a hit but wounds the hero first. 1 sentence.";
        } else {
            damage = 1 * villain_level;
            hp -= damage;
            closingPrompt = "The villain stands firm and wounds the hero badly. 1 sentence.";
        }

        System.out.println("You took " + damage + " damage! HP remaining: " + hp);
        System.out.println(GeminiChat.narrate(closingPrompt)); // narrator closes the scene
        System.out.println();
    }

    public static int lottery_system(int x_Coordinate, int y_Coordinate, int hp_just_practise, Scanner scanner,
            Random random) {
        System.out.println("**********************************************************");

        if (hp_just_practise == 0) {
            hp_just_practise = 20;
            System.out.println("Now your hp has been increased by a little!!");
            return hp_just_practise;
        }
        // Lottery System
        System.out.println(" YOUR HP : " + hp_just_practise);
        System.out.println("Lottery System , Roll The Dice #:o");
        int guess = scanner.nextInt();
        switch (guess) {
            case 1:
                System.out.println(
                        """
                                 -------
                                |       |
                                |   ●   |
                                |       |
                                 -------

                                         """);
                break;
            case 2:
                System.out.println(
                        """
                                 -------
                                |  ●    |
                                |       |
                                |    ●  |
                                 -------

                                         """);
                break;

            case 3:
                System.out.println(
                        """
                                 -------
                                |  ●    |
                                |   ●   |
                                |    ●  |
                                 -------

                                         """);
                break;
            case 4:
                System.out.println(
                        """
                                 -------
                                | ●   ● |
                                |       |
                                | ●   ● |
                                 -------

                                         """);
                break;
            case 5:
                System.out.println(
                        """
                                 -------
                                | ●   ● |
                                |   ●   |
                                | ●   ● |
                                 -------

                                         """);
                break;
            case 6:
                System.out.println(
                        """
                                 -------
                                | ●   ● |
                                | ●   ● |
                                | ●   ● |
                                 -------

                                         """);
                break;
            default:
                break;
        }
        scanner.nextLine();

        int systemNumber = random.nextInt(1, 7);
        double temp1 = Math.abs((double) (guess - systemNumber) / systemNumber);

        int hp_temp = hp_just_practise < 0 ? Math.abs(hp_just_practise) : hp_just_practise;

        if (guess == systemNumber) {
            System.out.println("HP fully restored ;)");
            hp_just_practise = 100;
        }

        else if (temp1 >= 0.8) {
            hp_just_practise = (int) (hp_just_practise + hp_temp * 1);
            System.out.println("HP increased by 100% ;)" + "\nHP:" + hp_just_practise);
        }

        else if (temp1 >= 0.5) {
            hp_just_practise = (int) (hp_just_practise + hp_temp * 0.5);
            System.out.println("HP increased by 50% ;)" + "\nHP:" + hp_just_practise);
        }

        else {
            hp_just_practise = (int) (hp_just_practise + hp_temp * 0.3);
            System.out.println("HP increased by 30% ;)" + "\n");
        }
        return hp_just_practise;
    }

    public static void main(String[] args) {

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        String InGameMovement = "";
        String StartingPoint = "Journey Start Point";

        int x_Coordinate = 0;
        int y_Coordinate = 0;
        int counter = 0;
        boolean gameState = true;
        int coordinates_vector = 0;

        System.out.println("Welcome to the game!");
        System.out.println("**********************************************************");

        description();

        System.out.println("Enter your name:");
        final String PLAYERNAME = scanner.nextLine();

        System.out.printf("Your player name is: %s \n", PLAYERNAME);
        System.out.printf("Your weapon is: %s \n", myWeapon);
        System.out.printf("Your armor is: %s \n", myArmor);

        while (gameState) {

            System.out.printf("\nYour HP is: %d \n", hp);
            System.out.println("Your Score is: " + score);
            System.out.println("Your current coordinates are: (" + x_Coordinate + ", " + y_Coordinate + ")");

            if (counter != 0) {
                if (score >= 60) {
                    System.out.println("Congratulations! You won the game with a score of " + score + "!");
                    break;
                }
                System.out.println("Wanna continue? (Y/N)");
                gamestateChange = scanner.nextLine();

                if (gamestateChange.equalsIgnoreCase("N")) {
                    gameState = false;
                    continue;
                }
            }

            counter++;

            // Movement
            coordinates_vector = movement(InGameMovement, x_Coordinate, y_Coordinate, StartingPoint, scanner,
                    coordinates_vector);
            x_Coordinate = coordinates_vector / 10;
            y_Coordinate = coordinates_vector % 10;

            // Villain spawn
            int x_Coordinate_v = random.nextInt(0, 5);
            int y_Coordinate_v = random.nextInt(0, 5);

            if (counter % 3 == 0) {
                x_Coordinate_v = x_Coordinate;
                y_Coordinate_v = y_Coordinate;
            }

            if (x_Coordinate == x_Coordinate_v && y_Coordinate == y_Coordinate_v) {
                villain_action(x_Coordinate, y_Coordinate, counter, PLAYERNAME, scanner, random);

                // Immediate HP check after combat
                if (hp <= 0) {
                    System.out.println("You lost all your HP, Game Over :(");
                    break;
                }
            }

            // Lottery shrine at (1,1)
            if (x_Coordinate == 1 && y_Coordinate == 1) {
                hp = lottery_system(x_Coordinate, y_Coordinate, hp, scanner, random);
            }
        }

        scanner.close();
    }
}
