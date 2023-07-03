/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import chess.ChessPiece;

/**
 *
 * @author gueel
 */
public class UI {
    public static void printBoard(ChessPiece[][]piece){
        for (int i=0; i<piece.length; i++){
            System.out.print((8-i)+" ");
            for (int j=0; j<piece.length; j++){
                printPiece(piece[i][j]);
            }
            System.out.println("");
        }
        System.out.println("  A B C D E F G H ");
    }
    private static void printPiece(ChessPiece piece){
        if (piece == null){
            System.out.print("-");
        }else{
            System.out.print(piece);
        }
        System.out.print(" ");
    }
}