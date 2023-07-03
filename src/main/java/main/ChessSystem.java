/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package main;

import boardgame.Board;
import chess.ChessMetch;

/**
 *
 * @author gueel
 */
public class ChessSystem {

    public static void main(String[] args) {
        ChessMetch chessMetch = new ChessMetch();
        UI.printBoard(chessMetch.getPieces());
    }
}
