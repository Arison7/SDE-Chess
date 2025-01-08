public class GameLogger implements GameObserver {
    @Override
    public void update(Board board) {
        // Log the board state after every move
        System.out.println("Board updated: \n" + board);
    }
}