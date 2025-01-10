// Knight class represents the knight piece in the game.
class Knight extends Piece {

    // Constructor initializes the knight with its color and move strategy.
    public Knight(String color) {
        super(color);
        this.moveStrategy = new KnightMoveStrategy();
    }

    @Override
    public String getSymbol() {
        // Returns the symbol for the knight: 'N' for white, 'n' for black.
        return color.equals("white") ? "N" : "n";
    }
}

// KnightMoveStrategy defines the rules for how the knight moves.
class KnightMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        return dx * dy == 2; // Valid if it moves in an 'L' shape.
    }
}
