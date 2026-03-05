package game;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void Description() {
        String temp = GeminiChat.chat(
                "Provide a good game description in 40 words. The game is about a player who is on a journey to defeat villains and gain points. The player can move around, attack or defend against villains, and participate in a lottery system to restore HP. Try to use vowels less but lines must make sense. just try to use the word with less vowels.");

        if (temp.contains("z")) {
            int i = temp.length();
            while (i > 0) {
                if (temp.indexOf('z', i - 1) < temp.indexOf(' ', i - 1)) {
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
        String tem = temp.toUpperCase();
        int desire_score_vowels = 0;

        int count = 0;
        for (int i = 0; i < length; i++) {
            if (tem.charAt(i) == 'A' || tem.charAt(i) == 'E' || tem.charAt(i) == 'I' || tem.charAt(i) == 'O'
                    || tem.charAt(i) == 'U') {
                count++;
            }
        }
        if (Character.toUpperCase(letter) == 'A' || Character.toUpperCase(letter) == 'E'
                || Character.toUpperCase(letter) == 'I' || Character.toUpperCase(letter) == 'O'
                || Character.toUpperCase(letter) == 'U') {
            desire_score_vowels = -1;
        }

        if (index_of_adventure_word >= length / 2) {
            index_of_adventure_word = -1;
        }

        desire_score = (1 - (count / (double) length)) * 0.5 + desire_score_vowels * 0.2 + index_of_adventure_word * 0.3
                + ((length - lastIndexof_space) / length) * 0.1;

        if (desire_score >= 0.5 && temp.isEmpty() == false) {
            System.out.println(temp.trim());
        } else {
            System.out.println("Welcome to this adventure :)".trim());
        }

        return;
    }

    public static void Movement(String InGameMovement, int x_Coordinate, int y_Coordinate, String StartingPoint,
            Scanner scanner) {
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
    }

    public static void Villain(int x_Coordinate, int y_Coordinate, int counter, String PLAYERNAME, Scanner scanner, int hp, int score, int villain_level, Random random) {

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
            GeminiChat.chat("You are a game villain. Choose a good name. You just encountered a player named "
                    + PLAYERNAME + ". Introduce yourself dramatically in 30 words.");
            System.out.println();
            System.out.print(PLAYERNAME + " : ");
            String reply = scanner.nextLine();

            GeminiChat.chat(
                    "You reply to the player reply in a way that after which is just action left. The reply is : "
                            + reply);
            System.out.println();

            System.out.println("Do you want to attack or defend? (Type 'attack' or 'defend')");
            String action = scanner.nextLine();

            if (action.equalsIgnoreCase("attack")) {
                hp = hp - 10 * villain_level;
                score = score + 5 * villain_level;
                GeminiChat.chat(
                        "Vilain went away but deal some damage. Appropriate dialogue for it as a closing scene in 2 sentences.");
                System.out.println();

            } else if (action.equalsIgnoreCase("defend")) {
                hp = hp - 1 * villain_level;
                GeminiChat.chat(
                        "Villain does not went away and deal some damage. Appropriate dialogue for it as a closing scene in 2 sentences.");
                System.out.println();
            }
        }
        return;
    }

    public static void Lottery_System(int x_Coordinate, int y_Coordinate, int hp, Scanner scanner, Random random, int systemNumber) {
        // Lottery System
        if ((x_Coordinate == 3 && y_Coordinate == 3 || hp <= 0)) {
            System.out.println("YOUR HP IS LESS THAN 0 : " + hp);
            System.out.println("Lottery System , Guess a number in between 1 - 10 #:o");
            int guess = scanner.nextInt();
            scanner.nextLine();

            systemNumber = random.nextInt(1, 11);
            double temp1 = Math.abs((double) (guess - systemNumber) / systemNumber);

            int hp_temp = hp < 0 ? Math.abs(hp) : hp;

            if (guess == systemNumber) {
                System.out.println("HP fully restored ;)");
                hp = 100;
            }

            else if (temp1 >= 0.8) {
                hp = (int) (hp + hp_temp * 1);
                System.out.println("HP increased by 100% ;)" + "\nHP:" + hp);
            }

            else if (temp1 >= 0.5) {
                hp = (int) (hp + hp_temp * 0.5);
                System.out.println("HP increased by 50% ;)" + "\nHP:" + hp);
            }

            else {
                hp = (int) (hp + hp_temp * 0.3);
                System.out.println("HP increased by 30% ;)" + "\nHP:" + hp);
            }

            if (hp == 0) {
                hp = 20;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the game!");
        Description();

        // Player Info
        Scanner scanner = new Scanner(System.in);

        String myWeapon = "Sword";
        String myArmor = "Shield";
        int hp = 100;
        String gamestateChange = "";
        Random random = new Random();
        int score = 0;

        System.out.println("Enter your name:");
        final String PLAYERNAME = scanner.nextLine();

        System.out.printf("Your player name is: %s \n", PLAYERNAME);
        System.out.printf("Your weapon is: %s \n", myWeapon);
        System.out.printf("Your armor is: %s \n", myArmor);

        // Game Instructions and Story
        String InGameMovement = "";

        String StartingPoint = "Journey Start Point";

        int x_Coordinate = 0;
        int y_Coordinate = 0;

        int counter = 0;

        int villain_level = 0;

        int systemNumber = 0;

        boolean gameState = true;

        // Game Logic
        while (gameState) {

            System.out.printf("Your Hp is: %d \n", hp);
            String hpStatus = hp > 50 ? "Healthy 💪" : "Critical ❤️";
            System.out.println("Status: " + hpStatus);
            System.out.println("Your Score is: " + score);

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
            Movement(InGameMovement, x_Coordinate, y_Coordinate, StartingPoint, scanner);

            Villain(x_Coordinate, y_Coordinate, counter, PLAYERNAME, scanner, hp, score, villain_level, random);

            Lottery_System(x_Coordinate, y_Coordinate, hp, scanner, random, systemNumber);
        }
        scanner.close();
    }
}
