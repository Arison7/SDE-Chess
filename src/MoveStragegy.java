// Behavioral Pattern: Strategy Pattern
// Purpose: Encapsulate movement rules for each piece.
interface MoveStrategy {
    // Method to check if the move is valid for a piece from start to end position on the board
    boolean isMoveValid(int startX, int startY, int endX, int endY, Board board);
            //, Piece piece);

}
