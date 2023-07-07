/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queem;
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
    private boolean checkMatch;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;

    private List<Piece> pieceOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMetch() {
        board = new Board(8, 8);
        turno = 1;
        jogador = Color.WHITE;
        initialSetup();
    }
 public ChessPiece getPromoted(){
     return promoted;
 }
    public int getTurno() {
        return turno;
    }

    public Color getJogador() {
        return jogador;
    }

    public boolean getCheck() {
        return check;

    }

    public boolean getCheckMatch() {
        return checkMatch;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
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

    private Color opponent(Color color) {
        return (color == color.WHITE) ? color.BLACK : color.WHITE;
    }

    private ChessPiece king(Color color) {
        List<Piece> list = pieceOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("NÃO TEM NO TABULEIRO KING " + color);
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponentPieces = pieceOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMatch(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = pieceOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRow(); i++) {
                for (int j = 0; j < board.getColumn(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }

            }

        }
        return true;
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
        
        if (testCheck(jogador)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("ATENÇÃO !! VOÇE NÃO PODE SE COLOCAR EM CHECK");
        }
        ChessPiece movePiece = (ChessPiece) board.piece(target);
        
        // #specialmove promotion
        promoted = null;
        if ( movePiece instanceof Pawn){
            if((movePiece.getColor() == Color.WHITE && target.getRow()== 0)|| (movePiece.getColor() == Color.BLACK && target.getRow()== 7)){
                promoted = (ChessPiece)board.piece(target);
                promoted = replacePromotedPiece("Q");
            }
        }
        
        check = (testCheck(opponent(jogador))) ? true : false;
        if (testCheckMatch(opponent(jogador))) {
            checkMatch = true;
            System.out.println("Fim de jogo");
        } else {
            nextTurn();
        }
        // #specialmove en passant
        if (movePiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movePiece;
        } else {
            enPassantVulnerable = null;
        }
        return (ChessPiece) capturedPiece;

    }
    public ChessPiece replacePromotedPiece(String type){
        if (promoted == null){
            throw new IllegalStateException("NÃO A PEÇA PARA SER PROMOVIDA");
        }
        if(!type.equals("B")&& !type.equals("N") && !type.equals("R")&& !type.equals("Q")){
           return promoted;
        }
        Position pos = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        pieceOnTheBoard.remove(p);
        
        ChessPiece newPiece = newPiece(type,promoted.getColor());
        board.placePiece(newPiece,pos);
        pieceOnTheBoard.add(newPiece);
         return newPiece;
    }
    private ChessPiece newPiece(String type,Color color){
        if (type.equals("B"))return new Bishop(color, board);
        if (type.equals("N"))return new Knight(color, board);
        if (type.equals("Q"))return new Queem(color, board);
        return new Rook(color, board);
    }

    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPiece != null) {
            pieceOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        // #Specialmove castling kingsazi rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position TargetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = ((ChessPiece) board.removePiece(sourceT));
            board.placePiece(rook, target);
            rook.increaseMoveCount();
        }
        // #Specialmove castling queensazi rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position TargetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = ((ChessPiece) board.removePiece(sourceT));
            board.placePiece(rook, target);
            rook.increaseMoveCount();
        }
        // # specialmove en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                pieceOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece captured) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placePiece(p, source);
        if (captured != null) {
            board.placePiece(captured, target);
            capturedPieces.remove(captured);
            pieceOnTheBoard.add((ChessPiece) captured);
        }
        // #Specialmove castling kingsazi rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position TargetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = ((ChessPiece) board.removePiece(TargetT));
            board.placePiece(rook, sourceT);
            rook.increaseMoveCount();
        }
        // #Specialmove castling queensazi rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position TargetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = ((ChessPiece) board.removePiece(TargetT));
            board.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }
        // # specialmove en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && captured == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece) board.removePiece(target);
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                } else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);
            }
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
        placeNewPiece('a', 1, new Rook(Color.WHITE, board));
        placeNewPiece('b', 1, new Knight(Color.WHITE, board));
        placeNewPiece('c', 1, new Bishop(Color.WHITE, board));
        placeNewPiece('d', 1, new Queem(Color.WHITE, board));
        placeNewPiece('e', 1, new King(Color.WHITE, board, this));
        placeNewPiece('f', 1, new Bishop(Color.WHITE, board));
        placeNewPiece('g', 1, new Knight(Color.WHITE, board));
        placeNewPiece('h', 1, new Rook(Color.WHITE, board));
          placeNewPiece('a', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('b', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('c', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('d', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('e', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('f', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('g', 2, new Pawn(Color.WHITE, board, this));
        placeNewPiece('h', 2, new Pawn(Color.WHITE, board, this));

        placeNewPiece('a', 8, new Rook(Color.BLACK, board));
        placeNewPiece('b', 8, new Knight(Color.BLACK, board));
        placeNewPiece('c', 8, new Bishop(Color.BLACK, board));
        placeNewPiece('d', 8, new Queem(Color.BLACK, board));
        placeNewPiece('e', 8, new King(Color.BLACK, board, this));
        placeNewPiece('f', 8, new Bishop(Color.BLACK, board));
        placeNewPiece('g', 8, new Knight(Color.BLACK, board));
        placeNewPiece('h', 8, new Rook(Color.BLACK, board));
       placeNewPiece('a', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('b', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('c', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('d', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('e', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('f', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('g', 7, new Pawn(Color.BLACK, board, this));
        placeNewPiece('h', 7, new Pawn(Color.BLACK, board, this));
    }
}
