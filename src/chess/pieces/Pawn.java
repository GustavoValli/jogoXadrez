package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
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

            //special move en passant
            if (this.position.getRow() == 3) {
                Position redPawnLeft = new Position(this.position.getRow(), this.position.getColumn() - 1);

                if (getBoard().positionExists(redPawnLeft) && isThereOpponentPiece(redPawnLeft) && getBoard().piece(redPawnLeft) == chessMatch.getEnPassantVulnerable()) {
                    possibleMoves[redPawnLeft.getRow() - 1][redPawnLeft.getColumn()] = true;
                }

                Position redPawnRight = new Position(this.position.getRow(), this.position.getColumn() + 1);

                if (getBoard().positionExists(redPawnRight) && isThereOpponentPiece(redPawnRight) && getBoard().piece(redPawnRight) == chessMatch.getEnPassantVulnerable()) {
                    possibleMoves[redPawnRight.getRow() - 1][redPawnRight.getColumn()] = true;
                }
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

            //special move en passant
            if (this.position.getRow() == 4) {
                Position bluePawnLeft = new Position(this.position.getRow(), this.position.getColumn() - 1);

                if (getBoard().positionExists(bluePawnLeft) && isThereOpponentPiece(bluePawnLeft) && getBoard().piece(bluePawnLeft) == chessMatch.getEnPassantVulnerable()) {
                    possibleMoves[bluePawnLeft.getRow() + 1][bluePawnLeft.getColumn()] = true;
                }

                Position bluePawnRight = new Position(this.position.getRow(), this.position.getColumn() + 1);

                if (getBoard().positionExists(bluePawnRight) && isThereOpponentPiece(bluePawnRight) && getBoard().piece(bluePawnRight) == chessMatch.getEnPassantVulnerable()) {
                    possibleMoves[bluePawnRight.getRow() + 1][bluePawnRight.getColumn()] = true;
                }
            }

        }

        return possibleMoves;
    }
}
