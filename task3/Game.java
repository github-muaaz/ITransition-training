import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Game {
    private final String[] moves;
    private SecretKey key;

    public Game(String[] moves) {
        this.moves = moves;

        try {
            this.key = KeyGenerator.getInstance("HmacSHA256").generateKey();
        } catch (Exception ex) {
            System.err.println("Error generating key: " + ex.getMessage());
        }

    }

    public static void main(String[] args) {
        if (!validateMoves(args)) {
            System.out.println("\nError: Moves must be unique\n");
            return;
        }

        if (args.length < 3 || args.length % 2 == 0) {
            System.out.println("\nError: Invalid arguments, Provide odd number and non-repeating args");
            System.out.println("Example: java Game rock paper scissors\n");
            return;
        }

        Game game = new Game(args);
        Scanner scanner = new Scanner(System.in);

//        getting computer choice
        int computerChoice = (int) (Math.random() * args.length) + 1;

        try {
            System.out.println("HMAC: " + HMACGenerator.generateHMAC(args[computerChoice - 1], game.key));
        } catch (Exception e) {
            System.out.println("Error calculating HMAC: " + e.getMessage());
            return;
        }

        int userChoice = 1;

//        infinite loop if user did not choose 0
        while (userChoice != 0) {
            game.displayMenu();

//            get user input
            userChoice = game.acceptUserInput(scanner);

            if (userChoice == -1)  // if help
                game.provideHelp();
            else if (userChoice == -2)  // if incorrect command
                System.out.println("\nIncorrect command!!!\n");
            else if (userChoice != 0){  // if ok
                game.displayResults(userChoice, computerChoice);
                break;
            }
        }
        scanner.close();
    }

    private static String keyToString(SecretKey key) {
        byte[] keyBytes = key.getEncoded();
        StringBuilder sb = new StringBuilder();

        for (byte b : keyBytes)
            sb.append(String.format("%02x", b));

        return sb.toString();
    }

    private static boolean validateMoves(String[] moves) {
        Set<String> uniqueMoves = new HashSet<>();

        for (String move : moves) {
            if (!uniqueMoves.add(move))
                return false;
        }
        return true;
    }

    public void displayMenu() {
        System.out.println("Available moves:");

        for (int i = 0; i < moves.length; i++)
            System.out.println((i + 1) + " - " + moves[i]);

        System.out.println("0 - exit");
        System.out.println("? - help\n");
    }

    public int acceptUserInput(Scanner scanner) {
        System.out.print("Enter your move: ");

        String input = scanner.nextLine().trim();

//        check if help
        if (input.equals("?"))
            return -1;

        try {
            int choice = Integer.parseInt(input);

//            check if valid command number
            if (choice >= 0 && choice <= moves.length)
                return choice;
        } catch (NumberFormatException ex){
            return -2;
        }

//      return -2 if invalid command number
        return -2;
    }

    public void displayResults(int userChoice, int computerChoice) {
        System.out.println("Your move: " + moves[userChoice - 1]);
        System.out.println("Computer move: " + moves[computerChoice - 1]);

        System.out.println(RuleEngine.determineWinner(userChoice, computerChoice, moves.length));

        System.out.println("HMAC key: " + keyToString(key) + "\n");
    }

    public void provideHelp() {
        TableGenerator.generateTable(moves);
    }
}