package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position) {
        ChessPiece chessPiece = (ChessPiece) getBoard().piece(position);
        return chessPiece == null || chessPiece.getColor() != getColor();
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

        return possibleMoves;
    }
}
