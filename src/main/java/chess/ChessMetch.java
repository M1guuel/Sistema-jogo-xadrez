/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 *
 * @author gueel
 */
public class ChessMetch {

    private Board board;

    public ChessMetch() {
        board = new Board(8, 8);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRow()][board.getColumn()];
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getColumn(); j++) {
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }

        }
        return mat;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
    }

    private void initialSetup() {
        placeNewPiece ( 'c' , 2 , new  Rook (  Color . WHITE, board ));
        placeNewPiece ( 'd' , 2 , new  Rook ( Color . WHITE , board));
        placeNewPiece ( 'e' , 2 , new  Rook (  Color . WHITE , board));
        placeNewPiece ( 'e' , 1 , new  Rook (  Color . WHITE, board ));
        placeNewPiece ( 'd' , 1 , new  King ( Color . WHITE , board));

        placeNewPiece ( 'c' , 7 , new  Rook ( Color . BLACK , board));
        placeNewPiece ( 'c' , 8 , new  Rook ( Color . BLACK , board));
        placeNewPiece ( 'd' , 7 , new  Rook ( Color . BLACK , board));
        placeNewPiece ( 'e' , 7 , new  Rook (  Color . BLACK, board ));
        placeNewPiece ( 'e' , 8 , new  Rook (  Color . BLACK , board));
        placeNewPiece ( 'd' , 8 , new  King ( Color . BLACK , board));
    }
}
