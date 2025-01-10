import java.util.*;

class ChessGameFacade {
    private Board board;
    private String currentPlayer;

    public ChessGameFacade() {
        this.board = Board.getInstance();
        this.currentPlayer = "white";
        setupBoard();
    }

    private void setupBoard() {
        for (int i = 0; i < 8; i++) {
            board.setPiece(1, i, PieceFactory.createPiece("pawn", "black"));
            board.setPiece(6, i, PieceFactory.createPiece("pawn", "white"));
        }
        board.setPiece(0, 7, PieceFactory.createPiece("rook", "black"));
        board.setPiece(0, 0, PieceFactory.createPiece("rook", "black"));
        board.setPiece(7, 7, PieceFactory.createPiece("rook", "white"));
        board.setPiece(7, 0, PieceFactory.createPiece("rook", "white"));
        board.setPiece(0, 4, PieceFactory.createPiece("king", "black"));
        board.setPiece(7, 4, PieceFactory.createPiece("king", "white"));
        board.setPiece(0, 2, PieceFactory.createPiece("bishop", "black"));
        board.setPiece(0, 5, PieceFactory.createPiece("bishop", "black"));
        board.setPiece(7, 5, PieceFactory.createPiece("bishop", "white"));
        board.setPiece(7, 2, PieceFactory.createPiece("bishop", "white"));
        board.setPiece(0, 3, PieceFactory.createPiece("queen", "black"));
        board.setPiece(7, 3, PieceFactory.createPiece("queen", "white"));
        board.setPiece(0, 1, PieceFactory.createPiece("knight", "black"));
        board.setPiece(0, 6, PieceFactory.createPiece("knight", "black"));
        board.setPiece(7, 6, PieceFactory.createPiece("knight", "white"));
        board.setPiece(7, 1, PieceFactory.createPiece("knight", "white"));
    }

    public boolean makeMove(String start, String end) {
        int[] startCoords = parseCoordinates(start);
        int[] endCoords = parseCoordinates(end);

        // check if the coordinate is on the board
        if(!(startCoords[0] >= 0 & startCoords[0] < 8 & startCoords[1] >= 0 & startCoords[1] < 8) |
                !(endCoords[0] >= 0 & endCoords[0] < 8 & endCoords[1] >= 0 & endCoords[1] < 8)
        ){
            return false;
        }

        Piece piece = board.getPiece(startCoords[0], startCoords[1]);
        if (piece == null || !piece.getColor().equals(currentPlayer)) {
            return false; // Invalid move
        }

        if (!piece.canMove(startCoords[0], startCoords[1], endCoords[0], endCoords[1], board)) {
            return false; // Invalid move
        }

        Piece captured = board.getPiece(endCoords[0], endCoords[1]);
        board.setPiece(endCoords[0], endCoords[1], piece);
        board.setPiece(startCoords[0], startCoords[1], null);

        if (board.isKingInCheck(currentPlayer)) {
            // Undo the move
            board.setPiece(startCoords[0], startCoords[1], piece);
            board.setPiece(endCoords[0], endCoords[1], captured);
            System.out.println("Your king is in check");
            return false;
        }

        if (piece instanceof Pawn) {
            handlePawnPromotion(endCoords[0], endCoords[1], piece);
        }

        // Check for game-ending conditions
        if (board.isCheckmate(currentPlayer.equals("white") ? "black" : "white")) {
            System.out.println("Checkmate! " + currentPlayer + " wins!");
            return true;
        } else if (board.isStalemate(currentPlayer.equals("white") ? "black" : "white")) {
            System.out.println("Stalemate! It's a draw.");
            return true;
        }

        switchPlayer();
        return true;
    }

    private void handlePawnPromotion(int x, int y, Piece pawn) {
        if ((currentPlayer.equals("white") && x == 0) || (currentPlayer.equals("black") && x == 7)) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Pawn promotion! Enter new piece (queen/rook/knight/bishop): ");
            String newPiece = scanner.next();
            board.setPiece(x, y, PieceFactory.createPiece(newPiece, currentPlayer));
        }
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("white") ? "black" : "white";
    }

    private int[] parseCoordinates(String input) {
        int col = input.charAt(0) - 'A';
        int row = 8 - Character.getNumericValue(input.charAt(1));
        return new int[]{row, col};
    }

    public void displayBoard() {
        board.display();
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }
}
