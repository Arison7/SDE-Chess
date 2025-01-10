
import java.util.*;



class Square {
    private Piece piece;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}

// Main class to run the chess game
public class ChessGame {
    public static void main(String[] args) {
        ChessGameFacade chessGame = new ChessGameFacade();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            chessGame.displayBoard();
            System.out.println(chessGame.getCurrentPlayer() + "'s turn.");
            System.out.print("Enter your move (e.g., A2 to A3): ");
            String move = scanner.nextLine().trim().toUpperCase();

            String[] parts = move.split(" TO ");
            if (parts.length != 2) {
                System.out.println("Invalid input. Use 'start to end' format (e.g., A2 to A3).");
                continue;
            }
            String temp = chessGame.getCurrentPlayer();
            if (chessGame.makeMove(parts[0], parts[1]) ) {
                if(chessGame.getCurrentPlayer().equals(temp)){
                    break; // Game over
                }
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        scanner.close();
    }
}

