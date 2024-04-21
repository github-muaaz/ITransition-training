public class RuleEngine {
    public static String determineWinner(int userChoice, int computerChoice, int numOfMoves) {
        if (userChoice == computerChoice)
            return "Draw";

        int half = numOfMoves / 2;
        if ((userChoice - 1 + half) % numOfMoves + 1 == computerChoice)
            return "You win!";

        return "Computer wins!";
    }
}
