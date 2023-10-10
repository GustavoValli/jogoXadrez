package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private Board board;
    private int turn;
    private Color currentPlayer;
    private boolean check;
    private boolean checkMate;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.BLUE;
        check = false;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] pieces = new ChessPiece[board.getRows()][board.getColumns()];

        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                pieces[i][j] = (ChessPiece) board.piece(i, j);
            }
        }

        return pieces;
    }

    public boolean[][] possibleMoves(ChessPosition source) {
        Position position = source.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        check = testCheck(opponent(currentPlayer));

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        return (ChessPiece) capturedPiece;
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece movingPiece = (ChessPiece) board.removePiece(source);
        movingPiece.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(movingPiece, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece piece = (ChessPiece) board.removePiece(target);
        piece.decreaseMoveCount();
        board.placePiece(piece, source);

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece in source position");
        }
        if (currentPlayer != ( (ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("The chosen piece is not yours");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible moves for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Color.BLUE) ? Color.RED : Color.BLUE;
    }

    private Color opponent(Color color) {
        return (color == Color.BLUE) ? Color.RED : Color.BLUE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ( (ChessPiece) x).getColor() == color).collect(Collectors.toList());

        for (Piece piece : list) {
            if (piece instanceof King) {
                return (ChessPiece) piece;
            }
        }

        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ( (ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());

        for (Piece piece : opponentPieces) {
            boolean[][] possibleMoves = piece.possibleMoves();
            if (possibleMoves[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }

        return false;
    }

    private boolean testCheckMate(Color color) {
        if (!testCheck(color)) {
            return false;
        }

        List<Piece> list = piecesOnTheBoard.stream().filter(x -> ( (ChessPiece) x).getColor() == color).collect(Collectors.toList());

        for (Piece piece : list) {
            boolean[][] possibleMoves = piece.possibleMoves();

            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (possibleMoves[i][j]) {
                        Position source = ( (ChessPiece) piece).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);

                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }

        }

        return true;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup() {
        //red pawns
        placeNewPiece('a', 7, new Pawn(board, Color.RED));
        placeNewPiece('b', 7, new Pawn(board, Color.RED));
        placeNewPiece('c', 7, new Pawn(board, Color.RED));
        placeNewPiece('d', 7, new Pawn(board, Color.RED));
        placeNewPiece('e', 7, new Pawn(board, Color.RED));
        placeNewPiece('f', 7, new Pawn(board, Color.RED));
        placeNewPiece('g', 7, new Pawn(board, Color.RED));
        placeNewPiece('h', 7, new Pawn(board, Color.RED));

        //red bishops
        placeNewPiece('c', 8, new Bishop(board, Color.RED));
        placeNewPiece('f', 8, new Bishop(board, Color.RED));

        //red knights
        placeNewPiece('b', 8, new Knight(board, Color.RED));
        placeNewPiece('g', 8, new Knight(board, Color.RED));

        //red rooks
        placeNewPiece('a', 8, new Rook(board, Color.RED));
        placeNewPiece('h', 8, new Rook(board, Color.RED));

        //red king
        placeNewPiece('e', 8, new King(board, Color.RED));

        //blue queen
        placeNewPiece('d', 8, new Queen(board, Color.RED));

        //----------------------------------------------------------------------

        //blue pawns
        placeNewPiece('a', 2, new Pawn(board, Color.BLUE));
        placeNewPiece('b', 2, new Pawn(board, Color.BLUE));
        placeNewPiece('c', 2, new Pawn(board, Color.BLUE));
        placeNewPiece('d', 2, new Pawn(board, Color.BLUE));
        placeNewPiece('e', 2, new Pawn(board, Color.BLUE));
        placeNewPiece('f', 2, new Pawn(board, Color.BLUE));
        placeNewPiece('g', 2, new Pawn(board, Color.BLUE));
        placeNewPiece('h', 2, new Pawn(board, Color.BLUE));

        //blue bishops
        placeNewPiece('c', 1, new Bishop(board, Color.BLUE));
        placeNewPiece('f', 1, new Bishop(board, Color.BLUE));

        //blue knights
        placeNewPiece('b', 1, new Knight(board, Color.BLUE));
        placeNewPiece('g', 1, new Knight(board, Color.BLUE));

        //blue rooks
        placeNewPiece('a', 1, new Rook(board, Color.BLUE));
        placeNewPiece('h', 1, new Rook(board, Color.BLUE));

        //blue king
        placeNewPiece('e', 1, new King(board, Color.BLUE));

        //blue queen
        placeNewPiece('d', 1, new Queen(board, Color.BLUE));
    }
}
