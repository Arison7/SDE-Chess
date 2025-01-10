class Queen extends Piece {

    // Constructor to create a Queen piece with the given color
    public Queen(String color) {
        super(color);
        this.moveStrategy = new QueenMoveStrategy();
    }

    @Override
    public String getSymbol() {
        // Return the symbol for the Queen ('Q' for white, 'q' for black)
        return color.equals("white") ? "Q" : "q";
    }
}

// Check if the Queen's move is valid
class QueenMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        return new RookMoveStrategy().isMoveValid(startX, startY, endX, endY, board) ||
                new BishopMoveStrategy().isMoveValid(startX, startY, endX, endY, board);
    }
}
