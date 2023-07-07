/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMetch;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author gueel
 */
public class Pawn extends ChessPiece {

    private ChessMetch chessMetch;

    public Pawn(Color color, Board board,ChessMetch chessMetch) {
        super(color, board);
        this.chessMetch = chessMetch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRow()][getBoard().getColumn()];
        Position p = new Position(0, 0);
        
        if (getColor() == Color.WHITE) {
            p.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;

            }
            p.setValues(position.getRow() - 2, position.getColumn());
            Position p2 = new Position(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p.getRow()][p.getColumn()] = true;

            }
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
            // #specialmove en passant white
            if (position.getRow() == 3) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMetch.getEnPassantVulnerable()) {
                    mat[left.getRow() - 1][left.getColumn()] = true;
                }
            }
            Position reght = new Position(position.getRow(), position.getColumn() + 1);
            if (getBoard().positionExists(reght) && isThereOpponentPiece(reght) && getBoard().piece(reght) == chessMetch.getEnPassantVulnerable()) {
                mat[reght.getRow() - 1][reght.getColumn()] = true;
            }
        }
   
         else if (getColor() == Color.BLACK) {
            p.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;

        }
        p.setValues(position.getRow() + 2, position.getColumn());
        Position p2 = new Position(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }
         // #specialmove en passant black
            if (position.getRow() == 4) {
                Position left = new Position(position.getRow(), position.getColumn() - 1);
                if (getBoard().positionExists(left) && isThereOpponentPiece(left) && getBoard().piece(left) == chessMetch.getEnPassantVulnerable()) {
                    mat[left.getRow() +  1][left.getColumn()] = true;
                }
            }
            Position reght = new Position(position.getRow(), position.getColumn() + 1);
            if (getBoard().positionExists(reght) && isThereOpponentPiece(reght) && getBoard().piece(reght) == chessMetch.getEnPassantVulnerable()) {
                mat[reght.getRow() + 1][reght.getColumn()] = true;
            }
    }
    return mat ;
}
@Override
public String toString() {
        return "P";
    }

}
