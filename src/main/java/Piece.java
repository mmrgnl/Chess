import javax.swing.*;

public class Piece {

    String name;
    Boolean colour; //t = white, f = black
    Boolean fistMove;
    ImageIcon pieceIcon;

    public Piece(String name, Boolean colour, Boolean fistMove, ImageIcon pieceIcon){
        this.name = name;
        this.colour = colour;
        this.fistMove = fistMove;
        this.pieceIcon = pieceIcon;
    }
}

