# Chess Game with Design Patterns

## Introduction

This project implements a text-based chess game in Java, utilizing various design patterns to enhance code structure, maintainability, and flexibility. The program allows two players to play chess with proper rules, including check, checkmate, stalemate, and pawn promotion. The design patterns applied in this project include **Creational**, **Structural**, and **Behavioral** patterns, as detailed below.

### Collaboration

The program was developed collaboratively by two team members. Contributions were balanced, with one team member focusing on implementing the game logic and structural patterns, while the other focused on the creational patterns and the main game flow. Code reviews and pair programming sessions ensured equal participation and quality improvements.

## Design Patterns

### Creational Patterns

#### a. **Factory Pattern**

- **Usage:** The `PieceFactory` class creates chess pieces based on their type and color.
- **Benefit:** Simplifies piece creation logic and ensures adherence to the Single Responsibility Principle.
- **Code Example:**
  ```java
  public class PieceFactory {
      public static Piece createPiece(String type, String color) {
          return switch (type.toLowerCase()) {
              case "pawn" -> new Pawn(color);
              case "rook" -> new Rook(color);
              case "knight" -> new Knight(color);
              case "bishop" -> new Bishop(color);
              case "queen" -> new Queen(color);
              case "king" -> new King(color);
              default -> throw new IllegalArgumentException("Invalid piece type.");
          };
      }
  }
  ```

  #### b. **Singleton Pattern**

- **Usage:** The `Board` class implements the Singleton pattern to ensure only one instance of the board is used throughout the game.

- **Benefit:** Guarantees that there is a single shared instance of the chessboard, avoiding multiple instances and ensuring a consistent state across the game.

- **Code Example:**

  ```java
  class Board {
      private static Board instance; // The single instance of Board.
      private final Square[][] squares;

      private Board() {
          squares = new Square[8][8];
          for (int i = 0; i < 8; i++) {
              for (int j = 0; j < 8; j++) {
                  squares[i][j] = new Square();
              }
          }
      }

      // Public method to get the single instance of the Board
      public static Board getInstance() {
          if (instance == null) {
              instance = new Board(); // Create the instance if it doesn't exist
          }
          return instance;
      }

      // Other methods for piece movement and game logic...
  }
  ```

### Behavioral Patterns
#### a. **Template Method Pattern**

- **Usage:** The `Piece` class defines the core structure for piece movement, and subclasses (e.g., `King`) implement specific movement logic.
- **Benefit:** Standardizes move validation across all pieces while allowing each piece to have its own specific movement logic.
- **Code Example:**

  ```java
  // Abstract base class for chess pieces
  abstract class Piece {
      protected String color;

      public Piece(String color) {
          this.color = color;
      }

      public abstract String getSymbol();

      // Template method for move validation
      public boolean canMove(int startX, int startY, int endX, int endY) {
          if (endX < 0 || endX >= 8 || endY < 0 || endY >= 8) return false;
          return isMoveValid(startX, startY, endX, endY);
      }

      // Abstract method for specific piece move logic
      protected abstract boolean isMoveValid(int startX, int startY, int endX, int endY);
  }

  // King piece class implementing specific move logic
  class King extends Piece {
      public King(String color) {
          super(color);
      }

      @Override
      public String getSymbol() {
        return color.equals("white") ? "K" : "k";
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
  }
  ```

#### b. **Observer Pattern**

- **Usage:** The `Board` class maintains a list of observers, such as the `GameLogger`, which are notified of changes to the board (e.g., after a piece is moved).
- **Benefit:** Promotes decoupling between the board and any components that need to observe and react to changes, such as logging or UI updates. This pattern enables real-time updates without tightly coupling components.
- **Code Example:**

  ```java
  import java.util.ArrayList;
  import java.util.List;

  // The Observer interface
  interface Observer {
      void update(Board board);
  }

  // Concrete Observer
  class GameLogger implements Observer {
      @Override
      public void update(Board board) {
          System.out.println("Board updated:");
          board.display();  // Display the updated board (or log the state)
      }
  }

  // The Subject (Board) class with Observer Pattern
  class Board {
      private List<Observer> observers = new ArrayList<>();
      private final Square[][] squares;

      public Board() {
          squares = new Square[8][8];
          for (int i = 0; i < 8; i++) {
              for (int j = 0; j < 8; j++) {
                  squares[i][j] = new Square();
              }
          }
      }

      // Methods to add and remove observers
      public void addObserver(Observer observer) {
          observers.add(observer);
      }

      public void removeObserver(Observer observer) {
          observers.remove(observer);
      }

      // Notify all observers
      public void notifyObservers() {
          for (Observer observer : observers) {
              observer.update(this);
          }
      }

      // Example of moving a piece and notifying observers
      public void movePiece(Piece piece, int startX, int startY, int endX, int endY) {
          if (piece.canMove(startX, startY, endX, endY, this)) {
              setPiece(endX, endY, piece);  // Move the piece
              setPiece(startX, startY, null);  // Clear the original square

              // Notify observers about the board state change
              notifyObservers();
          }
      }
  }

### Structural Patterns
#### a. **Composite Pattern**

- **Usage:** The `Board` and `Square` classes implement a hierarchical structure where the board contains squares, and each square holds a piece.
- **Benefit:** Facilitates board management and provides a clean way to organize components.
- **Code Example:**

  ```java
  class Board {
      private final Square[][] squares;

      public Board() {
          squares = new Square[8][8];
          for (int i = 0; i < 8; i++) {
              for (int j = 0; j < 8; j++) {
                  squares[i][j] = new Square();
              }
          }
      }
  }
  ```

  #### b. **Facade Pattern**  

- **Usage:** The `ChessGameFacade` class simplifies interaction by encapsulating board setup, move validation, and game state management behind a unified interface.  
- **Benefit:** Reduces complexity in the `ChessGame` class, improves code readability, and centralizes game logic for easier maintenance.  
- **Code Example:**  

  ```java  
  class ChessGameFacade {  
      private Board board;  
      private String currentPlayer;  

      public ChessGameFacade() {  
          this.board = Board.getInstance();  
          this.currentPlayer = "white";  
          setupBoard();  
      }  

      private void setupBoard() {  
          // Setup initial pieces  
      }  

      public boolean makeMove(String start, String end) {  
          // Handle move logic  
          return true;  
      }  
  }  

## Program Features

- **Chess Rules:** Enforces standard chess rules, including legal moves, check, checkmate, stalemate, and pawn promotion.
- **User Input Validation:** Ensures players enter valid moves using chess notation (e.g., A2 to A3).
- **Turn-Based Gameplay:** Alternates turns between white and black players.
- **Game State Evaluation:** Includes methods to detect check, checkmate, and stalemate conditions.
