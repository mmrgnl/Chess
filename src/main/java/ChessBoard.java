import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ChessBoard extends JFrame {

    public  Piece[][] pieces = new Piece[8][8];
    public  JButton[][] buttons = new JButton[8][8];
    public ActionListener actionListener = new TestActionListener();
    public  ImageIcon dot = new ImageIcon("dot_PNG29.png");


    public ChessBoard() {

        JFrame chessBoard = new JFrame();
        chessBoard.setSize(1000, 1000);
        chessBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chessBoard.setTitle("Chess");

        ImageIcon icon = new ImageIcon("1728594.png");
        chessBoard.setIconImage(icon.getImage());

        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setSize(525,525);


        boolean t = false;

       StartPos();

        for (int y = 0; y < 8; y++) {

            t = !t;

            for (int x = 0; x < 8; x++) {

                buttons[y][x]= new JButton();


                if (t) {

                    if (x % 2 == 0) buttons[y][x].setBackground(Color.pink);
                    else buttons[y][x].setBackground(Color.white);

                } else {

                    if (x % 2 == 0) buttons[y][x].setBackground(Color.white);
                    else buttons[y][x].setBackground(Color.pink);

                }
               buttons[y][x].setText(Integer.toString(y*10+x));

                if (pieces[y][x] != null ) buttons[y][x].setIcon(pieces[y][x].pieceIcon);
                buttons[y][x].addActionListener(actionListener);
                board.add(buttons[y][x]);

            }

            //board.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);




        }






       chessBoard.add(board);
       chessBoard.pack();
       chessBoard.setVisible(true);



    }




    public  void DefColours(){

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


    public class TestActionListener implements ActionListener {

        public static Boolean moveReady = false;
        public static Integer moveY;
        public static Integer moveX;
        public static Boolean colour = true;  //t = white, f = black

        public void actionPerformed(ActionEvent e) {

            String buttonText = ((JButton) e.getSource()).getText();

            int y = Integer.parseInt(buttonText) / 10;
            int x = Integer.parseInt(buttonText) % 10;


            if (buttons[y][x].getBackground() == Color.red || (moveReady && buttons[y][x].getIcon() == dot)) {

                Move(moveY, moveX, y, x);
                colour = !colour;

            }


            if (moveReady && buttons[y][x].getIcon() != dot) {
                moveReady = false;
                ClearDots();
                DefColours();
            }


            if (pieces[y][x] != null && colour == pieces[y][x].colour) {

                System.out.println(x);


                switch (pieces[y][x].name) {

                    case "pawn":

                        if (include(y - 1, x - 1)) {

                            if (pieces[y - 1][x - 1] != null && pieces[y - 1][x - 1].colour != pieces[y][x].colour)
                                buttons[y - 1][x - 1].setBackground(Color.red);

                        }


                        if (include(y - 1, x + 1)) {

                            if (pieces[y - 1][x + 1] != null && pieces[y - 1][x + 1].colour != pieces[y][x].colour)
                                buttons[y - 1][x + 1].setBackground(Color.red);
                        }

                        for (int i = 1; i <= 2; i++) {

                            if ((empty(y - i, x))) buttons[y - i][x].setIcon(dot);
                            else break;
                        }

                        moveReady = true;
                        moveY = y;
                        moveX = x;

                        break;

                    case "rook":

                      checkLong(y, x, 1, 0);
                      checkLong(y, x, -1, 0);
                      checkLong(y, x, 0, 1);
                      checkLong(y, x, 0, -1);

                        moveReady = true;
                        moveY = y;
                        moveX = x;

                        break;

                    case "knight":

                       knightMove(y, x, 2, 1);
                       knightMove(y, x, 2, -1);
                       knightMove(y, x, -2, 1);
                       knightMove(y, x, -2, -1);

                        moveReady = true;
                        moveY = y;
                        moveX = x;
                        break;


                    case "bishop":

                        checkLong(y, x, 1, 1);
                        checkLong(y, x, 1, -1);
                        checkLong(y, x, -1, 1);
                        checkLong(y, x, -1, -1);

                        moveReady = true;
                        moveY = y;
                        moveX = x;
                        break;

                    case "queen":

                        checkLong(y, x, 1, 1);
                        checkLong(y, x, 1, -1);
                        checkLong(y, x, -1, 1);
                        checkLong(y, x, -1, -1);

                        checkLong(y, x, 1, 0);
                        checkLong(y, x, -1, 0);
                        checkLong(y, x, 0, 1);
                        checkLong(y, x, 0, -1);

                        moveReady = true;
                        moveY = y;
                        moveX = x;
                        break;

                    case "king":

                        kingMove(y, x, 1, 0);
                        kingMove(y, x, -1, 0);
                        kingMove(y, x, 0, 1);
                        kingMove(y, x, 0, -1);
                        kingMove(y, x, 1, 1);
                        kingMove(y, x, 1, -1);
                        kingMove(y, x, -1, 1);
                        kingMove(y, x, 1, -1);

                        moveReady = true;
                        moveY = y;
                        moveX = x;
                        break;
                }




            }
        }
    }

    void kingMove(int y, int x, int y1, int x1) {

        int i = 1;

        if (include(y + i * y1, x + i * x1)) {

            if ((empty(y + i * y1, x + i * x1)) && CheckKing(y + i * y1, x + i * x1)) buttons[y + i * y1][x + i * x1].setIcon(dot);

            else {

                if (pieces[y + i * y1][x + i * x1] != null && pieces[y + i * y1][x + i * x1].colour != pieces[y][x].colour)
                    buttons[y + i * y1][x + i * x1].setBackground(Color.red);


            }

        }

    }

    void Move(Integer moveY, Integer moveX, Integer y, Integer x) {

        buttons[moveY][moveX].setIcon(null);
        buttons[y][x].setIcon(pieces[moveY][moveX].pieceIcon);
        pieces[y][x] = pieces[moveY][moveX];
        pieces[moveY][moveX] = null;

        ClearDots();
        DefColours();
        Mirror();

    }

    public  void knightMove(int y, int x, int y1, int x1) {

        if (include(y + y1, x + x1) && empty(y + y1, x + x1)) buttons[y + y1][x + x1].setIcon(dot);
        if (include(y + y1, x + x1) && attackReady(y, x, y + y1, x + x1))
            buttons[y + y1][x + x1].setBackground(Color.red);

    }

    public  void checkLong(int y, int x, int y1, int x1) {

        for (int i = 1; i <= 7; i++) {

            if (!include(y + i * y1, x + i * x1)) break;

                if ((empty(y + i * y1, x + i * x1))) buttons[y + i * y1][x + i * x1].setIcon(dot);
                else {
                    if (pieces[y + i * y1][x + i * x1] != null && pieces[y + i * y1][x + i * x1].colour != pieces[y][x].colour)
                        buttons[y + i * y1][x + i * x1].setBackground(Color.red);
                    break;

            }
        }


    }

    public  void Mirror() {

        for (int y = 4; y < 8; y++) {
            for (int x = 0; x < 8; x++) {

                ImageIcon mir = (ImageIcon) buttons[y][x].getIcon();

                buttons[y][x].setIcon(buttons[7 - y][x].getIcon());
                buttons[7 - y][x].setIcon(mir);

                Piece tempP = pieces [y][x];
                pieces[y][x] = pieces[7 - y][x];
                pieces[7 - y][x] = tempP;

            }
        }

        }


    public  void ClearDots() {

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (buttons[y][x].getIcon() == dot) buttons[y][x].setIcon(null);
                if (Objects.equals(buttons[y][x].getBorder(), BorderFactory.createLineBorder(Color.red))) buttons[y][x].setBorder(null);
            }
        }


    }

    public Boolean CheckKing(int y, int x) {

        if (!stop(y, x, 1, 1, "bishop")) return false;
        if (!stop(y, x, 1, -1, "bishop")) return false;
        if (!stop(y, x, -1, 1, "bishop")) return false;
        if (!stop(y, x, -1, -1, "bishop")) return false;
        if (!stop(y, x, 1, 0, "rook")) return false;
        if (!stop(y, x, -1, 0, "rook")) return false;
        if (!stop(y, x, 0, 1, "rook")) return false;
        if (!stop(y, x, 0, -1, "rook")) return false;

        if (include(y - 1, x - 1))
            if (pieces[y - 1][x - 1] != null && pieces[y - 1][x - 1].colour != pieces[y][x].colour) return false;

        if (include(y - 1, x + 1))
            return pieces[y - 1][x + 1] == null || pieces[y - 1][x + 1].colour == pieces[y][x].colour;



        return true;
    }


    Boolean stop(int y, int x, int y1, int x1, String p){

        for (int i = 1; i <= 7; i++) { // y+

            if (include(y + i * y1, x + i * x1)) break;

            if (!empty(y + i * y1, x + i * x1) && pieces[y + i * y1][x + i * x1].colour
                    != pieces[y][x].colour && ( pieces[y + i * y1][x + i * x1].name == p ||
                    pieces[y + i * y1][x + i * x1].name == "queen")) return false;
            else break;

        }

        return true;
    }

    //
    public  Boolean empty(int y, int x){

       return (pieces[y][x] == null);

    }

    public  Boolean include (int y, int x){

        return !(y > 7 || y < 0 || x > 7 || x < 0);

    }

    public  Boolean attackReady(int y, int x, int y1, int x1) {

        return  (pieces[y1][x1] != null && pieces[y1][x1].colour != pieces[y][x].colour);
    }

    public void StartPos(){

       for (int i = 0; i < 8; i++) {

            pieces[1][i] = new Piece("pawn", 2, 0, 0, false, false, new ImageIcon("pawnB.png"));
            pieces[6][i] = new Piece("pawn", 2, 0, 0, false, true, new ImageIcon("pawnW.png"));

       }

        pieces[0][0] = pieces[0][7] = new Piece("rook", 8, 8, 0, false, false, new ImageIcon("rookB.png"));
        pieces[7][0] = pieces[7][7] = new Piece("rook", 8, 8, 0, false, true, new ImageIcon("rookW.png"));
        pieces[0][1] = pieces[0][6] = new Piece("knight", 0, 0, 0, true, false, new ImageIcon("knightB.png"));
        pieces[7][1] = pieces[7][6] = new Piece("knight", 0, 0, 0, true, true, new ImageIcon("knightW.png"));
        pieces[0][2] = pieces[0][5] = new Piece("bishop", 0, 0, 8, false, false, new ImageIcon("bishopB.png"));
        pieces[7][2] = pieces[7][5] =  new Piece("bishop", 0, 0, 8, false, true, new ImageIcon("bishopW.png"));
        pieces[0][3] =  new Piece("queen", 8, 8, 8, false, false, new ImageIcon("queenB.png"));
        pieces[7][3] =  new Piece("queen", 8, 8, 8, false, true, new ImageIcon("queenW.png"));
        pieces[0][4] = new Piece("king", 1, 1, 1, false, false, new ImageIcon("kingB.png"));
        pieces[7][4] = new Piece("king", 1, 1, 1, false, true, new ImageIcon("kingW.png"));
    }
}




