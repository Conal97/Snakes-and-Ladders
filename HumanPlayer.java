// Student number: 2191079B

import java.util.Scanner;

public class HumanPlayer extends Player {
    
    // Constructor 
    public HumanPlayer(char playerID) {
        super(playerID);
    }

    // move method with human input
    public boolean move() {

        // Initialise reference to the square we are moving to
        Square nextSquareRef = null;

        // Get position value of the final square
        int finalPos = ((currentBoardRef.getRows() * currentBoardRef.getCols()) - 1);

        int newPos = 0;
            // Loop that ensures new position will not exceed boundary of the board
            do {

                // Determine number of steps to take via human input, only accept integer between 1 & 6
                int steps = 0;
                Scanner scanner = new Scanner(System.in);

                while (steps < 1 || steps > 7) {
                    System.out.println("Please enter an integer number of steps between 1 & 6 to move forward: ");
                    steps = scanner.nextInt();
                }
                
                // Determine position the player moves to 
                newPos = this.currentSquareRef.getPosition() + steps;

                // Error message if input causes board boundary to be exceeded
                if (newPos > finalPos) {
                    System.out.println("Steps input was too large, gone out of bounds!");
                }

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
}
