// Behavioral Pattern: Strategy Pattern
// Purpose: Encapsulate movement rules for each piece.
interface MoveStrategy {
    boolean isMoveValid(int startX, int startY, int endX, int endY, Board board);
            //, Piece piece);

}
