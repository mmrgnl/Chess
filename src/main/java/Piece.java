import javax.swing.*;


public class Piece {

    enum Name {
        PAWN,
        ROOK,
        KNIGHT,
        BISHOP,
        QUEEN,
        KING
    }


    Boolean colour; //t = white, f = black
    Boolean fistMove;
    ImageIcon pieceIcon;
    Name name;

    public Piece(Name name, Boolean colour, Boolean fistMove, ImageIcon pieceIcon){
        this.name = name;
        this.colour = colour;
        this.fistMove = fistMove;
        this.pieceIcon = pieceIcon;
    }
}

