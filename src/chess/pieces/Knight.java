package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "N";
    }

    private boolean canMove(Position position) {
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
        return chessPiece == null || chessPiece.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] possibleMoves = new boolean[getBoard().getRows()][getBoard().getRows()];

        Position position = new Position(0, 0);

        //nw
        position.setValues(this.position.getRow() - 2, this.position.getColumn() - 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] =  true;
        }

        position.setValues(this.position.getRow() - 1, this.position.getColumn() - 2);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] =  true;
        }

        //sw
        position.setValues(this.position.getRow() + 1, this.position.getColumn() - 2);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] =  true;
        }

        position.setValues(this.position.getRow() + 2, this.position.getColumn() - 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] =  true;
        }

        //se
        position.setValues(this.position.getRow() + 2, this.position.getColumn() + 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] =  true;
        }

        position.setValues(this.position.getRow() + 1, this.position.getColumn() + 2);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] =  true;
        }

        //ne
        position.setValues(this.position.getRow() - 1, this.position.getColumn() + 2);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] =  true;
        }

        position.setValues(this.position.getRow() - 2, this.position.getColumn() + 1);
        if (getBoard().positionExists(position) && canMove(position)) {
            possibleMoves[position.getRow()][position.getColumn()] =  true;
        }

        return possibleMoves;
    }
}
