// Creational Pattern: Factory Pattern
// Purpose:
// 1. To create different types of chess pieces dynamically.
// 2. Helps when dealing with user input since it doesn't require parsing (In this case for promotion)
class PieceFactory {
    public static Piece createPiece(String type, String color) {
        switch (type.toLowerCase()) {
            case "pawn": return new Pawn(color);
            case "rook": return new Rook(color);
            case "knight": return new Knight(color);
            case "bishop": return new Bishop(color);
            case "queen": return new Queen(color);
            case "king": return new King(color);
            default: throw new IllegalArgumentException("Unknown piece type: " + type);
        }
    }
}

// Abstract base class for chess pieces
abstract class Piece {
    protected String color;
    protected MoveStrategy moveStrategy; // Behavioral Pattern: Strategy Pattern

    public Piece(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract String getSymbol();

    public boolean canMove(int startX, int startY, int endX, int endY, Board board) {
        // Check out of bounds for all calls
        if (endX < 0 || endX >= 8 || endY < 0 || endY >= 8) return false;
        Piece target = board.getPiece(endX, endY);
        // Protect against capturing your own piece
        if (target != null && target.getColor().equals(this.color)) return false;
        return moveStrategy.isMoveValid(startX, startY, endX, endY, board);
    }
}
