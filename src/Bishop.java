// The Bishop class represents the bishop piece in chess
class Bishop extends Piece {
    // This creates a bishop and sets how it moves
    public Bishop(String color) {
        super(color);
        this.moveStrategy = new BishopMoveStrategy();
    }

    @Override
    public String getSymbol() {
        // Returns "B" for a white bishop and "b" for a black bishop
        return color.equals("white") ? "B" : "b";
    }
}

// This class defines how the bishop moves on the board
class BishopMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        // Bishop can only move diagonally (same change in X and Y)
        if (Math.abs(endX - startX) != Math.abs(endY - startY)) return false;

        // Find the direction the bishop is moving (up-right, down-left, etc.)
        int xDir = Integer.compare(endX, startX);
        int yDir = Integer.compare(endY, startY);

        // Check if the path is clear (no pieces blocking the diagonal)
        for (int i = 1; i < Math.abs(endX - startX); i++) {
            if (board.getPiece(startX + i * xDir, startY + i * yDir) != null) return false; // Move is not valid if something is in the way
        }
        // If no pieces are in the way, the move is valid
        return true;
    }
}
