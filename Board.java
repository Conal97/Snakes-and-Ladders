// Student number: 2191079B

public class Board {

    public static void main(String[] args) {
        
        // Creation of board + two players
        Board board = new Board(10, 5);
        Player player1 = new Player('A');
        Player player2 = new Player('B');

        // Adding both players to the board
        board.addPlayerToGame(player1);
        board.addPlayerToGame(player2);

        // Printing the board
        System.out.println(board);
    }
    
    // Board attributes
    private int row; // x length of board
    private int column; // y length of board
    private Player[] playersInGame; 
    private int numPlayersInGame;
    protected Square[][] squareArray; 

    // Constructor
    public Board(int row, int column) {
        this.row = row;
        this.column = column;
        this.playersInGame = new Player[2]; // start with two as this is the minimum number of players
        this.numPlayersInGame = 0;
        this.squareArray = new Square [row][column]; // create board of empty square objects

        // Variables for populating the board
        int boardPos = (this.row * this.column) - 1;
        int arrPosAsc = 0;  // necessary for correct square number when wrapping
        int switcher = 0; // 0 when square takes boardPos value, 1 when square takes arrPosAsc value

        // Ensuring that '0' position is always printed at bottom left of the board -> if we have odd number of columns we start with an ascending row
        if (this.row % 2 != 0) {
            arrPosAsc = boardPos - (this.column - 1);
            switcher = 1;
        }

        // Populating the game board with square objects, with wrapping
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {

                // Determining if the row values ascend (1) or descend (0)
                if ((boardPos + 1) % this.column == 0  && boardPos + 1 != (this.row * this.column)) { // Determine if we are at the end of a row + handling for first entry

                    if (switcher == 0) {
                    // We are switching from a 'descending row' (49 -> 45) to an 'ascending row' (40 -> 44)
                    arrPosAsc = boardPos - (this.column - 1);
                    switcher = 1;
                    }
                    else if (switcher == 1) { 
                    // We are swtiching from an 'ascending row' (40 -> 44) to a 'descending row' (39 -> 35)
                    switcher = 0;
                    }    
                }

                // Inputting the square into the board array
                if (switcher == 0) {
                    squareArray[i][j] =  new Square(boardPos);
                }
                else if (switcher == 1) {
                    squareArray[i][j] = new Square(arrPosAsc);
                    arrPosAsc++;
                }
                // boardPos tracks overall position so decrements every iteration
                boardPos--;
            }
        }
    }

    // Method to add a player to the zero position square
    public void addPlayerToGame(Player newPlayer) {
        
        // If numPlayersInGame needs to be bigger
        if (this.numPlayersInGame >= this.playersInGame.length) {

            Player[] playersInGameCopy = new Player[this.playersInGame.length + 1];
            for (int i = 0; i < this.playersInGame.length; i++) {

                playersInGameCopy[i] = playersInGame[i];
            }
            playersInGame = playersInGameCopy;
        }

        // Accessing the zero position square
        int rowPos = 0;
        int colPos = 0;

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {

                // Checking if square is the zero position square
                if (squareArray[i][j].getPosition() == 0) {

                    // Store i & j in variable so we can access as necessary
                    rowPos = i;
                    colPos = j;
                }
            }
        }

        // Use square method for adding player to add to zero position square
        squareArray[rowPos][colPos].playerToSquare(newPlayer);

        // Update playersInGame array 
        playersInGame[this.numPlayersInGame] = newPlayer;

        // Provide player a reference to the board
        newPlayer.setCurrentBoardRef(this);

        // Update number of players in game 
        numPlayersInGame++;
    }

    // takeTurns method
    public boolean takeTurns() {

        // Loop over all players, return true if there is a winner, false otherwise
        for (int i = 0; i < playersInGame.length; i++) {
            if (playersInGame[i].move()) {
                return true;
            }
        }
        return false; 
    }

    // toString method
    public String toString() {

        int maxPlayers = 0;

        // Loop over squareArray to find which square has the most players on it -> need this for string padding
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {

                // Query number of players on square & update maxPlayers if necessary 
                if (squareArray[i][j].getNumPlayersOnSquare() > maxPlayers) {
                    maxPlayers = squareArray[i][j].getNumPlayersOnSquare();
                }
            }
        }

        String board ="";
        String square ="";

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {

                int numPlayersOnSquare = squareArray[i][j].getNumPlayersOnSquare();
                
                // Formatting each square based on maxPlayers
                if (numPlayersOnSquare == maxPlayers) {

                    square = squareArray[i][j].toString();
                    board += square;
                }
                else { // Pad the square so that length matches the square with the most players -> allows board to shrink and grow as necessary
                    
                    String padding = String.format("%" + (maxPlayers - numPlayersOnSquare) * 2 + "s", " ");
                    square = padding + squareArray[i][j].toString();
                    board += square;
                }
            } board += "\n";
        }
        return board;
    }
    
    // Helper method that returns the row & column of a particular position
    public int[] getRowAndCol(int position) {

        int[] array = new int[2];

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {

                // Checking if square positon is equal to entered position
                if (squareArray[i][j].getPosition() == position) {

                    // Store i & j in array so we can access as necessary
                    array[0] = i; // first number in array is the row
                    array[1] = j; // second number in array is the column
                }
            }
        }
        return array; 
    }

    // Helper method to return position based on row and column input
    public int getPosition(int row, int column) {

        int position = squareArray[row][column].getPosition();
        return position;
    }

    // Method that returns a reference to the square object at any integer position
    public Square getSquare(int position) {

        int rowPos = 0;
        int colPos = 0;

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {

                // Checking if square is at the defined position
                if (squareArray[i][j].getPosition() == position) {

                    // Store i & j in variable so we can access as necessary
                    rowPos = i;
                    colPos = j;
                }
            }
        }
        return squareArray[rowPos][colPos];
    }

    // Getters 
    public int getNumPlayersInGame() {
        return this.numPlayersInGame;
    }

    public int getRows() {
        return this.row;
    }

    public int getCols() {
        return this.column;
    }
}
