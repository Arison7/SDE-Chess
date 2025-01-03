class Rook extends Piece {
    private boolean hasMoved = false;

    public Rook(String color) {
        super(color);
        this.moveStrategy = new RookMoveStrategy();
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "R" : "r";
    }
}

class RookMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        if (startX != endX && startY != endY) return false;
        int xDir = Integer.compare(endX, startX);
        int yDir = Integer.compare(endY, startY);
        for (int i = 1; i < Math.max(Math.abs(endX - startX), Math.abs(endY - startY)); i++) {
            if (board.getPiece(startX + i * xDir, startY + i * yDir) != null) return false; // Path blocked
        }
        return true;
    }
}
