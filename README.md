# Chess Game with Design Patterns

## Introduction

This is a text-based chess game built in Java. The game follows standard chess rules, such as check, checkmate, stalemate, and pawn promotion. It uses design patterns to make the code easy to manage, understand, and expand. The design patterns applied in this project include **Creational**, **Structural**, and **Behavioral** patterns, as detailed below.

### Teamwork

This project was made by Lukasz Krysmalski and Hao Guo. Lukasz Krysmalski worked more on the chess rules because of the game knowledge. We divided the six design patterns between us to share the work evenly. At the end, we checked the whole code together to fix any problems and make sure everything worked well.

## Design Patterns

### Creational Patterns

#### a. **Factory Pattern**

- **Usage:** Creates chess pieces like pawns or kings based on their type and color.
- **Benefit:** Keeps the code for creating pieces neat and focused.
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

- **Usage:** Ensures there is only one chessboard shared by the whole game.

- **Benefit:** Keeps the game consistent, as all actions happen on the same board.

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

- **Usage:** Defines common rules for how pieces move, while allowing each piece (like a king or rook) to have its own movement logic.
- **Benefit:** Makes sure all pieces follow the same basic rules, but still move differently.
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

- **Usage:** Lets the game notify other parts, like a logger, whenever something changes on the board.
- **Benefit:** Keeps different parts of the game connected without mixing up their responsibilities.
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

- **Usage:** Organizes the board as a collection of squares, each holding a piece or being empty.
- **Benefit:** Makes managing the board simple and structured.
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

- **Usage:** Simplifies how the game starts and runs by combining board setup, move checking, and rule enforcement in one place.  
- **Benefit:** Makes the main game code shorter and easier to follow.
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

- **Chess Rules:** Follows real chess rules, such as valid moves and checkmate.
- **User Input Validation:** Checks that players enter valid moves, like "A2 to A3."
- **Turn-Based Gameplay:** Switches turns between players.
- **Game State Evaluation:** Detects situations like check, checkmate, or stalemate.
