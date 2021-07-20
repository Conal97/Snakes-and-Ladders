// Student number: 2191079B

public class Square {
    
    // Square attributes
    private int position; 
    private Player[] playerRefArray; // reference to players on 'this' square
    private int delta;
    private int numPlayersOnSquare;

    // Constructor 
    public Square(int position) {
        this.position = position;
        this.playerRefArray = new Player[1]; // We can expand this array as necessary
        this.delta = 0;
        this.numPlayersOnSquare = 0; 
    }

    // Method to assign a player to a square
    public void playerToSquare(Player player) {
        
        // If player ref array needs to be bigger
        if (this.numPlayersOnSquare >= this.playerRefArray.length) {

            Player[] playerRefArrayCopy = new Player[this.playerRefArray.length + 1];
            for (int i = 0; i < this.playerRefArray.length; i++) {

                playerRefArrayCopy[i] = this.playerRefArray[i];
            }
            this.playerRefArray = playerRefArrayCopy;
        }

        // Adding a player to the square 
        this.playerRefArray[this.numPlayersOnSquare] = player; // new player always added to the end of the array

        // Provide player a reference to the square
        player.setCurrentSquareRef(this);

        // Updating number of players on square
        this.numPlayersOnSquare++; 
    }

    // Method to remove player from square -> required for when the player moves from the square
    public void popPlayer(Player player) {

        int removalIndex = -1; // initiate with dummy value

        // Loop over playerRefArray and identify index of player we want to remove
        for (int i = 0; i < this.playerRefArray.length; i++ ) {

            if (this.playerRefArray[i].getPlayerID() == player.getPlayerID()) {

                removalIndex = i; 
            }
        }

        // Resizing the array to account for removed player
        Player[] playerRefArrayCopy = new Player[this.playerRefArray.length - 1];
            for (int i = 0, k = 0; i < this.playerRefArray.length; i++) {

                // When removal index is reached continue without copying
                if (i == removalIndex) {
                    continue;
                }

                // Else the player is copied
                playerRefArrayCopy[k++] = this.playerRefArray[i];
            }
            // Reseting reference and updating number of players on square
            this.playerRefArray = playerRefArrayCopy;
            this.numPlayersOnSquare--;
    }

    // toString method
    public String toString() {

        // Adding the playerID's to the string
        String square = "";
        for (int i = 0; i < numPlayersOnSquare; i++) {
            square += playerRefArray[i].toString() + " ";
        }

        // Adding the square position
        String stringPos = String.format("%2d", this.position);
        square += stringPos;

        // Adding the delta
        String deltaBrackets = "";
        if (this.delta != 0) {
            deltaBrackets = String.format("(%3d) ", this.delta);
        }
        else {
            deltaBrackets = "(   ) ";
        }

        square += deltaBrackets;
        return square;        
    }

    // Getters & Setters
    public int getPosition() {
        return this.position;
    }

    public int getDelta() {
        return this.delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public int getNumPlayersOnSquare() {
        return this.numPlayersOnSquare;
    }

    public Player[] playerRefArray() {
        return this.playerRefArray;
    }
}
