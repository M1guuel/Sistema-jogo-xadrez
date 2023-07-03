package boardgame;

/**
 *
 * @author gueel
 */
public class Board {

    private int row;
    private int column;
    private Piece[][] pieces;

    public Board(int row, int column) {
        if(row <1 && column <1){
            throw new BoardExeception("Erro ao criar Tabuleiro: Precisar haver Pelo menos 1 linha e 1 coluna");
        }
        this.row = row;
        this.column = column;
        this.pieces = new Piece[row][column];
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public Piece piece(int row, int column) {
        if(!positionExists(row,column)){
            throw new BoardExeception("POSIÇÃO NÃO EXISTE");
        }
        return pieces[row][column];
    }

    public Piece piece(Position position) {
         if(!positionExists(position)){
            throw new BoardExeception("POSIÇÃO NÃO EXISTE");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position) {
        if(thereIsAPiece(position)){
            throw new BoardExeception("JÁ EXISTE PEÇA NESSA POSIÇÃO "+position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }
    
    public boolean positionExists(int row, int column){
       return row >= 0 && row < this.row && column >=0 && column <this.column;
        
    }
    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn());
    }
      public boolean thereIsAPiece(Position position ){
           if(!positionExists(position)){
            throw new BoardExeception("POSIÇÃO NÃO EXISTE");
        }
         return piece(position)!= null;
      }
}
