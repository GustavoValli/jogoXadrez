package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "P";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position position = new Position(0, 0);
        Position positionAux = new Position(0, 0);

        //blue pawn
        if (getColor() == Color.BLUE) {

            //one house
            position.setValues(this.position.getRow() - 1, this.position.getColumn());
            if (getBoard().positionExists(position) && !getBoard().thereIsAPiece(position)) {
                possibleMoves[position.getRow()][position.getColumn()] = true;
            }

            //two houses
            position.setValues(this.position.getRow() - 2, this.position.getColumn());
            positionAux.setValues(this.position.getRow() - 1, this.position.getColumn());
            if (getBoard().positionExists(position) && !getBoard().thereIsAPiece(position) && getBoard().positionExists(positionAux) && !getBoard().thereIsAPiece(positionAux) && getMoveCount() == 0) {
                possibleMoves[position.getRow()][position.getColumn()] = true;
            }

            //capturing a piece
            position.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
            if (getBoard().positionExists(position) && isThereOpponentPiece(position)) {
                possibleMoves[position.getRow()][position.getColumn()] = true;
            }

            position.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
            if (getBoard().positionExists(position) && isThereOpponentPiece(position)) {
                possibleMoves[position.getRow()][position.getColumn()] = true;
            }
        }

        //red pawn
        if (getColor() == Color.RED) {

            //one house
            position.setValues(this.position.getRow() + 1, this.position.getColumn());
            if (getBoard().positionExists(position) && !getBoard().thereIsAPiece(position)) {
                possibleMoves[position.getRow()][position.getColumn()] = true;
            }

            //two houses
            position.setValues(this.position.getRow() + 2, this.position.getColumn());
            positionAux.setValues(this.position.getRow() + 1, this.position.getColumn());
            if (getBoard().positionExists(position) && !getBoard().thereIsAPiece(position) && getBoard().positionExists(positionAux) && !getBoard().thereIsAPiece(positionAux) && getMoveCount() == 0) {
                possibleMoves[position.getRow()][position.getColumn()] = true;
            }

            //capturing a piece
            position.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
            if (getBoard().positionExists(position) && isThereOpponentPiece(position)) {
                possibleMoves[position.getRow()][position.getColumn()] = true;
            }

            position.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
            if (getBoard().positionExists(position) && isThereOpponentPiece(position)) {
                possibleMoves[position.getRow()][position.getColumn()] = true;
            }
        }

        return possibleMoves;
    }
}
