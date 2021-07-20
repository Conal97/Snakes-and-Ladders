// Student number: 2191079B

public class Play {
    public static void main(String[] args) {
        
        // Creation of board + 2 computer players & 1 human player
        Board board = new Board(10, 5);
        Player player1 = new Player('A');
        Player player2 = new Player('B');
        HumanPlayer player3 = new HumanPlayer('C');

        // Adding players to the board
        board.addPlayerToGame(player1);
        board.addPlayerToGame(player2);
        board.addPlayerToGame(player3);

        // Add Snakes to positions 8, 17 & 36 
        addSnakeLadder(8, -4, board);
        addSnakeLadder(17, -2, board);
        addSnakeLadder(36, -5, board);

        // Add ladders to positions 12, 23 & 40
        addSnakeLadder(12, 7, board);
        addSnakeLadder(23, 5, board);
        addSnakeLadder(40, 3, board);
        
        // Printing the starting board
        System.out.println(board);

        // Playing the game until completion
        boolean winner = false;
        while (!winner) {
            winner = board.takeTurns();
            System.out.println(board);
        }
        printWinner(board);
    }

    // Method to add snake or ladder
    public static void addSnakeLadder(int position, int deltaValue, Board boardRef) {

        int[] rowColArray = boardRef.getRowAndCol(position); 
        int row = rowColArray[0];
        int col = rowColArray[1];

        // +ve deltaValue for ladder, -ve deltaValue for snake
        boardRef.squareArray[row][col].setDelta(deltaValue);
    }

    // Method to print the winner
    public static void printWinner (Board board) {

        // Get position value of the final square
        int finalPos = (board.getRows() * board.getCols() -1 );

        // Get final position square
        Square finalSquare = board.getSquare(finalPos);
        Player[] winningPlayer = finalSquare.playerRefArray();
        System.out.println("The winner is " + winningPlayer[0] + "!");
    }
}
