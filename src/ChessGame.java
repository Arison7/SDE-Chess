import java.util.Scanner;

// Represents a square on the chessboard.
// Each square can hold one chess piece or be empty.
class Square {
    // The piece on this square, or null if it's empty.
    private Piece piece;

    // Gets the piece on this square.
    public Piece getPiece() {
        return piece;
    }

    // Places a piece on this square.
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}

// Main class to run the chess game
public class ChessGame {
    public static void main(String[] args) {
        ChessGameFacade chessGame = new ChessGameFacade(); // Creates a new game using the facade.
        Scanner scanner = new Scanner(System.in); // Reads user input.

        // Main game loop that keeps running until the game ends.
        while (true) {
            chessGame.displayBoard();
            System.out.println(chessGame.getCurrentPlayer() + "'s turn.");
            System.out.print("Enter your move (e.g., A2 to A3): ");
            String move = scanner.nextLine().trim().toUpperCase();

            // Split the input into the starting and ending positions.
            String[] parts = move.split(" TO ");
            if (parts.length != 2) {
                System.out.println("Invalid input. Use 'start to end' format (e.g., A2 to A3).");
                continue;
            }

            // Store the current player before making a move.
            String temp = chessGame.getCurrentPlayer();
            if (chessGame.makeMove(parts[0], parts[1]) ) {
                if(chessGame.getCurrentPlayer().equals(temp)){
                    break; // Game over
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        // Close the scanner when the game is over.
        scanner.close();
    }
}

