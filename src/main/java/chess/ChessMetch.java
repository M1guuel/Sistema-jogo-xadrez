/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import boardgame.Board;

/**
 *
 * @author gueel
 */
public class ChessMetch {

    private Board board;

    public ChessMetch() {
        board = new Board(8, 8);
    }

    public ChessPiece[][] getPiece() {
        ChessPiece[][]mat = new ChessPiece[board.getRow()][board.getColumn()];
        for (int i = 0; i < board.getRow(); i++) {
            for (int j=0;j<board.getColumn();j++){
                mat[i][j]= (ChessPiece) board.piece(j, j);
            }

        }
        return mat;
    }
}
