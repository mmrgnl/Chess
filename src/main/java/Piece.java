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



  /*        public Piece pawnW1 = new Piece("pawn", 2, 0, 0, false, true, new ImageIcon("pawnW.png"));


  public static Piece pawnW2 = pawnW1;
    public Piece pawnW3 = pawnW1;
    public Piece pawnW4 = pawnW1;
    public Piece pawnW5 = pawnW1;
    public Piece pawnW6 = pawnW1;
    public Piece pawnW7 = pawnW1;
    public Piece pawnW8 = pawnW1;
    public Piece pawnB1 = new Piece("pawn", 2, 0, 0, false, false, new ImageIcon("pawnB.png"));
    public Piece pawnB2 = pawnB1;
    public Piece pawnB3 = pawnB1;
    public Piece pawnB4 = pawnB1;
    public Piece pawnB5 = pawnB1;
    public Piece pawnB6 = pawnB1;
    public Piece pawnB7 = pawnB1;
    public Piece pawnB8 = pawnB1;
    public Piece rookW1 =  new Piece("rook", 8, 8, 0, false, false, new ImageIcon("rookW.png"));
    public Piece rookW2 = rookW1;
    public Piece rookB1 =  new Piece("rook", 8, 8, 0, false, false, new ImageIcon("rookB.png"));
    public Piece rookB2 = rookB1;
    public Piece knightW1 =  new Piece("knight", 0, 0, 0, true, false, new ImageIcon("knightW.png"));
    public Piece knightW2 = knightW1;
    public Piece knightB1 = new Piece("knight", 0, 0, 0, true, false, new ImageIcon("knightB.png"));
    public Piece knightB2 = knightB1;
    public Piece bishopW1 =  new Piece("bishop", 0, 0, 8, false, false, new ImageIcon("bishopW.png"));
    public Piece BishopW2 = bishopW1;
    public Piece bishopB1 =  new Piece("bishop", 0, 0, 8, false, false, new ImageIcon("bishopB.png"));
    public Piece BishopB2 = bishopB1;
    public Piece queenW =  new Piece("queen", 8, 8, 8, false, false, new ImageIcon("queenW.png"));
    public Piece queenB =  new Piece("queen", 8, 8, 8, false, false, new ImageIcon("queenB.png"));
    public Piece kingW =  new Piece("king", 1, 1, 1, false, false, new ImageIcon("kingW.png"));
    public Piece kingB =  new Piece("king", 1, 1, 1, false, false, new ImageIcon("kingB.png"));

*/
}

