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
        String temp = GeminiChat.chat(
                "Provide a good game description in 40 words. The game is about a player who is on a journey to defeat villains and gain points. The player can move around, attack or defend against villains, and participate in a lottery system to restore HP. Try to use vowels less but lines must make sense. just try to use the word with less vowels.")
                + " ";

        if (temp.contains("z")) {
            int i = temp.length();
            while (i > 0) {
                if (temp.indexOf('z', i - 1) < temp.indexOf(' ', i - 1) && temp.indexOf('z', i - 1) != -1) {
                    int z_index = temp.indexOf('z', i - 1);
                    String before_z = temp.substring(0, z_index);
                    String after_z = temp.substring(z_index + 1);
                    temp = before_z + 's' + after_z;
                }
                i--;
            }
        }

        temp = temp.replace("!", ":)");

        double desire_score = 0;
        int length = temp.length();
        char letter = temp.charAt(length / 2);
        int index_of_adventure_word = temp.indexOf("adventure");
        int lastIndexof_space = temp.lastIndexOf(" ");
        int firstIndexof_space = temp.indexOf(" ");
        String tem = temp.toUpperCase();

        int count = 0;
        for (int i = 0; i < length; i++) {
            if (tem.charAt(i) == 'A' || tem.charAt(i) == 'E' || tem.charAt(i) == 'I' || tem.charAt(i) == 'O'
                    || tem.charAt(i) == 'U') {
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

        desire_score = (1 - (count / (double) length)) * 0.5 + desire_score_vowels * 0.2 + adventure_word_score * 0.3
                + ((length - lastIndexof_space) + (firstIndexof_space)) * 0.05;

        if (desire_score >= 0.5 && temp.isEmpty() == false) {
            System.out.println(temp.trim());
        } else {
            System.out.println("Adventure begins here :)".trim());
        }

        return;
    }

    public static int movement(String InGameMovement, int x_Coordinate, int y_Coordinate, String StartingPoint,
            Scanner scanner, int coordinates_vector) {

        System.out.println("----------------------------------------");

        // Movement System

        if (x_Coordinate == 0 && y_Coordinate == 0) {
            System.out.println("You are at the " + StartingPoint);
        }

        System.out.println("Current Position: (" + x_Coordinate + ", " + y_Coordinate + ")");
        System.out.println(
                "Where do you want to move? (Type 'up', 'down', 'left', or 'right')(max x and y are (5,5) and min x and y are (0,0))");
        InGameMovement = scanner.nextLine();

        switch (InGameMovement.toLowerCase()) {

            case "down":
                if (y_Coordinate > 0) {
                    y_Coordinate = y_Coordinate - 1;
                }
                break;
            case "left":
                if (x_Coordinate > 0) {
                    x_Coordinate = x_Coordinate - 1;
                }
                break;
            case "right":
                if (x_Coordinate < 5) {
                    x_Coordinate = x_Coordinate + 1;
                }
                break;
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

    public static void villain(int x_Coordinate, int y_Coordinate, int counter, String PLAYERNAME, Scanner scanner,
            int villain_level, Random random) {

        System.out.println("----------------------------------------");

        // attack or defend
        int x_Coordinate_v = random.nextInt(0, 5);
        int y_Coordinate_v = random.nextInt(0, 5);

        villain_level = random.nextInt(1, 11);

        if (counter % 3 == 0) {
            x_Coordinate_v = x_Coordinate;
            y_Coordinate_v = y_Coordinate;
        }
        if ((x_Coordinate == x_Coordinate_v && y_Coordinate == y_Coordinate_v)) {
            System.out.println("You are " + PLAYERNAME + "\n" + "A level " + villain_level
                    + " villain has appear. BE PREPARED!!!");

            System.out.println(
                    GeminiChat.chat("You are a game villain. Choose a good name. You just encountered a player named "
                            + PLAYERNAME + ". Introduce yourself dramatically in 30 words."));
            System.out.println();
            System.out.print(PLAYERNAME + " : ");
            String reply = scanner.nextLine();

            System.out.println(GeminiChat.chat(
                    "You reply to the player reply in a way that after which is just action left in 2 sentences. The reply is : "
                            + reply));
            System.out.println();

            System.out.println("Do you want to attack or defend? (Type 'attack' or 'defend')");
            String action = scanner.nextLine().trim();

            if (action.equalsIgnoreCase("attack")) {
                hp = hp - 10 * villain_level;
                score = score + 5 * villain_level;
                System.out.println(GeminiChat.chat(
                        "Vilain went away but deal some damage. Appropriate dialogue for it as a closing scene in 2 sentences."));
                System.out.println();

            } else if (action.equalsIgnoreCase("defend")) {
                hp = hp - 1 * villain_level;
                System.out.println(GeminiChat.chat(
                        "Villain does not went away and deal some damage. Appropriate dialogue for it as a closing scene in 2 sentences."));
                System.out.println();
            }
        }
        return;
    }

    public static int lottery_system(int x_Coordinate, int y_Coordinate, int hp_just_practise, Scanner scanner,
            Random random) {
        System.out.println("----------------------------------------");

        // Lottery System
        if ((x_Coordinate == 3 && y_Coordinate == 3) || (hp_just_practise < 0)) {
            System.out.println(" YOUR HP IS LESS THAN 0 : " + hp_just_practise);
            System.out.println("Lottery System , Guess a number in between 1 - 10 #:o");
            int guess = scanner.nextInt();
            scanner.nextLine();

            int systemNumber = random.nextInt(1, 11);
            double temp1 = Math.abs((double) (guess - systemNumber) / systemNumber);

            if (hp_just_practise == 0) {
                hp_just_practise = 20;
                return hp_just_practise;
            }
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
                System.out.println("HP increased by 30% ;)" + "\nHP:" + hp_just_practise);
            }
        }
        return hp_just_practise;
    }

    public static void main(String[] args) {

        // Game Enviroment Variables
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        String InGameMovement = "";

        String StartingPoint = "Journey Start Point";

        int x_Coordinate = 0;
        int y_Coordinate = 0;

        int counter = 0;

        int villain_level = 0;

        boolean gameState = true;

        int coordinates_vector = 0;

        System.out.println("Welcome to the game!");

        System.out.println("@- - - - - - - - - - - - - - - - - - - -@");

        description();

        System.out.println("Enter your name:");
        final String PLAYERNAME = scanner.nextLine();

        System.out.printf("Your player name is: %s \n", PLAYERNAME);
        System.out.printf("Your weapon is: %s \n", myWeapon);
        System.out.printf("Your armor is: %s \n", myArmor);

        // Game Logic
        while (gameState) {

            System.out.printf("Your Hp is: %d \n", hp);
            System.out.println("Your Score is: " + score);
            System.out.println("Your current coordinates are: (" + x_Coordinate + ", " + y_Coordinate + ")");

            // wanna continue the game or leave
            if (counter != 0) {
                if (hp < 0) {
                    System.out.println("You lost all your HP, Game Over :(");
                    break;
                }
                if (score >= 60) {
                    System.out.println("Congratulations! You won the game with a score of " + score + "!");
                    break;
                }
                System.out.println("Wanna continue the loop :) (type Y/N) ");
                gamestateChange = scanner.nextLine();

                if (gamestateChange.equals("Y") || gamestateChange.equals("y")) {
                    gameState = true;
                } else if (gamestateChange.equalsIgnoreCase("N")) {
                    gameState = false;
                    continue;
                }
            }

            counter++;

            coordinates_vector = movement(InGameMovement, x_Coordinate, y_Coordinate, StartingPoint, scanner,
                    coordinates_vector);
            x_Coordinate = coordinates_vector / 10;
            y_Coordinate = coordinates_vector % 10;

            villain(x_Coordinate, y_Coordinate, counter, PLAYERNAME, scanner, villain_level, random);

            hp = lottery_system(x_Coordinate, y_Coordinate, hp, scanner, random);
        }
        scanner.close();
    }
}
