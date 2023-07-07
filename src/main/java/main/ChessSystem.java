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

        while (!chessMetch.getCheckMatch()) {
            try {
                UI.clearScreen();

                UI.printMetch(chessMetch, captured);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition soucer = UI.readChessPosition(sc);

                boolean[][] possibleMoves = chessMetch.possibleMoves(soucer);
                UI.clearScreen();
                UI.printBoard(chessMetch.getPieces(), possibleMoves);
                System.out.println();
                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(sc);
                ChessPiece capturedPiece = chessMetch.performChessMove(soucer, target);

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }
                if (chessMetch.getPromoted() != null) {
                    System.out.println("ENTRE COM A PEÇA PROMOVIDA (B/N/R/Q): ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
                        System.out.println("ERRO: VALORES VALIDOS (B/N/R/Q)! ");
                      type = sc.nextLine().toUpperCase();
                    }
                    chessMetch.replacePromotedPiece(type);
                }

            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();

            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMetch(chessMetch, captured);

    }
}
