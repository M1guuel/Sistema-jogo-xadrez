/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package chess;

import boardgame.BoardExeception;

/**
 *
 * @author gueel
 */
public class ChessException extends BoardExeception{
    
    public ChessException (String msg){
        super(msg);
    }
}
