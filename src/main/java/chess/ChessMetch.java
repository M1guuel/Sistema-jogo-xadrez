/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author gueel
 */
public class ChessMetch {

    private int turno;
    private Color jogador;
    private Board board;
    private boolean check;
    private List<Piece> pieceOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMetch() {
        board = new Board(8, 8);
        turno = 1;
        jogador = Color.WHITE;
        initialSetup();
    }

    public int getTurno() {
        return turno;
    }

    public Color getJogador() {
        return jogador;
    }
    public boolean getCheck(){
        return check;
        
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
    private Color opponent (Color color){
        return (color == color.WHITE)? color.BLACK : color.WHITE;
    }
    private ChessPiece king(Color color){
        List<Piece> list = pieceOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list){
          if (p instanceof King){
              return (ChessPiece) p;
          }  
        }
       throw new IllegalStateException("NÃO TEM NO TABULEIRO KING " + color);
    }
    private boolean testCheck(Color color){
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = pieceOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces){
            boolean [][] mat  = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]){
            return true;
        }
        }
        return false;
    }

    private void nextTurn() {
        turno++;
        jogador = (jogador == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();

    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition tangetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = tangetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);
        if (testCheck(jogador)){
            undoMove(source, target, capturedPiece);
            throw new ChessException("ATENÇÃO !! VOÇE NÃO PODE SE COLOCAR EM CHECK");
        }
        check = (testCheck(opponent(jogador)))? true: false;
        nextTurn();
        return (ChessPiece) capturedPiece;

    }

    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);
        if(capturedPiece != null){
            pieceOnTheBoard.remove(capturedPiece);
          this.capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }
    private void undoMove (Position source, Position target,Piece captured ){
        Piece p = board.removePiece(target);
        board.placePiece(p,source);
        if (capturedPieces!=null){
            board.placePiece(captured, target);
            capturedPieces.remove(captured);
            pieceOnTheBoard.add(captured);
        }
    }

    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("NÃO EXISTE PEÇA NA POSIÇÃO ESCOLHIDA ");
        }
        if (jogador != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("ESSA PEÇA NÃO E SUA !!!");
        }
        if (!board.piece(position).isThereAnyPossibleMoves()) {
            throw new ChessException("NÃO EXISTE MOVIMENTO POSSIVEIS PARA A PEÇA ESCOLHIDA ");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("A PEÇA ESCOLHIDA NÃO PODEE SE MOVER PARA A POSIÇAO DE DESTINO !");
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        pieceOnTheBoard.add(piece);
    }

    private void initialSetup() {
        placeNewPiece('c', 2, new Rook(Color.WHITE, board));
        placeNewPiece('d', 2, new Rook(Color.WHITE, board));
        placeNewPiece('e', 2, new Rook(Color.WHITE, board));
        placeNewPiece('e', 1, new Rook(Color.WHITE, board));
        placeNewPiece('d', 1, new King(Color.WHITE, board));

        placeNewPiece('c', 7, new Rook(Color.BLACK, board));
        placeNewPiece('c', 8, new Rook(Color.BLACK, board));
        placeNewPiece('d', 7, new Rook(Color.BLACK, board));
        placeNewPiece('e', 7, new Rook(Color.BLACK, board));
        placeNewPiece('e', 8, new Rook(Color.BLACK, board));
        placeNewPiece('d', 8, new King(Color.BLACK, board));
    }
}
