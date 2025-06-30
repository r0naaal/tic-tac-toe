import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) throws Exception {
        GameLogic gameLogic;
        Scanner scan = new Scanner(System.in);
        String choice;
        do {
            gameLogic = new GameLogic();
            gameLogic.displayScores();
            gameLogic.typeAnim("Would you like to play Again?: ", 40);
            choice = scan.nextLine().trim();
        } while (choice.equalsIgnoreCase("yes") ||
                choice.equalsIgnoreCase("y") ||
                choice.equalsIgnoreCase("true") ||
                choice.equalsIgnoreCase("t"));

        gameLogic.typeAnim("Thanks for Playing!\n", 40);
    }
}
