
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
        Board board = Board.getInstance(); // Use Singleton to get the Board instance

        // Setup initial pieces
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

        Scanner scanner = new Scanner(System.in);
        String currentPlayer = "white";


        // Game loop
        while (true) {
            // Display the board
            board.display();
            System.out.println(currentPlayer + "'s turn.");

            int[] start = getCoordinates(scanner, "Enter the starting square (e.g., A2): ");
            int startX = start[0], startY = start[1];
            Piece piece = board.getPiece(startX, startY);
            //Extracting the logic to different each statement so it's possible to provide better error messages
            if(piece == null){
                System.out.println("Selected square doesn't hold a piece");
                continue;
            }
            if(!piece.getColor().equals(currentPlayer)){
                System.out.println("You can't move your opponent's pieces. The game would have been too easy ;p");
                continue;
            }
            int[] end = getCoordinates(scanner, "Enter the destination square (e.g., A3): ");
            int endX = end[0], endY = end[1];

            if ( piece.canMove(startX, startY, endX, endY, board)) {
                Piece captured = board.getPiece(endX, endY);
                board.setPiece(endX, endY, piece);
                board.setPiece(startX, startY, null);

                if (board.isKingInCheck(currentPlayer)) {
                    System.out.println("Invalid move. Your king is in check.");
                    board.setPiece(startX, startY, piece);
                    board.setPiece(endX, endY, captured);
                    continue;
                }

                if (piece instanceof Pawn) {
                    ((Pawn) piece).setMoved(true);
                    if ((currentPlayer.equals("white") && endX == 0) || (currentPlayer.equals("black") && endX == 7)) {
                        System.out.println("Pawn promotion! Enter new piece (queen/rook/knight/bishop): ");
                        try{
                            String newPiece = scanner.next();
                            board.setPiece(endX, endY, PieceFactory.createPiece(newPiece, currentPlayer));
                        }catch( Exception e ){
                            // If the user tries to enter invalid piece inform them of it
                            if(e instanceof  IllegalArgumentException){
                                System.out.println("Invalid piece name. Try again.");
                                continue;
                            }
                            throw e;
                        }
                    }
                }

                if (board.isCheckmate(currentPlayer.equals("white") ? "black" : "white")) {
                    board.display();
                    System.out.println("Checkmate! " + currentPlayer + " wins!");
                    break;
                } else if (board.isStalemate(currentPlayer.equals("white") ? "black" : "white")) {
                    board.display();
                    System.out.println("Stalemate! It's a draw.");
                    break;
                }
                // I should have used boolean for the current player ....
                currentPlayer = currentPlayer.equals("white") ? "black" : "white";
            } else {
                System.out.println("Invalid move. Try again.");
            }
        }

        scanner.close();
    }

    // Converts chess notation to board indices
    private static int[] getCoordinates(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.matches("[A-H][1-8]")) {
                int col = input.charAt(0) - 'A';
                int row = 8 - Character.getNumericValue(input.charAt(1));
                return new int[]{row, col};
            } else {
                System.out.println("Invalid input. Please use chess notation (e.g., A2).");
            }
        }
    }
}
