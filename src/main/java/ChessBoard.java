import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ChessBoard extends JFrame {

     Piece[][] pieces = new Piece[8][8];
     JButton[][] buttons = new JButton[8][8];
     ActionListener actionListener = new TestActionListener();

     ImageIcon dot = new ImageIcon("dot_PNG29.png");
    Integer wKingY = 7;
    Integer wKingX = 4;
     Integer bKingY = 7;
     Integer bKingX = 4;
     Boolean colour = true;  //t = white, f = black

    public ChessBoard() {

        JFrame chessBoard = new JFrame();
        chessBoard.setSize(1000, 1000);
        chessBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chessBoard.setTitle("Chess");

        ImageIcon icon = new ImageIcon("1728594.png");
        chessBoard.setIconImage(icon.getImage());

        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setSize(525, 525);


        boolean t = false;

        StartPos();

        for (int y = 0; y < 8; y++) {
            t = !t;
            for (int x = 0; x < 8; x++) {
                buttons[y][x] = new JButton();
                if (t) {
                    if (x % 2 == 0) buttons[y][x].setBackground(Color.pink);
                    else buttons[y][x].setBackground(Color.white);
                } else {
                    if (x % 2 == 0) buttons[y][x].setBackground(Color.white);
                    else buttons[y][x].setBackground(Color.pink);
                }
                buttons[y][x].setText(Integer.toString(y * 10 + x));
                if (pieces[y][x] != null) buttons[y][x].setIcon(pieces[y][x].pieceIcon);
                buttons[y][x].addActionListener(actionListener);
                board.add(buttons[y][x]);
            }
        }
        chessBoard.add(board);
        chessBoard.pack();
        chessBoard.setVisible(true);

    }


     void DefColours() {

        boolean t = false;
        for (int y = 0; y < 8; y++) {
            t = !t;
            for (int x = 0; x < 8; x++) {
                if (t) {
                    if (x % 2 == 0) buttons[y][x].setBackground(Color.pink);
                    else buttons[y][x].setBackground(Color.white);
                } else {
                    if (x % 2 == 0) buttons[y][x].setBackground(Color.white);
                    else buttons[y][x].setBackground(Color.pink);
                }
            }
        }

    }




     class TestActionListener implements ActionListener {


        public static Boolean moveReady = false;
        public static Integer moveY;
        public static Integer moveX;


         public void actionPerformed(ActionEvent e) {

            String buttonText = ((JButton) e.getSource()).getText();

            int y = Integer.parseInt(buttonText) / 10;
            int x = Integer.parseInt(buttonText) % 10;


            if (buttons[y][x].getBackground() == Color.red || (moveReady && buttons[y][x].getIcon() == dot)) {

                Move(moveY, moveX, y, x);


            }


            if (moveReady && buttons[y][x].getIcon() != dot) {
                moveReady = false;
                ClearDots();
                DefColours();
            }


            if (pieces[y][x] != null && colour == pieces[y][x].colour ) {


                switch (pieces[y][x].name) {
                    case "pawn" -> pawnMove(y, x);
                    case "rook" -> rookMove(y, x);
                    case "knight" -> knightMove(y, x);
                    case "bishop" -> bishopMove(y, x);
                    case "queen" -> queenMove(y, x);
                    case "king" -> kingMove(y, x);
                }
                moveReady = true;
                moveY = y;
                moveX = x;


            }
        }
    }


    void pawnMove(int y, int x) {

        if (include(y - 1, x - 1)) {

            if (pieces[y - 1][x - 1] != null && checkShah(y, x, y - 1, x - 1) && pieces[y - 1][x - 1].colour != pieces[y][x].colour )
                buttons[y - 1][x - 1].setBackground(Color.red);


            if (include(y - 1, x + 1)) {

                if (pieces[y - 1][x + 1] != null && checkShah(y, x, y - 1, x + 1) && pieces[y - 1][x + 1].colour != pieces[y][x].colour)
                    buttons[y - 1][x + 1].setBackground(Color.red);
            }

            for (int i = 1; i <= 2; i++) {

                if (include(y - i, x) && (empty(y - i, x)) && checkShah(y, x, y - i, x)) buttons[y - i][x].setIcon(dot);
                else break;
            }

        }
    }

    void rookMove(int y, int x) {

        checkLong(y, x, 1, 0);
        checkLong(y, x, -1, 0);
        checkLong(y, x, 0, 1);
        checkLong(y, x, 0, -1);


    }

    void knightMove(int y, int x) {

        CheckKnigh(y, x, 2, 1);
        CheckKnigh(y, x, 2, -1);
        CheckKnigh(y, x, -2, 1);
        CheckKnigh(y, x, -2, -1);
        CheckKnigh(y, x, 1, 2);
        CheckKnigh(y, x, 1, -2);
        CheckKnigh(y, x, -1, 2);
        CheckKnigh(y, x, -1, -2);

    }

    void bishopMove(int y, int x) {

        checkLong(y, x, 1, 1);
        checkLong(y, x, 1, -1);
        checkLong(y, x, -1, 1);
        checkLong(y, x, -1, -1);

    }


    void CheckKnigh(int y, int x, int y1, int x1) {

        if (include(y + y1, x + x1) && empty(y + y1, x + x1) && checkShah(y, x, y + y1, x + x1)) buttons[y + y1][x + x1].setIcon(dot);
        if (include(y + y1, x + x1) && attackReady(y, x, y + y1, x + x1) && checkShah(y, x, y + y1, x + x1))
            buttons[y + y1][x + x1].setBackground(Color.red);

    }

    void queenMove(int y, int x) {

        checkLong(y, x, 1, 1);
        checkLong(y, x, 1, -1);
        checkLong(y, x, -1, 1);
        checkLong(y, x, -1, -1);
        checkLong(y, x, 1, 0);
        checkLong(y, x, -1, 0);
        checkLong(y, x, 0, 1);
        checkLong(y, x, 0, -1);
    }


    void kingMove(int y, int x) {

        kingCheckMove(y, x, 1, 0, pieces[y][x].colour);
        kingCheckMove(y, x, -1, 0, pieces[y][x].colour);
        kingCheckMove(y, x, 0, 1, pieces[y][x].colour);
        kingCheckMove(y, x, 0, -1, pieces[y][x].colour);
        kingCheckMove(y, x, 1, 1, pieces[y][x].colour);
        kingCheckMove(y, x, 1, -1, pieces[y][x].colour);
        kingCheckMove(y, x, -1, 1, pieces[y][x].colour);
        kingCheckMove(y, x, -1, -1, pieces[y][x].colour);

    }


    void kingCheckMove(int y, int x, int y1, int x1, Boolean color) {

        int i = 1;

        if (include(y + i * y1, x + i * x1)) {

            if ((empty(y + i * y1, x + i * x1)) && CheckKing(y + i * y1, x + i * x1, color))
                buttons[y + i * y1][x + i * x1].setIcon(dot);

            else {

                if (pieces[y + i * y1][x + i * x1] != null && pieces[y + i * y1][x + i * x1].colour != pieces[y][x].colour && CheckKing(y + i * y1, x + i * x1, color))
                    buttons[y + i * y1][x + i * x1].setBackground(Color.red);


            }

        }

    }


    void Move(int moveY, int moveX, int y, int x) {

        if (pieces[moveY][moveX].name.equals("king")) {

            if (colour) {

                wKingY = y;
                wKingX = x;

            } else {

                bKingY = y;
                bKingX = x;

            }

        }

        if ( ((colour && CheckKing(wKingY, wKingX, true)) || (!colour && CheckKing(bKingY, bKingX, false)))) {

        buttons[moveY][moveX].setIcon(null);
        buttons[y][x].setIcon(pieces[moveY][moveX].pieceIcon);
        pieces[y][x] = pieces[moveY][moveX];
        pieces[moveY][moveX] = null;

            if (pieces[y][x].name.equals("pawn") && y == 0) {

                if (pieces[y][x].colour) {

                    pieces[y][x] = new Piece("queen", true, new ImageIcon("queenW.png"));

                } else {

                    pieces[y][x] = new Piece("queen", false, new ImageIcon("queenW.png"));

                }
                buttons[y][x].setIcon((pieces[y][x].pieceIcon));

            }


            DefColours();
            Mirror();

            colour = !colour;

        } else ClearDots();


    }


    Boolean checkShah(int y, int x, int y1, int x1) {

        Piece piece = pieces[y][x];

        Piece piece1 = null;

      if (!empty(y1, x1))  piece1 = pieces[y1][x1];

        buttons[y][x].setIcon(null);
        pieces[y][x] = null;

        buttons[y1][x1].setIcon(piece.pieceIcon);
        pieces[y1][x1] = piece;

        boolean r = !((colour && !CheckKing(wKingY, wKingX, true)) || (!colour && !CheckKing(bKingY, bKingX, false)));

        buttons[y][x].setIcon(piece.pieceIcon);
        pieces[y][x] = piece;

        if (piece1 != null) {

            buttons[y1][x1].setIcon(piece1.pieceIcon);
            pieces[y1][x1] = piece1;
        } else {

            buttons[y1][x1].setIcon(null);
            pieces[y1][x1] = null;

        }

        return r;

    }

    void checkLong(int y, int x, int y1, int x1) {

        for (int i = 1; i <= 6; i++) {

            if (!include(y + i * y1, x + i * x1)) break;

            if ((empty(y + i * y1, x + i * x1)) && checkShah(y, x, y + i * y1, x + i * x1)) buttons[y + i * y1][x + i * x1].setIcon(dot);
            else {
                if (pieces[y + i * y1][x + i * x1] != null && checkShah(y, x, y + i * y1, x + i * x1) && pieces[y + i * y1][x + i * x1].colour != pieces[y][x].colour)
                    buttons[y + i * y1][x + i * x1].setBackground(Color.red);
                break;

            }
        }


    }

    void Mirror() {

        for (int y = 4; y < 8; y++) {
            for (int x = 0; x < 8; x++) {

                ImageIcon temp = (ImageIcon) buttons[y][x].getIcon();
                buttons[y][x].setIcon(buttons[7 - y][x].getIcon());
                buttons[7 - y][x].setIcon(temp);
                Piece tempP = pieces[y][x];
                pieces[y][x] = pieces[7 - y][x];
                pieces[7 - y][x] = tempP;

            }
        }

    }


     void ClearDots() {

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (buttons[y][x].getIcon() == dot) buttons[y][x].setIcon(null);
                if (Objects.equals(buttons[y][x].getBorder(), BorderFactory.createLineBorder(Color.red)))
                    buttons[y][x].setBorder(null);
            }
        }


    }

     Boolean CheckKing(int y, int x, Boolean color) {

        if (!stop(y, x, 1, 1, "bishop", color)) return false;
        if (!stop(y, x, 1, -1, "bishop", color)) return false;
        if (!stop(y, x, -1, 1, "bishop", color)) return false;
        if (!stop(y, x, -1, -1, "bishop", color)) return false;
        if (!stop(y, x, 1, 0, "rook", color)) return false;
        if (!stop(y, x, -1, 0, "rook", color)) return false;
        if (!stop(y, x, 0, 1, "rook", color)) return false;
        if (!stop(y, x, 0, -1, "rook", color)) return false;


        if (!shortStop(y, x, 1, 1, "king", color)) return false;
        if (!shortStop(y, x, 1, -1, "king", color)) return false;
        if (!shortStop(y, x, 1, 0, "king", color)) return false;
        if (!shortStop(y, x, -1, 1, "king", color)) return false;
        if (!shortStop(y, x, -1, -1, "king", color)) return false;
        if (!shortStop(y, x, -1, 0, "king", color)) return false;
        if (!shortStop(y, x, 0, -1, "king", color)) return false;
        if (!shortStop(y, x, 0, 1, "king", color)) return false;


        if (!shortStop(y, x, 1, -1, "king", color)) return false;
        if (!shortStop(y, x, 1, -1, "king", color)) return false;
        if (!shortStop(y, x, 0, -1, "king", color)) return false;
        if (!shortStop(y, x, -1, 1, "king", color)) return false;
        if (!shortStop(y, x, -1, -1, "king", color)) return false;
        if (!shortStop(y, x, 0, -1, "king", color)) return false;
        if (!shortStop(y, x, 0, -1, "king", color)) return false;
        if (!shortStop(y, x, 0, -1, "king", color)) return false;


        if (!shortStop(y, x, -1, 1, "pawn", color)) return false;
        if (!shortStop(y, x, -1, -1, "pawn", color)) return false;


        if (!shortStop(y, x, 2, 1, " knight", color)) return false;
        if (!shortStop(y, x, 2, -1, "knight", color)) return false;
        if (!shortStop(y, x, -2, 1, "knight", color)) return false;
        if (!shortStop(y, x, -2, -1, "knight", color)) return false;
        if (!shortStop(y, x, 1, -2, "knight", color)) return false;
        if (!shortStop(y, x, 1, 2, "knight", color)) return false;
        if (!shortStop(y, x, -1, -2, "knight", color)) return false;
        return shortStop(y, x, -1, 2, "knight", color);
    }


    Boolean shortStop(int y, int x, int y1, int x1, String p, Boolean color) {

        int i = 1;

        return !include(y + i * y1, x + i * x1) || empty(y + i * y1, x + i * x1) || color == pieces[y + i * y1][x + i * x1].colour || !pieces[y + i * y1][x + i * x1].name.equals(p);

    }


    Boolean stop(int y, int x, int y1, int x1, String p, Boolean color) {

        for (int i = 1; i <= 7; i++) { // y+

            if (!include(y + i * y1, x + i * x1)) break;

            if (!empty(y + i * y1, x + i * x1)) {

                return pieces[y + i * y1][x + i * x1].colour == color || (!pieces[y + i * y1][x + i * x1].name.equals(p) &&
                        !pieces[y + i * y1][x + i * x1].name.equals("queen"));
            }

        }

        return true;
    }

    Boolean empty(int y, int x) {

        return (pieces[y][x] == null);

    }

    Boolean include(int y, int x) {

        return !(y > 7 || y < 0 || x > 7 || x < 0);

    }

     Boolean attackReady(int y, int x, int y1, int x1) {

        return (pieces[y1][x1] != null && pieces[y1][x1].colour != pieces[y][x].colour);
    }

     void StartPos() {

        for (int i = 0; i < 8; i++) {

            pieces[1][i] = new Piece("pawn", false, new ImageIcon("pawnB.png"));
            pieces[6][i] = new Piece("pawn", true, new ImageIcon("pawnW.png"));

        }

        pieces[0][0] = pieces[0][7] = new Piece("rook", false, new ImageIcon("rookB.png"));
        pieces[7][0] = pieces[7][7] = new Piece("rook", true, new ImageIcon("rookW.png"));
        pieces[0][1] = pieces[0][6] = new Piece("knight", false, new ImageIcon("knightB.png"));
        pieces[7][1] = pieces[7][6] = new Piece("knight", true, new ImageIcon("knightW.png"));
        pieces[0][2] = pieces[0][5] = new Piece("bishop", false, new ImageIcon("bishopB.png"));
        pieces[7][2] = pieces[7][5] = new Piece("bishop", true, new ImageIcon("bishopW.png"));
        pieces[0][3] = new Piece("queen", false, new ImageIcon("queenB.png"));
        pieces[7][3] = new Piece("queen", true, new ImageIcon("queenW.png"));
        pieces[0][4] = new Piece("king", false, new ImageIcon("kingB.png"));
        pieces[7][4] = new Piece("king", true, new ImageIcon("kingW.png"));
    }


}






