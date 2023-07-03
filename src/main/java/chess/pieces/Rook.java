
package chess.pieces;

import boardgame.Board;
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
    
}
