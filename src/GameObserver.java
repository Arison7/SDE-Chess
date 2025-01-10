// This interface is used by classes that want to observe changes in the game board.
public interface GameObserver {
    // This method is called to notify the observer about changes to the board.
    void update(Board board);
}