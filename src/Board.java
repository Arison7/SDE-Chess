import java.util.List;
import java.util.ArrayList;

// This class represents the chessboard and handles its operations.
class Board {
    private static Board instance; // A single instance of the chessboard (Singleton Pattern).
    private final Square[][] squares; // The 8x8 grid of squares on the board.
    private List<GameObserver> observers; // List of observers to notify about changes.

    // Constructor initializes the board with empty squares.
    private Board() {
        squares = new Square[8][8];
        observers = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square();
            }
        }
    }

    /// Get the single instance of the board (Singleton Pattern).
    public static Board getInstance() {
        if (instance == null) {
            instance = new Board(); // Create the instance if it doesn't exist
        }
        return instance;
    }

    // Add an observer to the list
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    // Remove an observer from the list
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers about a change
    private void notifyObservers() {
        for (GameObserver observer : observers) {
            observer.update(this);  // Notify observer about the board update
        }
    }

    // Get the piece on a specific square.
    public Piece getPiece(int x, int y) {
        return squares[x][y].getPiece();
    }

    // Place a piece on a specific square.
    public void setPiece(int x, int y, Piece piece) {
        squares[x][y].setPiece(piece);
    }

    // Move a piece from one square to another.
    public void movePiece(Piece piece, int startX, int startY, int endX, int endY) {
        // Simplified movement logic
        squares[endX][endY].setPiece(piece); // Place the piece on the new square.
        squares[startX][startY].setPiece(null); // Remove the piece from the old square.
        
        notifyObservers(); // Notify observers about the move.
    }

    // Check if the king of a given color is in check.
    public boolean isKingInCheck(String color) {
        int kingX = -1, kingY = -1;
        // Find position of the king of the given color
        // Not the most efficient  way technically board could hold an instance of a king
        // but in general this looks cleaner
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = getPiece(i, j);
                if (piece instanceof King && piece.getColor().equals(color)) {
                    kingX = i;
                    kingY = j;
                    break;
                }
            }
        }
        // Check if there is any piece of opposite color that can move to the square of the attacker
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece attacker = getPiece(i, j);
                if (attacker != null && !attacker.getColor().equals(color)) {
                    if (attacker.canMove(i, j, kingX, kingY, this)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    // If king is in check and cannot avoid it, it's checkmate
    public boolean isCheckmate(String color) {
        return isKingInCheck(color) && canAvoidCheck(color);
    }

    private boolean canAvoidCheck(String color) {
        // Iterate over each square in the board
        for (int startX = 0; startX < 8; startX++) {
            for (int startY = 0; startY < 8; startY++) {
                // Attempt to get a piece from each square
                Piece piece = getPiece(startX, startY);
                // if there is a piece that's same as color as input
                if (piece != null && piece.getColor().equals(color)) {
                    for (int endX = 0; endX < 8; endX++) {
                        for (int endY = 0; endY < 8; endY++) {
                            // This checks if moving any of the pieces anywhere could prevent being checked
                            // If there is at least one outcome that let's us escape we can return false
                            if (piece.canMove(startX, startY, endX, endY, this)) {
                                Piece captured = getPiece(endX, endY);
                                setPiece(endX, endY, piece);
                                setPiece(startX, startY, null);
                                boolean check = isKingInCheck(color);
                                setPiece(startX, startY, piece);
                                setPiece(endX, endY, captured);
                                if (!check) return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    // If King isn't in check but there also aren't any legal moves it's a stalemate
    public boolean isStalemate(String color) {
        return  !isKingInCheck(color) && canAvoidCheck(color);
    }

    // Display the board with correct chess notation
    public void display() {
        System.out.println("  A B C D E F G H");
        for (int i = 0; i < 8; i++) {
            System.out.print(8 - i + " ");
            for (int j = 0; j < 8; j++) {
                Piece piece = squares[i][j].getPiece();
                System.out.print((piece == null ? "." : piece.getSymbol()) + " ");
            }
            System.out.println(8 - i);
        }
        System.out.println("  A B C D E F G H");
    }
}
