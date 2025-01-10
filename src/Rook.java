class Rook extends Piece {

    // Track if the rook has moved
    private boolean hasMoved = false;

    // Constructor to create a Rook piece with the given color
    public Rook(String color) {
        super(color);
        this.moveStrategy = new RookMoveStrategy();
    }

    // Return whether the Rook has moved
    public boolean hasMoved() {
        return hasMoved;
    }

    // Set whether the Rook has moved
    public void setMoved(boolean moved) {
        this.hasMoved = moved;
    }

    @Override
    public String getSymbol() {
        // Return the symbol for the Rook ('R' for white, 'r' for black)
        return color.equals("white") ? "R" : "r";
    }
}

// Check if the Rook's move is valid
class RookMoveStrategy implements MoveStrategy {
    @Override
    public boolean isMoveValid(int startX, int startY, int endX, int endY, Board board) {
        // Rooks can only move in straight lines, either horizontally or vertically
        if (startX != endX && startY != endY) return false;
        int xDir = Integer.compare(endX, startX);
        int yDir = Integer.compare(endY, startY);
        
        // Check if there are any pieces blocking the path
        for (int i = 1; i < Math.max(Math.abs(endX - startX), Math.abs(endY - startY)); i++) {
            if (board.getPiece(startX + i * xDir, startY + i * yDir) != null) return false; // Path blocked
        }

        // Move is valid if the path is clear
        return true;
    }
}
