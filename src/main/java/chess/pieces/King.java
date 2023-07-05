package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author gueel
 */
public class King extends ChessPiece {

    public King(Color cor, Board board) {
        super(cor, board);
    }

    @Override
    public String toString() {
        return "K";
    }

    public boolean canMove(Position position) {
        //DIFERENCIA WHITER/BLACK
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        // VERIFICAR SE A PEÇA PODE SE MOVIMENTAR
        boolean[][] mat = new boolean[getBoard().getRow()][getBoard().getColumn()];
        Position p = new Position(0, 0);
        // PARA CIMA
        p.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        // PARA ABAIXO
        p.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        //PARA ESQUERDA
        p.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        // PARA DIREITA 
        p.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        // PARA NOROESTE
        p.setValues(position.getRow()-1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        //PARA NORDESTE
         p.setValues(position.getRow() - 1, position.getColumn()+1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        // PARA SUDESTE
        p.setValues(position.getRow() + 1, position.getColumn()+1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        //PARA SUDOESTE
        p.setValues(position.getRow()+1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && canMove(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        return mat;
    }
}
