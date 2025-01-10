// King class represents the king piece in the game.
class King extends Piece {
    // Tracks if the king has moved.
    private boolean hasMoved = false;

    // Constructor initializes the king with its color and move strategy.
    public King(String color) {
        super(color);
        this.moveStrategy = new KingMoveStrategy();
    }

    // Returns whether the king has moved.
    public boolean hasMoved() {
        return hasMoved;
    }

    // Sets the king's movement status.
    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }

    @Override
    public String getSymbol() {
        // Returns the symbol of the king: 'K' for white, 'k' for black.
        return color.equals("white") ? "K" : "k";
    }
}

// KingMoveStrategy defines the rules for how the king moves.
class KingMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        // The king can move one square in any direction.
        if (Math.abs(endX - startX) <= 1 && Math.abs(endY - startY) <= 1) {
            Piece target = board.getPiece(endX, endY);
            // The king can move to an empty square or capture an opponent's piece.
            return target == null || !target.getColor().equals(board.getPiece(startX, startY).getColor());
        }
        return false;
    }
}
