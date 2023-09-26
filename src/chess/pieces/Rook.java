package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position position = new Position(0, 0);

        //up
        position.setValues(this.position.getRow() - 1, this.position.getColumn());
        while (getBoard().positionExists(position) && !getBoard().thereIsAPiece(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
            position.setRow(position.getRow() - 1);
        }
        if (getBoard().positionExists(position) && isThereOpponentPiece(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //left
        position.setValues(this.position.getRow(), this.position.getColumn() - 1);
        while (getBoard().positionExists(position) && !getBoard().thereIsAPiece(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
            position.setColumn(position.getColumn() - 1);
        }
        if (getBoard().positionExists(position) && isThereOpponentPiece(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //down
        position.setValues(this.position.getRow() + 1, this.position.getColumn());
        while (getBoard().positionExists(position) && !getBoard().thereIsAPiece(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
            position.setRow(position.getRow() + 1);
        }
        if (getBoard().positionExists(position) && isThereOpponentPiece(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        //right
        position.setValues(this.position.getRow(), this.position.getColumn() + 1);
        while (getBoard().positionExists(position) && !getBoard().thereIsAPiece(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
            position.setColumn(position.getColumn() + 1);
        }
        if (getBoard().positionExists(position) && isThereOpponentPiece(position)) {
            possibleMoves[position.getRow()][position.getColumn()] = true;
        }

        return possibleMoves;
    }
}
