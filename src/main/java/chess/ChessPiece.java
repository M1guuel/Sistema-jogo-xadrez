package chess;

import boardgame.Board;
import boardgame.Piece;

/**
 *
 * @author gueel
 */
public class ChessPiece extends Piece{
    
    private Color cor;

    public ChessPiece(Color cor, Board board) {
        super(board);
        this.cor = cor;
    }

    public Color getCor() {
        return cor;
    }

   
    
    
    
}
