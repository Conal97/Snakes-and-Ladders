// Student number: 2191079B

import java.util.Random;

public class Player {

    public static void main(String[] args) {
        
        // Creation of a player
        Player player1 = new Player('A');

        // Creation of a square
        Square square1 = new Square(1);

        // Assigning the player to the square
        square1.playerToSquare(player1);

        // toString methods of player & square
        System.out.println("The player toString method outputs: " +  player1);
        System.out.println("The square toString method outputs: " + square1);

        // Square with delta value
        square1.setDelta(4);
        System.out.println("Square with delta: " + square1);
    }
    
    // Player attributes
    private char playerID;
    protected Square currentSquareRef; // Required to access square instances
    protected Board currentBoardRef; // Required to access board instance

    // Constructor
    public Player(char playerID) {
        this.playerID = playerID;
        this.currentSquareRef = null; 
        this.currentBoardRef = null; 
    }

    // toString Method
    public String toString() {
        String stringPlayerID = String.valueOf(this.playerID);
        return stringPlayerID;
    }

    // move Method
    public boolean move() {

        // Initialise reference to the square we are moving to
        Square nextSquareRef = null;

        // Get position value of the final square
        int finalPos = ((currentBoardRef.getRows() * currentBoardRef.getCols()) - 1);
        
        int newPos = 0;
            // Loop that ensures new position will not exceed boundary of the board
            do {

                // Rolling a six-sided die to determine how far to move
                Random r = new Random();
                int count = r.nextInt(6) + 1; 

                // Determine position the player moves to 
                newPos = this.currentSquareRef.getPosition() + count;

            } while (newPos > finalPos);
            
        // Get board row & column of new square reference using helper methods 
        int[] rowColArray = this.currentBoardRef.getRowAndCol(newPos); 
        int row = rowColArray[0];
        int col = rowColArray[1];

        // Set reference to new square
        nextSquareRef = this.currentBoardRef.squareArray[row][col];

        // Loop that runs until we land on a square without a delta
        while (nextSquareRef.getDelta() != 0) {

            // Move player forward or backwards delta amount of steps
            newPos += nextSquareRef.getDelta();

            // Update nextSquare reference
            rowColArray = this.currentBoardRef.getRowAndCol(newPos); 
            row = rowColArray[0];
            col = rowColArray[1];

            nextSquareRef = this.currentBoardRef.squareArray[row][col];
        }
        
        // Remove player from old square
        currentSquareRef.popPlayer(this);

        // Update reference to current square and move player to new square
        currentSquareRef = nextSquareRef;
        currentSquareRef.playerToSquare(this);

        // If current square is the final square return true
        if (currentSquareRef.getPosition() == finalPos) {
            return true;
        }
        return false;
    }

    // Getters & Setters
    public char getPlayerID() {
        return this.playerID;
    }

    public void setCurrentSquareRef(Square squareRef) {
        this.currentSquareRef = squareRef;
    }

    public void setCurrentBoardRef (Board boardRef){
        this.currentBoardRef = boardRef;
    }
}
