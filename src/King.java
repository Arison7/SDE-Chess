class King extends Piece {
    private boolean hasMoved = false;

    public King(String color) {
        super(color);
        this.moveStrategy = new KingMoveStrategy();
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "K" : "k";
    }
}

class KingMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        if (Math.abs(endX - startX) <= 1 && Math.abs(endY - startY) <= 1) {
            Piece target = board.getPiece(endX, endY);
            return target == null || !target.getColor().equals(board.getPiece(startX, startY).getColor());
        }
        return false;
    }
}
