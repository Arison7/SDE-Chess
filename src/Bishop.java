class Bishop extends Piece {
    public Bishop(String color) {
        super(color);
        this.moveStrategy = new BishopMoveStrategy();
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "B" : "b";
    }
}

class BishopMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        if (Math.abs(endX - startX) != Math.abs(endY - startY)) return false;
        int xDir = Integer.compare(endX, startX);
        int yDir = Integer.compare(endY, startY);
        for (int i = 1; i < Math.abs(endX - startX); i++) {
            if (board.getPiece(startX + i * xDir, startY + i * yDir) != null) return false; // Path blocked
        }
        return true;
    }
}
