
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author gueel
 */
public class Rook extends ChessPiece{
    
    public Rook(Color cor, Board board) {
        super(cor, board);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
         boolean [][]mat = new boolean [getBoard().getRow()][getBoard().getColumn()];
         // Cima
         Position p = new Position(0,0);
         p.setValues(position.getRow()-1,position.getColumn());
         while(getBoard().positionExists(p)&& !getBoard().thereIsAPiece(p)){
             mat[p.getRow()][p.getColumn()]= true;
             p.setRow(p.getRow()-1);
         }
         if(getBoard().positionExists(p)&& isThereOpponentPiece(p) ){
         mat[p.getRow()][p.getColumn()]= true;
         }
         // Esqueda
         p.setValues(position.getRow(),position.getColumn()-1);
         while(getBoard().positionExists(p)&& !getBoard().thereIsAPiece(p)){
             mat[p.getRow()][p.getColumn()]= true;
             p.setColumn(p.getColumn()-1);
         }
         if(getBoard().positionExists(p)&& isThereOpponentPiece(p) ){
         mat[p.getRow()][p.getColumn()]= true;
         }
         
         //direita
         p.setValues(position.getRow(),position.getColumn()+1);
         while(getBoard().positionExists(p)&& !getBoard().thereIsAPiece(p)){
             mat[p.getRow()][p.getColumn()]= true;
             p.setColumn(p.getColumn()+1);
         }
         if(getBoard().positionExists(p)&& isThereOpponentPiece(p) ){
         mat[p.getRow()][p.getColumn()]= true;
         }
         //Baixo
         p.setValues(position.getRow()+1,position.getColumn());
         while(getBoard().positionExists(p)&& !getBoard().thereIsAPiece(p)){
             mat[p.getRow()][p.getColumn()]= true;
             p.setRow(p.getRow()+1);
         }
         if(getBoard().positionExists(p)&& isThereOpponentPiece(p) ){
         mat[p.getRow()][p.getColumn()]= true;
         }
        return mat;
    }
    
}
