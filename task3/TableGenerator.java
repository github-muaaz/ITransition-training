public class TableGenerator {
    public static void generateTable(String[] moves) {
        int len = moves.length;

        // get universal cellWidth
        int cellWidth = Math.max(getLongestMoveLength(moves), 10);

        printHorizontalLine(len + 2, cellWidth);

        // header
        System.out.print("| " + center("v PC\\User >", cellWidth) + " |");
        for (String move : moves)
            System.out.print(" " + center(move, cellWidth) + " |");

        System.out.println();

        printHorizontalLine(len + 2, cellWidth);

        // row
        for (int i = 0; i < len; i++) {
            System.out.print("| " + center(moves[i], cellWidth) + " |");

            for (int j = 0; j < len; j++) {
                String result;

                if (i == j)
                    result = "Draw";
                else if ((j - i + len) % len <= len / 2)
                    result = "Win";
                else
                    result = "Lose";

                System.out.print(" " + center(result, cellWidth) + " |");
            }

            System.out.println();
            printHorizontalLine(len + 2, cellWidth);
        }
    }

    private static String center(String move, int width) {
        return String.format("%-" + width + "s", String.format("%" + (move.length() + (width - move.length()) / 2) + "s", move));
    }

    private static void printHorizontalLine(int width, int cellWidth) {
        System.out.print("+");

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < cellWidth + 2; j++)
                System.out.print("-");

            System.out.print("+");
        }

        System.out.println();
    }

    private static int getLongestMoveLength(String[] moves) {
        int maxLength = 0;

        for (String move : moves)
            maxLength = Math.max(maxLength, move.length());

        return maxLength;
    }
}
