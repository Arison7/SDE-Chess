class Pawn extends Piece {
    private boolean hasMoved = false;

    public Pawn(String color) {
        super(color);
        this.moveStrategy = new PawnMoveStrategy();
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "P" : "p";
    }
}

class PawnMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        boolean isWhite = board.getPiece(startX, startY).getColor().equals("white");
        int direction = isWhite ? -1 : 1;
        Piece target = board.getPiece(endX, endY);
        if (startY == endY && target == null) {
            boolean move = startX + direction == endX;
            // Check for the starting move of the pawns
            if(startX == 1 && !isWhite || startX == 6 && isWhite) {
                move = move || startX + (direction * 2) == endX;
            }
            return move;
        } else if (Math.abs(startY - endY) == 1 && startX + direction == endX && target != null && !target.getColor().equals(board.getPiece(startX, startY).getColor())) {
            return true; // Capture diagonally
        }
        return false;
    }
}
