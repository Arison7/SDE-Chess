// A class that logs the state of the game whenever the board changes.
// Implements the GameObserver interface to receive updates.
public class GameLogger implements GameObserver {
    @Override
    public void update(Board board) {
        // This method is called whenever the board changes.
        // Prints the updated state of the board to the console.
        System.out.println("Board updated: \n" + board);
    }
}