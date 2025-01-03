class Knight extends Piece {
    public Knight(String color) {
        super(color);
        this.moveStrategy = new KnightMoveStrategy();
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "N" : "n";
    }
}

class KnightMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        return dx * dy == 2;
    }
}
