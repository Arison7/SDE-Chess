public class Main {
    public static void main(String[] args) {
        // Create the board and observer
        Board board = Board.getInstance();
        GameObserver logger = new GameLogger();

        // Add the observer to the board
        board.addObserver(logger);

        // Create and move pieces
        Piece king = new King("white");
        board.setPiece(0, 0, king);  // Place king at the start
        board.movePiece(king, 0, 0, 1, 1);  // Move the king and log the update
    }
}