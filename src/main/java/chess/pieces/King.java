
package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author gueel
 */
public class King extends  ChessPiece{
    
    public King(Color cor, Board board) {
        super(cor, board);
    }

    @Override
    public String toString() {
        return "K";
    }
    
}
