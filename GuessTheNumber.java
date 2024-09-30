import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int maxAttempts = 5;
        int totalScore = 0; 
        boolean playAgain;

        System.out.println("Welcome to the Number Guessing Game!");

        do {
            int randomNumber = random.nextInt(100) + 1; 
            int attemptsLeft = maxAttempts;
            boolean guessedCorrectly = false;

            System.out.println("\nA new round has started! Guess the number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts to guess the correct number.");

            
            while (attemptsLeft > 0) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attemptsLeft--;

                if (userGuess == randomNumber) {
                    System.out.println("Congratulations! You guessed the correct number.");
                    totalScore += (maxAttempts - attemptsLeft); 
                    guessedCorrectly = true;
                    break;
                } else if (userGuess < randomNumber) {
                    if (randomNumber - userGuess > 20) {
                        System.out.println("Your guess is much too low. Attempts left: " + attemptsLeft);
                    } else {
                        System.out.println("Your guess is slightly low. Attempts left: " + attemptsLeft);
                    }
                } else {
                    if (userGuess - randomNumber > 20) {
                        System.out.println("Your guess is much too high. Attempts left: " + attemptsLeft);
                    } else {
                        System.out.println("Your guess is slightly high. Attempts left: " + attemptsLeft);
                    }
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Sorry, you've used all your attempts. The correct number was: " + randomNumber);
            }

            
            System.out.print("\nDo you want to play another round? (yes/no): ");
            String response = scanner.next();
            playAgain = response.equalsIgnoreCase("yes");

        } while (playAgain);

        
        System.out.println("\nThank you for playing! Your final score is: " + totalScore);
        scanner.close();
    }
}
