import java.util.Random;
import java.util.Scanner;

public class GameLogic {

    
    private final int rows = 3;
    private final int columns = 3; 
    private int[][] game; // 2D Array to represent the game board
    private Scanner scan = new Scanner(System.in);
    private Random rand = new Random();

    public Player playerOne; // player one
    public Player playerTwo; // PLayer two (can be AI or human)
    private String[] thinkingSayings = {
        "AI is thinking...",
        "Hmm, let me calculate...",
        "Analyzing the board...",
        "Just a moment, strategizing...",
        "Thinking... This is important!",
        "Calculating the best move...",
        "Let me see... what should I do?",
        "Processing my next move...",
        "Deciding on my strategy...",
        "Hold on, I'm figuring this out..."
    };

    private String[] winSayings = {
        "Uh-oh! I see a winning move!",
        "I won! That's how it's done!",
        "Victory is mine!",
        "Gotcha! I secured the win!",
        "Winning move detected!",
        "I've outsmarted you!",
        "You underestimated me!",
        "This is how champions play!",
        "I see the path to victory!",
        "I'm unstoppable!"
    };
    
    private String[] blockSayings = {
        "Time to block that!",
        "I won't let you win that easily!",
        "Not so fast! I'm blocking you!",
        "Gotcha! Blocking your move!",
        "I can't let that happen!",
        "Blocking you right here!",
        "I see your strategy, and I'll counter it!",
        "You thought you could win? Think again!",
        "I'll stop you from taking that spot!",
        "This is a critical block!"
    };

    private String[] randomMoveSayings = {
        "This is getting boring...",
        "Just picking a random spot...",
        "Might be a draw!",
        "I'll take this one, why not?",
        "Let's see what happens!",
        "Random choice, here we go!",
        "Feeling lucky with this move!",
        "I guess this will do!",
        "Why not? Let's try this!",
        "Taking a shot in the dark!"
    };

    public GameLogic(){
        game = new int[rows][columns]; // initialize the game board
        initialize(); // start the game initialization process
    }

    private void initialize(){
        typeAnim("Welcome to Tic-Tac-Toe!\n", 40);
        
        // prompt user for game mode
        typeAnim("Playing against AI or Player?: ", 30);
        String choice = scan.nextLine().trim();
       
        // validate user input for game mode selection
        while (!isValidChoice(choice)) {
            typeAnim("Invalid choice. Please enter 'player' or 'ai' to continue: ", 30);
            choice = scan.nextLine().trim();
        }

        // Set game mode based on user choice
        if (choice.equalsIgnoreCase("p") || choice.equalsIgnoreCase("player")) {
            typeAnim("You chose to play against another player.\n", 40);
            for (int i = 1; i <= 2; i++) {
                typeAnim("What's player " + i + "'s' name?: ", 40);
                String playerName = scan.nextLine().trim();
                if (i == 1) {
                    playerOne = new Player(playerName, 1, false); // human player
                } else {
                    playerTwo = new Player(playerName, 2, false); // human player
                }
            }
            startGame(); // start the game 
        } else {
            System.out.println("You chose to play against AI.\n");
            typeAnim("What's your name?: ", 40);
            String playerName = scan.nextLine().trim();

            playerOne = new Player(playerName, 1, false); // human player
            playerTwo = new Player("AI", 2, true); // AI player
            startGame(); // start the game
        }
        printBoard(); // print initial board state
    }

    private void startGame(){
        // Access currentPlayer = random boolean   if true: playerOne starts    if false: playerTwo Starts
        Player currentPlayer = rand.nextBoolean() ? playerOne : playerTwo;
        boolean gameWon = false; // flag to track if the is won
        
        // main game loop
        while (!gameWon && !isBoardFull()) {
            if (currentPlayer.isAI()) {
                typeAnim("AI's Turn: \n", 40);
                aiMove(); // AI makes a move
                printBoard(); // print the updated board
                gameWon = checkWin(currentPlayer.getSymbol()); // check for win
                if (gameWon) {
                    typeAnim(currentPlayer.getName() + " wins!\n", 40);
                }
            } else {
                typeAnim(currentPlayer.getName() + "'s turn. Enter your move (row and column):\n", 40);
                
                int row = -1;
                int col = -1;
                boolean validInput = false; // flag to see if the input is valid
                
                while (!validInput) {
                    typeAnim("Row (1-3): ", 40);
                    row = scan.nextInt() - 1; // Convert to 0-based index
                    typeAnim("Column (1-3): ", 40);
                    col = scan.nextInt() - 1; // Convert to 0-based index
                    
                    // Check if the move is valid
                    if (isValidMove(row, col)) {
                        game[row][col] = currentPlayer.getSymbol(); // Update the board
                        printBoard(); // Print the updated board
                        gameWon = checkWin(currentPlayer.getSymbol()); // Check for win
                        validInput = true; // Mark valid input as true
                    } else {
                        typeAnim("Invalid move. Try again.\n", 40);
                    }
                }

                if (gameWon) {
                    typeAnim(currentPlayer.getName() + " wins!\n", 40); // Announce winner
                    currentPlayer.increaseWins();
                }
            }        

            // switch to the next player only if the game is still going
            if (!gameWon) {
                // if current player is playerOne currentPLayer = playerTwo else currentPlayer = playerOne 
                currentPlayer = (currentPlayer == playerOne) ? playerTwo : playerOne; // Switch players
            }
        }   
        // announce the result of the game
        if (!gameWon) {
            typeAnim("It's a draw!\n", 40); // handle draw scenario
        }
    }

    public void displayScores() {
        typeAnim("Scores:\n", 40);
        typeAnim("Player One: " + playerOne.getWins() + "\n", 40);
        typeAnim("Player Two: " + playerTwo.getWins() + "\n", 40);
    }


    private void aiMove(){
        // simulate AI thinking
        typeAnim(thinkingSayings[rand.nextInt(thinkingSayings.length)] + "\n", 40);
        showThinkingIndicator(rand.nextInt(1000) + 1500);
        
        // check for winning move
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (game[x][y] == 0) { // if the cell is empty
                    game[x][y] = playerTwo.getSymbol(); // try AI's symbol
                    if (checkWin(playerTwo.getSymbol())) {
                        typeAnim(winSayings[rand.nextInt(winSayings.length)], 40);
                        playerTwo.increaseWins(); // update AI's score
                        return; // if this move wins the game, make the move and return
                    }
                    game[x][y] = 0; // reset if the move does not win
                }
            }
        }
        
        /* Check for blocking move */
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                if (game[x][y] == 0) { // check if the cell is empty 
                    game[x][y] = playerOne.getSymbol(); // try the player's symbol (X)
                    if (checkWin(playerOne.getSymbol())) {
                        typeAnim(blockSayings[rand.nextInt(blockSayings.length)] + "\n", 40);
                        game[x][y] = playerTwo.getSymbol(); // block the player's winning move
                        return; // block the move and return
                    }
                    game[x][y] = 0;
                }
            }
        }

        // make a random move if no winning or blocking moves
        int row, col;
        typeAnim(randomMoveSayings[rand.nextInt(randomMoveSayings.length)] + "\n", 40);
        do {
            row = rand.nextInt(rows); // randomly select a row
            col = rand.nextInt(columns); // randomly select a column 
        } while (game[row][col] != 0); // repeat until an empty cell is found
        
        game[row][col] = playerTwo.getSymbol(); // Make the move
    }

    
    private boolean isBoardFull(){
        for (int[] row : game) { // check each row
            for (int cell : row) { // each value 
                if (cell == 0) {
                    return false; // found an empty cell
                }
            }
        }
        return true; // no empty cells found
    }

    private boolean checkWin(int symbol){
        // check rows for a win
        for (int row = 0; row < rows; row++) {
            if (game[row][0] == symbol && game[row][1] == symbol && game[row][2] == symbol) {
                return true; // win found in row
            }
        }

        // check columns for a win
        for (int col = 0; col < columns; col++) {
            if (game[0][col] == symbol && game[1][col] == symbol && game[2][col] == symbol) {
                return true;   
            }
        }
        // check diagonals for a win
        if (game[0][0] == symbol && game[1][1] == symbol && game[2][2] == symbol) {
            return true;
        }
        if (game[0][2] == symbol && game[1][1] == symbol && game[2][0] == symbol) {
            return true;
        }
        return false; // no win found
    }

    private boolean isValidMove(int row, int col){
        // check if the row and column are within bounds and the cell is empty
        return (row >= 0 && row < rows &&
                col >= 0 && col < columns &&
                 game[row][col] == 0);
    }

    public void printBoard(){
        System.out.println();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                char symbol;
                switch (game[y][x]) {
                    case 1:
                        symbol = 'X';
                        break;
                    case 2:
                        symbol = 'O';
                        break;
                    default:
                        symbol = ' ';
                        break;
                }
                System.out.print(" " + symbol + " ");
                if (x < columns - 1) { // while x hasnt passed the limit of columns
                    System.out.print("|"); // vertical separator 
                }
            }
            System.out.println(""); // move to next line after finishing the row
            if (y < rows - 1) {
                System.out.println("-----------"); // horizontal separator
            }
        }
        System.out.println();
    }

    private boolean isValidChoice(String choice){
        return choice.equalsIgnoreCase("player") || choice.equalsIgnoreCase("p") ||
        choice.equalsIgnoreCase("ai") || choice.equalsIgnoreCase("a");
    }

    public void typeAnim(String message, int delay){
        for (char c : message.toCharArray()) {
            System.out.print(c); // print character
            try {
                Thread.sleep(delay); // pause for specified delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    private void showThinkingIndicator(int duration){
        int totalDots = 20;
        typeAnim("AI is thinking: [", 40);
        for (int i = 0; i < totalDots; i++) {
            try {
                Thread.sleep(duration / totalDots); // sleep for a portion of the total duration
                System.out.print("🟦");
                System.out.flush();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        typeAnim("] Done!\n", 40);
    }
}
