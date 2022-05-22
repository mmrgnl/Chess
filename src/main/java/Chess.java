import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
public class Chess extends JFrame {

    static Piece[][] pieces = new Piece[8][8];
    static JButton[][] buttons = new JButton[8][8];
    static ActionListener actionListener = new TestActionListener();

    static ImageIcon dot = new ImageIcon("dot_PNG29.png");
    static Integer wKingY = 7; //позиции короля
    static Integer wKingX = 4;
    static Integer bKingY = 7;
    static Integer bKingX = 4;
    static Boolean colour = true;  //t = white, f = black
    static Integer wTime = 1200;
    static Integer bTime = 1200;
    static JLabel label = new JLabel("...");

    public Chess() {

        JFrame choseFrame = new JFrame(); //панель для получения кода расположения фигур
        JLabel enterTheFen =  new JLabel("Enter the FEN");
        JButton submitFEN = new JButton("Submit");
        JTextField textFEN = new JTextField(64);
        textFEN.setHorizontalAlignment(JTextField.CENTER);
        submitFEN.addActionListener(e -> { //для стандартной расстановки rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR
            FEN(textFEN.getText());
            System.out.println(textFEN.getText());
            startGame();
            choseFrame.dispose();

        });
        JPanel p = new JPanel();
        p.add(textFEN);
        p.add(submitFEN);
        p.add(enterTheFen);
        ImageIcon icon = new ImageIcon("1728594.png");
        choseFrame.setTitle("Chose");
        choseFrame.setIconImage(icon.getImage());
        choseFrame.setLayout(new BorderLayout(20,0));
        choseFrame.add(p,BorderLayout.CENTER );
        choseFrame.setSize(500, 300);
        choseFrame.setVisible(true);

    }


    static void startGame() { //создание окна игры, расстановка фигур

        JFrame chessBoard = new JFrame();
        chessBoard.setSize(1000, 1000);
        chessBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chessBoard.setTitle("Chess");
        ImageIcon icon = new ImageIcon("1728594.png");
        chessBoard.setIconImage(icon.getImage());
        JPanel board = new JPanel(new GridLayout(8, 8));
        board.setSize(525, 525);

        JPanel timer = new JPanel();
        timer.setSize(525, 100);
        Timer wTimer = new Timer(1000, e -> {
            if (colour) {
                if (wTime > 0) {
                    wTime--;
                    label.setText(wTime / 60 + ":" + wTime % 60);
                } else System.out.println("Black wins");
            } else {
                if (bTime > 0) {

                    bTime--;
                    label.setText(bTime / 60 + ":" + bTime % 60);
                } else System.out.println("White wins");
            }
        });

        boolean t = false;

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

                if (pieces[y][x] != null) buttons[y][x].setIcon(pieces[y][x].pieceIcon);
                buttons[y][x].addActionListener(actionListener);
                board.add(buttons[y][x]);
            }
        }
        timer.add(label);
        wTimer.start();
        chessBoard.add(timer, BorderLayout.SOUTH);
        chessBoard.add(board);
        chessBoard.pack();
        chessBoard.setVisible(true);

    }



    static void FEN(String s) { //обработка кода расположения фигур
        int y = 0;
        int x;
        String[] symbols = s.split("/");
        for (String symbol : symbols) {
            int x1 = 0;
            for (x = 0; x < 8; x++) {
                if (x < symbol.length()) {
                    switch (symbol.charAt(x)) {
                        case ('p') -> pieces[y][x1] = new Piece("pawn", false, true,
                                new ImageIcon("pawnB.png"));
                        case ('r') -> pieces[y][x1] = new Piece("rook", false, true,
                                new ImageIcon("rookB.png"));
                        case ('n') -> pieces[y][x1] = new Piece("knight", false, null,
                                new ImageIcon("knightB.png"));
                        case ('b') -> pieces[y][x1] = new Piece("bishop", false, null,
                                new ImageIcon("bishopB.png"));
                        case ('q') -> pieces[y][x1] = new Piece("queen", false, null,
                                new ImageIcon("queenB.png"));
                        case ('k') -> pieces[y][x1] = new Piece("king", false, true,
                                new ImageIcon("kingB.png"));
                        case ('P') -> pieces[y][x1] = new Piece("pawn", true, true,
                                new ImageIcon("pawnW.png"));
                        case ('R') -> pieces[y][x1] = new Piece("rook", true, true,
                                new ImageIcon("rookW.png"));
                        case ('N') -> pieces[y][x1] = new Piece("knight", true, null,
                                new ImageIcon("knightW.png"));
                        case ('B') -> pieces[y][x1] = new Piece("bishop", true, null,
                                new ImageIcon("bishopW.png"));
                        case ('Q') -> pieces[y][x1] = new Piece("queen", true, null,
                                new ImageIcon("queenW.png"));
                        case ('K') -> pieces[y][x1] = new Piece("king", true, true,
                                new ImageIcon("kingW.png"));
                        case ('2') -> x1 = x1 + 1;
                        case ('3') -> x1 = x1 + 2;
                        case ('4') -> x1 = x1 + 3;
                        case ('5') -> x1 = x1 + 4;
                        case ('6') -> x1 = x1 + 5;
                        case ('7') -> x1 = x1 + 6;
                        case ('8') -> x1 = x1 + 7;

                    }
                    x1++;
                }
            }
            y++;
        }
    }


    static void DefColours() { //исправление цветов
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

    static Boolean moveReady = false;
    static Integer moveY;
    static Integer moveX;

    static class TestActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) { //обработка кнопок
            JButton o = (JButton) e.getSource();
            for (int y = 0; y < 8; y++) { //поиск нажатой кнопки
                for (int x = 0; x < 8; x++) {
                    if (buttons[y][x] == o) {
                        actionMove(y, x);
                        break;
                    }
                }
            }

        }
    }

    static void actionMove(int y, int x) {
        if (buttons[y][x].getBackground() == Color.red || (moveReady && buttons[y][x].getIcon() == dot)) {
            move(moveY, moveX, y, x);
        }
        if (moveReady && buttons[y][x].getIcon() != dot) {
            moveReady = false;
            clearDots();
            DefColours();
        }
        if (pieces[y][x] != null && colour == pieces[y][x].colour) {
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

    static boolean endGame() { //возвращает истину, когда нет доступных ходов
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (pieces[y][x] != null && pieces[y][x].colour == colour) {
                    switch (pieces[y][x].name) {
                        case "pawn" -> pawnMove(y, x);
                        case "rook" -> rookMove(y, x);
                        case "knight" -> knightMove(y, x);
                        case "bishop" -> bishopMove(y, x);
                        case "queen" -> queenMove(y, x);
                        case "king" -> kingMove(y, x);
                    }
                }
            }
        }

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (buttons[y][x].getIcon() == dot || buttons[y][x].getBackground() == Color.red) {
                    if (!colour) {
                        DefColours();
                        clearDots();
                        return false;
                    }
                }
            }
        }
        DefColours();
        clearDots();
        return true;
    }


    static void pawnMove(int y, int x) { //просчет атаки пешки
        if (include(y - 1, x - 1)) {
            if (pieces[y - 1][x - 1] != null && checkShah(y, x, y - 1, x - 1)
                    && pieces[y - 1][x - 1].colour != pieces[y][x].colour)
                buttons[y - 1][x - 1].setBackground(Color.red);

        }
        if (include(y - 1, x + 1)) {
            if (pieces[y - 1][x + 1] != null && checkShah(y, x, y - 1, x + 1)
                    && pieces[y - 1][x + 1].colour != pieces[y][x].colour)
                buttons[y - 1][x + 1].setBackground(Color.red);
        }
        if (include(y - 1, x) && (empty(y - 1, x)) && checkShah(y, x, y - 1, x)) buttons[y - 1][x].setIcon(dot);
        if (pieces[y][x].fistMove && include(y - 2, x) && (empty(y - 2, x)) && checkShah(y, x, y - 2, x))
            buttons[y - 2][x].setIcon(dot);

    }


    static void rookMove(int y, int x) { // просчет атаки ладьи
        checkLong(y, x, 1, 0);
        checkLong(y, x, -1, 0);
        checkLong(y, x, 0, 1);
        checkLong(y, x, 0, -1);

    }

    static void knightMove(int y, int x) { // просчет атаки коня
        CheckKnight(y, x, 2, 1);
        CheckKnight(y, x, 2, -1);
        CheckKnight(y, x, -2, 1);
        CheckKnight(y, x, -2, -1);
        CheckKnight(y, x, 1, 2);
        CheckKnight(y, x, 1, -2);
        CheckKnight(y, x, -1, 2);
        CheckKnight(y, x, -1, -2);

    }

    static void bishopMove(int y, int x) {  // просчет атаки слона
        checkLong(y, x, 1, 1);
        checkLong(y, x, 1, -1);
        checkLong(y, x, -1, 1);
        checkLong(y, x, -1, -1);

    }


    static void CheckKnight(int y, int x, int y1, int x1) { //шаблон атаки коня

        if (include(y + y1, x + x1) && empty(y + y1, x + x1) && checkShah(y, x, y + y1, x + x1))
            buttons[y + y1][x + x1].setIcon(dot);
        if (include(y + y1, x + x1) && attackReady(y, x, y + y1, x + x1)
                && checkShah(y, x, y + y1, x + x1))
            buttons[y + y1][x + x1].setBackground(Color.red);
    }

    static void queenMove(int y, int x) {
        checkLong(y, x, 1, 1);
        checkLong(y, x, 1, -1);
        checkLong(y, x, -1, 1);
        checkLong(y, x, -1, -1);
        checkLong(y, x, 1, 0);
        checkLong(y, x, -1, 0);
        checkLong(y, x, 0, 1);
        checkLong(y, x, 0, -1);
    }

    static void move(int moveY, int moveX, int y, int x) { //смена хода
       if (moveReady) {

           if (pieces[moveY][moveX].name.equals("king")) {
               if (x == moveX + 2) {
                   pieces[7][5] = pieces[7][7];
                   buttons[7][5].setIcon(pieces[7][5].pieceIcon);
                   pieces[7][7] = null;
                   buttons[7][7].setIcon(null);

               }
               if (x == moveX - 2) {
                   pieces[7][3] = pieces[7][0];
                   buttons[7][3].setIcon(pieces[7][3].pieceIcon);
                   pieces[7][0] = null;
                   buttons[7][0].setIcon(null);
               }
               if (colour) {
                   wKingY = y;
                   wKingX = x;
               } else {
                   bKingY = y;
                   bKingX = x;
               }
           }
           buttons[moveY][moveX].setIcon(null);
           buttons[y][x].setIcon(pieces[moveY][moveX].pieceIcon);
           pieces[y][x] = pieces[moveY][moveX];
           pieces[moveY][moveX] = null;
           if (pieces[y][x].fistMove != null) pieces[y][x].fistMove = false;
           if (pieces[y][x].name.equals("pawn") && y == 0) {
               if (pieces[y][x].colour) {
                   pieces[y][x] = new Piece("queen", true, null, new ImageIcon("queenW.png"));
               } else {
                   pieces[y][x] = new Piece("queen", false, null, new ImageIcon("queenB.png"));
               }
               buttons[y][x].setIcon((pieces[y][x].pieceIcon));
           }
           mirror();
           DefColours();
           clearDots();
           colour = !colour;
           if (endGame()) {
               if (colour) System.out.println("Black wins");
               else System.out.println("White wins");
           }
       }
    }


    static Boolean checkShah(int y, int x, int y1, int x1) { // шах после хода

        Piece piece = pieces[y][x];
        Piece piece1 = null;
        if (!empty(y1, x1)) piece1 = pieces[y1][x1];
        buttons[y][x].setIcon(null);
        pieces[y][x] = null;
        buttons[y1][x1].setIcon(piece.pieceIcon);
        pieces[y1][x1] = piece;
        boolean r = !((colour && !checkKing(wKingY, wKingX, true)) || (!colour
                && !checkKing(bKingY, bKingX, false)));
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

     static void checkLong(int y, int x, int y1, int x1) { //просчет возможности хода по вертикали, горизонтали, диагонали

        for (int i = 1; i <= 7; i++) {
            if (!include(y + i * y1, x + i * x1)) break;
            if ((empty(y + i * y1, x + i * x1)) && checkShah(y, x, y + i * y1, x + i * x1))
                buttons[y + i * y1][x + i * x1].setIcon(dot);
            else {
                if (pieces[y + i * y1][x + i * x1] != null && checkShah(y, x, y + i * y1, x + i * x1)
                        && pieces[y + i * y1][x + i * x1].colour != pieces[y][x].colour)
                    buttons[y + i * y1][x + i * x1].setBackground(Color.red);
                break;
            }
        }


    }

    static void mirror() { //разворот доски
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


    static void clearDots() { //удаление кругов хода
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (buttons[y][x].getIcon() == dot) buttons[y][x].setIcon(null);
            }
        }
    }


    static void kingCheckMove(int y, int x, int y1, int x1, Boolean color) { //шаблон для хода короля
        int i = 1;
        if (include(y + i * y1, x + i * x1)) {
            if ((empty(y + i * y1, x + i * x1)) && checkKing(y + i * y1, x + i * x1, color))
                buttons[y + i * y1][x + i * x1].setIcon(dot);
            else {
                if (!empty(y + i * y1, x + i * x1) && pieces[y + i * y1][x + i * x1].colour != pieces[y][x].colour
                        && checkKing(y + i * y1, x + i * x1, color))
                    buttons[y + i * y1][x + i * x1].setBackground(Color.red);
            }
        }
    }


    static void kingMove(int y, int x) { //обработка хода короля
        if (pieces[y][x].fistMove && !empty(7, 0) && empty(7, 1) && empty(7, 3)
                && pieces[7][0].name.equals("rook")
                && pieces[7][0].fistMove) { //обработка рокировки
            kingCheckMove(y, x, 0, -2, colour);
        }

        if (pieces[y][x].fistMove && !empty(7, 7) && empty(7, 6) && pieces[7][7].name.equals("rook")
                && pieces[7][7].fistMove) {
            kingCheckMove(y, x, 0, +2, colour);
        }
        kingCheckMove(y, x, 1, 0, colour);
        kingCheckMove(y, x, -1, 0, colour);
        kingCheckMove(y, x, 0, 1, colour);
        kingCheckMove(y, x, 0, -1, colour);
        kingCheckMove(y, x, 1, 1, colour);
        kingCheckMove(y, x, 1, -1, colour);
        kingCheckMove(y, x, -1, 1, colour);
        kingCheckMove(y, x, -1, -1, colour);

    }


    static Boolean checkKing(int y, int x, Boolean color) { //проверка на шах

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


    static Boolean shortStop(int y, int x, int y1, int x1, String p, Boolean color) { //шаблон для проверки коротких атак
        int i = 1;
        return !include(y + i * y1, x + i * x1) || empty(y + i * y1, x + i * x1)
                || color == pieces[y + i * y1][x + i * x1].colour || !pieces[y + i * y1][x + i * x1].name.equals(p);
    }


    static  Boolean stop(int y, int x, int y1, int x1, String p, Boolean color) { //шаблон для проверки длинных атак
        for (int i = 1; i <= 7; i++) { // y+
            if (!include(y + i * y1, x + i * x1)) break;
            if (!empty(y + i * y1, x + i * x1)) {
                if (pieces[y + i * y1][x + i * x1].colour == color
                        && !pieces[y + i * y1][x + i * x1].name.equals("king"))
                    return true;
                if (pieces[y + i * y1][x + i * x1].name.equals(p)) return false;
                if (pieces[y + i * y1][x + i * x1].name.equals("queen")) return false;
            }
        }
        return true;
    }

    static Boolean empty(int y, int x) { //наличие фигуры
        return (pieces[y][x] == null);
    }

    static Boolean include(int y, int x) { //существование поля
        return !(y > 7 || y < 0 || x > 7 || x < 0);
    }

    static Boolean attackReady(int y, int x, int y1, int x1) {
        return (pieces[y1][x1] != null && pieces[y1][x1].colour != pieces[y][x].colour);
    }

}