class Queen extends Piece {
    public Queen(String color) {
        super(color);
        this.moveStrategy = new QueenMoveStrategy();
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "Q" : "q";
    }
}

class QueenMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        return new RookMoveStrategy().isMoveValid(startX, startY, endX, endY, board) ||
                new BishopMoveStrategy().isMoveValid(startX, startY, endX, endY, board);
    }
}
