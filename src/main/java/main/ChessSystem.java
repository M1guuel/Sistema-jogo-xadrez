/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package main;

import boardgame.Board;
import chess.ChessException;
import chess.ChessMetch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author gueel
 */
public class ChessSystem {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMetch chessMetch = new ChessMetch();
        List<ChessPiece> captured = new ArrayList<>();
        
        while (true) {
            try {
                UI.clearScreen();
                UI.printMetch(chessMetch,captured);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition soucer = UI.readChessPosition(sc);
                
                boolean[][] possibleMoves = chessMetch.possibleMoves(soucer);
                UI.clearScreen();
                UI.printBoard(chessMetch.getPieces(),possibleMoves);

                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);
                ChessPiece capturedPiece = chessMetch.performChessMove(soucer, target);
                if(captured!= null){
                    captured.add(capturedPiece);
                }
                
            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }

    }
}
