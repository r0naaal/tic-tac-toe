public class Player {
    private String name;
    private int wins;
    private int symbol; // 1 for X, 2 for O
    private boolean isAI; // flag to indicate if the player is AI

    public Player(String name, int symbol, boolean isAI){
        this.name = name;
        this.symbol = symbol;
        this.isAI = isAI;
        this.wins = 0;
    }

    // get name method
    public String getName(){
        return name;
    }
    
    // get symbol method
    public int getSymbol(){
        return symbol;
    }

    /* Getter for AI status */
    public boolean isAI(){
        return isAI;
    }

    // increase wins method
    public void increaseWins(){
        wins++;
    }

    public int getWins(){
        return wins;
    }
}
