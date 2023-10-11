package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
        return chessPiece == null || chessPiece.getColor() != getColor();
    }

    private boolean testRookCastling(Position position) {
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
        return chessPiece != null && chessPiece instanceof Rook && chessPiece.getColor() == getColor()&& chessPiece.getMoveCount() == 0;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position position = new Position(0, 0);

        //up
        position.setValues(this.position.getRow() - 1, this.position.getColumn());
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //nw
        position.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //left
        position.setValues(this.position.getRow(), this.position.getColumn() - 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //sw
        position.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //down
        position.setValues(this.position.getRow() + 1, this.position.getColumn());
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //se
        position.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //right
        position.setValues(this.position.getRow(), this.position.getColumn() + 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //ne
        position.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //special move castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {

            //king side
            Position position1 = new Position(this.position.getRow(), this.position.getColumn() + 3);

            if (testRookCastling(position1)) {
                Position position2 = new Position(this.position.getRow(), this.position.getColumn() + 1);
                Position position3 = new Position(this.position.getRow(), this.position.getColumn() + 2);

                if (getBoard().piece(position2) == null && getBoard().piece(position3) == null) {
                    possibleMoves[position3.getRow()][position3.getColumn()] = true;
                }
            }

            //queen side
            position1.setValues(this.position.getRow(), this.position.getColumn() - 4);

            if (testRookCastling(position1)) {
                Position position2 = new Position(this.position.getRow(), this.position.getColumn() - 1);
                Position position3 = new Position(this.position.getRow(), this.position.getColumn() - 2);
                Position position4 = new Position(this.position.getRow(), this.position.getColumn() - 3);

                if (getBoard().piece(position2) == null && getBoard().piece(position3) == null && getBoard().piece(position4) == null) {
                    possibleMoves[position3.getRow()][position3.getColumn()] = true;
                }
            }
        }

        return possibleMoves;
    }
}
