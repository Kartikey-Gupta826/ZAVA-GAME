package game;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the game!");
        GeminiChat.chat("Provide a good game description in 2 sentences. The game is about a player who is on a journey to defeat villains and gain points. The player can move around, attack or defend against villains, and participate in a lottery system to restore HP.");

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
        System.out.printf("Your Hp is: %d \n", hp);

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

            // wanna continue the game or leave
            if (counter != 0) {
                if (hp <= 0) {
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

            System.out.println("Your Score is: " + score);

            if (x_Coordinate == 0 && y_Coordinate == 0) {
                System.out.println("You are at the " + StartingPoint);
            }

            // Movement System
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

            // attack or defend
            int x_Coordinate_v = random.nextInt(0, 5);
            int y_Coordinate_v = random.nextInt(0, 5);

            villain_level = random.nextInt(1, 11);

            if (counter % 3 == 0) {
                x_Coordinate_v = x_Coordinate;
                y_Coordinate_v = y_Coordinate;
            }
            if ((x_Coordinate == x_Coordinate_v && y_Coordinate == y_Coordinate_v)) {
                System.out.println("You are " + PLAYERNAME + "\n" + "A level " + villain_level + " villain has appear. BE PREPARED!!!");
                GeminiChat.chat("You are a game villain. Choose a good name. You just encountered a player named " + PLAYERNAME + ". Introduce yourself dramatically in 2 sentences.");
                    System.out.println();
                    System.out.print(PLAYERNAME +" : ");
                    String reply = scanner.nextLine();

                GeminiChat.chat("You reply to the player reply in a way that after which is just action left. The reply is : " + reply );
                System.out.println();

                System.out.println("Do you want to attack or defend? (Type 'attack' or 'defend')");
                String action = scanner.nextLine();

                if (action.equalsIgnoreCase("attack")) {
                    hp = hp - 10 * villain_level;
                    score = score + 5 * villain_level;
                    System.out.println("HP:" + hp);
                    System.out.println("Score:" + score);
                    GeminiChat.chat("Vilain went away but deal some damage. Appropriate dialogue for it as a closing scene in 2 sentences.");
                    System.out.println();
                
                } else if (action.equalsIgnoreCase("defend")) {
                    hp = hp - 1 * villain_level;
                    System.out.println("HP:" + hp);
                    System.out.println("Score:" + score);
                    GeminiChat.chat("Villain does not went away and deal some damage. Appropriate dialogue for it as a closing scene in 2 sentences.");
                    System.out.println();
                }
            }

            // Lottery System
            if ((x_Coordinate == 3 && y_Coordinate == 3 || hp <= 0)) {
                System.out.println("Lottery System , Guess a number in between 1 - 10 #:o");
                int guess = scanner.nextInt();
                scanner.nextLine();

                systemNumber = random.nextInt(1, 11);
                double temp1 = Math.abs((double) (guess - systemNumber) / systemNumber);

                int hp_temp = hp;

                if (hp < 0) {
                    hp_temp = Math.abs(hp);
                }

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
        scanner.close();
    }
}
