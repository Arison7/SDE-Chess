# Chess Game with Design Patterns

## Introduction

This project implements a text-based chess game in Java, utilizing various design patterns to enhance code structure, maintainability, and flexibility. The program allows two players to play chess with proper rules, including check, checkmate, stalemate, and pawn promotion. The design patterns applied in this project include **Creational**, **Structural**, and **Behavioral** patterns, as detailed below.

### Collaboration
The program was developed collaboratively by two team members. Contributions were balanced, with one team member focusing on implementing the game logic and structural patterns, while the other focused on the creational patterns and the main game flow. Code reviews and pair programming sessions ensured equal participation and quality improvements.

## Design Patterns

### 1. Creational Patterns
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

## Program Features
- **Chess Rules:** Enforces standard chess rules, including legal moves, check, checkmate, stalemate, and pawn promotion.
- **User Input Validation:** Ensures players enter valid moves using chess notation (e.g., A2 to A3).
- **Turn-Based Gameplay:** Alternates turns between white and black players.
- **Game State Evaluation:** Includes methods to detect check, checkmate, and stalemate conditions.