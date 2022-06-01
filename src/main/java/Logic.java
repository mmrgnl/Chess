import javax.swing.*;
import java.awt.*;

public class Logic {

    static int wKingY; //позиции короля
    static int wKingX;
    static int bKingY;
    static int bKingX;
    static boolean colour = true;//t = white, f = black
    static Piece[][] pieces = new Piece[8][8];
    static boolean mirrors;

    static void FEN(String s) { //обработка кода расположения фигур
        int y = 0;
        int x;
        String[] symbols = s.split("/");
        for (String symbol : symbols) {
            int x1 = 0;
            for (x = 0; x < 8; x++) {
                if (x < symbol.length()) {
                    switch (symbol.charAt(x)) {
                        case ('p') -> pieces[y][x1] = new Piece(Piece.Name.PAWN, false, true,
                                new ImageIcon("pawnB.png"));
                        case ('r') -> pieces[y][x1] = new Piece(Piece.Name.ROOK, false, true,
                                new ImageIcon("rookB.png"));
                        case ('n') -> pieces[y][x1] = new Piece(Piece.Name.KNIGHT, false, null,
                                new ImageIcon("knightB.png"));
                        case ('b') -> pieces[y][x1] = new Piece(Piece.Name.BISHOP, false, null,
                                new ImageIcon("bishopB.png"));
                        case ('q') -> pieces[y][x1] = new Piece(Piece.Name.QUEEN, false, null,
                                new ImageIcon("queenB.png"));
                        case ('k') -> {
                            pieces[y][x1] = new Piece(Piece.Name.KING, false, true,
                                    new ImageIcon("kingB.png"));
                            bKingY = y;
                            bKingX = x1;
                        }
                        case ('P') -> pieces[y][x1] = new Piece(Piece.Name.PAWN, true, true,
                                new ImageIcon("pawnW.png"));
                        case ('R') -> pieces[y][x1] = new Piece(Piece.Name.ROOK, true, true,
                                new ImageIcon("rookW.png"));
                        case ('N') -> pieces[y][x1] = new Piece(Piece.Name.KNIGHT, true, null,
                                new ImageIcon("knightW.png"));
                        case ('B') -> pieces[y][x1] = new Piece(Piece.Name.BISHOP, true, null,
                                new ImageIcon("bishopW.png"));
                        case ('Q') -> pieces[y][x1] = new Piece(Piece.Name.QUEEN, true, null,
                                new ImageIcon("queenW.png"));
                        case ('K') -> {
                            pieces[y][x1] = new Piece(Piece.Name.KING, true, true,
                                    new ImageIcon("kingW.png"));
                            wKingY = y;
                            wKingX = x1;
                        }
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

    static int moveY;
    static int moveX;

    static void actionMove(int y, int x) {
        if (ChessBoard.buttons[y][x].getBackground() == Color.red || ( ChessBoard.buttons[y][x].getIcon() == ChessBoard.dot)) {
            move(moveY, moveX, y, x);
        }
        if (ChessBoard.buttons[y][x].getIcon() != ChessBoard.dot) {
            ChessBoard.clearDots();
            ChessBoard.defColours();
        }
        if (pieces[y][x] != null && colour == pieces[y][x].colour) {
            switch (pieces[y][x].name) {
                case PAWN -> pawnMove(y, x);
                case ROOK -> rookMove(y, x);
                case KNIGHT -> knightMove(y, x);
                case BISHOP -> bishopMove(y, x);
                case QUEEN -> queenMove(y, x);
                case KING -> kingMove(y, x);
            }
            moveY = y;
            moveX = x;
        }
    }

    static boolean endGame() { //возвращает истину, когда нет доступных ходов
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (pieces[y][x] != null && pieces[y][x].colour == colour) {
                    switch (pieces[y][x].name) {
                        case PAWN -> pawnMove(y, x);
                        case ROOK -> rookMove(y, x);
                        case KNIGHT -> knightMove(y, x);
                        case BISHOP -> bishopMove(y, x);
                        case QUEEN -> queenMove(y, x);
                        case KING -> kingMove(y, x);
                    }
                }
            }
        }

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if (ChessBoard.buttons[y][x].getIcon() == ChessBoard.dot || ChessBoard.buttons[y][x].getBackground() == Color.red) {
                    ChessBoard.defColours();
                    ChessBoard.clearDots();
                    return false;
                }
            }
        }
        ChessBoard.defColours();
        ChessBoard.clearDots();
        return true;
    }


    static void pawnMove(int y, int x) { //просчет атаки пешки

        if (colour || mirrors) {

            if (include(y - 1, x - 1)) {
                if (pieces[y - 1][x - 1] != null && checkShah(y, x, y - 1, x - 1)
                        && pieces[y - 1][x - 1].colour != pieces[y][x].colour)
                    ChessBoard.buttons[y - 1][x - 1].setBackground(Color.red);

            }
            if (include(y - 1, x + 1)) {
                if (pieces[y - 1][x + 1] != null && checkShah(y, x, y - 1, x + 1)
                        && pieces[y - 1][x + 1].colour != pieces[y][x].colour)
                    ChessBoard.buttons[y - 1][x + 1].setBackground(Color.red);
            }
            if (include(y - 1, x) && (empty(y - 1, x)) && checkShah(y, x, y - 1, x)) ChessBoard.buttons[y - 1][x].setIcon(ChessBoard.dot);
            if (pieces[y][x].fistMove && include(y - 2, x) && (empty(y - 2, x)) && checkShah(y, x, y - 2, x))
                ChessBoard.buttons[y - 2][x].setIcon(ChessBoard.dot);

        } else {

            if (include(y + 1, x + 1)) {
                if (pieces[y + 1][x + 1] != null && checkShah(y, x, y + 1, x + 1)
                        && pieces[y + 1][x + 1].colour != pieces[y][x].colour)
                    ChessBoard.buttons[y + 1][x + 1].setBackground(Color.red);

            }
            if (include(y + 1, x - 1)) {
                if (pieces[y + 1][x - 1] != null && checkShah(y, x, y + 1, x - 1)
                        && pieces[y + 1][x - 1].colour != pieces[y][x].colour)
                    ChessBoard.buttons[y + 1][x - 1].setBackground(Color.red);
            }
            if (include(y + 1, x) && (empty(y + 1, x)) && checkShah(y, x, y + 1, x)) ChessBoard.buttons[y + 1][x].setIcon(ChessBoard.dot);

            if (pieces[y][x].fistMove && include(y + 2, x) && (empty(y + 2, x)) && checkShah(y, x, y + 2, x))
                ChessBoard.buttons[y + 2][x].setIcon(ChessBoard.dot);

        }


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



        if (include(y + y1, x + x1) && empty(y + y1, x + x1) && checkShah(y, x, y + y1, x + x1)) {
            ChessBoard.buttons[y + y1][x + x1].setIcon(ChessBoard.dot);


        }
        if (include(y + y1, x + x1) && attackReady(y, x, y + y1, x + x1)
                && checkShah(y, x, y + y1, x + x1))
            ChessBoard.buttons[y + y1][x + x1].setBackground(Color.red);

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

        if (pieces[moveY][moveX].name.equals(Piece.Name.KING)) {
            if (x == moveX + 2) {
                pieces[7][5] = pieces[7][7];
                ChessBoard.buttons[7][5].setIcon(pieces[7][5].pieceIcon);
                pieces[7][7] = null;
                ChessBoard.buttons[7][7].setIcon(null);

            }
            if (x == moveX - 2) {
                pieces[7][3] = pieces[7][0];
                ChessBoard.buttons[7][3].setIcon(pieces[7][3].pieceIcon);
                pieces[7][0] = null;
                ChessBoard.buttons[7][0].setIcon(null);
            }
            if (colour) {
                wKingY = y;
                wKingX = x;
            } else {
                bKingY = y;
                bKingX = x;
            }
        }
        ChessBoard.buttons[moveY][moveX].setIcon(null);
        ChessBoard.buttons[y][x].setIcon(pieces[moveY][moveX].pieceIcon);
        pieces[y][x] = pieces[moveY][moveX];
        pieces[moveY][moveX] = null;
        if (pieces[y][x].fistMove != null) pieces[y][x].fistMove = false;
        if (pieces[y][x].name.equals(Piece.Name.PAWN) && y == 0) {
            if (pieces[y][x].colour) {
                pieces[y][x] = new Piece(Piece.Name.QUEEN, true, null, new ImageIcon("queenW.png"));
            } else {
                pieces[y][x] = new Piece(Piece.Name.QUEEN, false, null, new ImageIcon("queenB.png"));
            }
            ChessBoard.buttons[y][x].setIcon((pieces[y][x].pieceIcon));
        }
        if (mirrors) {
            wKingY = 7 - wKingY;
            bKingY = 7 -  bKingY;
            ChessBoard.mirror();
        }
        ChessBoard.defColours();
        ChessBoard.clearDots();
        colour = !colour;

        if (endGame()) {
            if (colour) System.out.println("Black wins");
            else System.out.println("White wins");
        }

    }

    static boolean checkShah(int y, int x, int y1, int x1) { // шах после хода
        Piece piece = pieces[y][x];
        Piece piece1 = null;
        if (!empty(y1, x1)) piece1 = pieces[y1][x1];
        ChessBoard.buttons[y][x].setIcon(null);
        pieces[y][x] = null;
        ChessBoard.buttons[y1][x1].setIcon(piece.pieceIcon);
        pieces[y1][x1] = piece;
        boolean r = !((colour && !checkKing(wKingY, wKingX, true)) || (!colour &&
                !checkKing(bKingY, bKingX, false)));
        ChessBoard.buttons[y][x].setIcon(piece.pieceIcon);
        pieces[y][x] = piece;
        if (piece1 != null) {
            ChessBoard.buttons[y1][x1].setIcon(piece1.pieceIcon);
            pieces[y1][x1] = piece1;
        } else {
            ChessBoard.buttons[y1][x1].setIcon(null);
            pieces[y1][x1] = null;
        }
        return r;
    }

    static void checkLong(int y, int x, int y1, int x1) { //просчет возможности хода по вертикали, горизонтали, диагонали
        for (int i = 1; i <= 7; i++) {
            if (!include(y + i * y1, x + i * x1)) break;
            if ((empty(y + i * y1, x + i * x1)) && checkShah(y, x, y + i * y1, x + i * x1))
                ChessBoard.buttons[y + i * y1][x + i * x1].setIcon(ChessBoard.dot);
            else {
                if (pieces[y + i * y1][x + i * x1] != null && checkShah(y, x, y + i * y1, x + i * x1)
                        && pieces[y + i * y1][x + i * x1].colour != pieces[y][x].colour)
                    ChessBoard.buttons[y + i * y1][x + i * x1].setBackground(Color.red);
                break;
            }
        }

    }


    static void kingCheckMove(int y, int x, int y1, int x1, Boolean color) { //шаблон для хода короля
        int i = 1;
        if (include(y + i * y1, x + i * x1)) {
            if ((empty(y + i * y1, x + i * x1)) && checkKing(y + i * y1, x + i * x1, color))
                ChessBoard.buttons[y + i * y1][x + i * x1].setIcon(ChessBoard.dot);
            else {
                if (!empty(y + i * y1, x + i * x1) && pieces[y + i * y1][x + i * x1].colour != pieces[y][x].colour
                        && checkKing(y + i * y1, x + i * x1, color))
                    ChessBoard.buttons[y + i * y1][x + i * x1].setBackground(Color.red);
            }
        }
    }


    static void kingMove(int y, int x) { //обработка хода короля
        if (pieces[y][x].fistMove && !empty(7, 0) && empty(7, 1) && empty(7, 3)
                && pieces[7][0].name.equals(Piece.Name.ROOK)
                && pieces[7][0].fistMove) { //обработка рокировки
            kingCheckMove(y, x, 0, -2, colour);
        }

        if (pieces[y][x].fistMove && !empty(7, 7) && empty(7, 6) && pieces[7][7].name.equals(Piece.Name.ROOK)
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


    static boolean checkKing(int y, int x, boolean color) { //проверка на шах
        if (stop(y, x, 1, 1, Piece.Name.BISHOP, color)) return false;
        if (stop(y, x, 1, -1, Piece.Name.BISHOP, color)) return false;
        if (stop(y, x, -1, 1, Piece.Name.BISHOP, color)) return false;
        if (stop(y, x, -1, -1, Piece.Name.BISHOP, color)) return false;
        if (stop(y, x, 1, 0, Piece.Name.ROOK, color)) return false;
        if (stop(y, x, -1, 0, Piece.Name.ROOK, color)) return false;
        if (stop(y, x, 0, 1, Piece.Name.ROOK, color)) return false;
        if (stop(y, x, 0, -1, Piece.Name.ROOK, color)) return false;
        if (!shortStop(y, x, 1, 1, Piece.Name.KING, color)) return false;
        if (!shortStop(y, x, 1, -1, Piece.Name.KING, color)) return false;
        if (!shortStop(y, x, 1, 0, Piece.Name.KING, color)) return false;
        if (!shortStop(y, x, -1, 1, Piece.Name.KING, color)) return false;
        if (!shortStop(y, x, -1, -1, Piece.Name.KING, color)) return false;
        if (!shortStop(y, x, -1, 0, Piece.Name.KING, color)) return false;
        if (!shortStop(y, x, 0, -1, Piece.Name.KING, color)) return false;
        if (!shortStop(y, x, 0, 1, Piece.Name.KING, color)) return false;
        if (!shortStop(y, x, 0, -1, Piece.Name.KING, color)) return false;
        if (color || mirrors) {
            if (!shortStop(y, x, -1, 1, Piece.Name.PAWN, color)) return false;
            if (!shortStop(y, x, -1, -1,  Piece.Name.PAWN, color)) return false;
        } else {
            if (!shortStop(y, x, 1, 1,  Piece.Name.PAWN, false)) return false;
            if (!shortStop(y, x, 1, -1,  Piece.Name.PAWN, false)) return false;
        }
        if (!shortStop(y, x, 2, 1, Piece.Name.KNIGHT, color)) return false;
        if (!shortStop(y, x, 2, -1, Piece.Name.KNIGHT, color)) return false;
        if (!shortStop(y, x, -2, 1, Piece.Name.KNIGHT, color)) return false;
        if (!shortStop(y, x, -2, -1, Piece.Name.KNIGHT, color)) return false;
        if (!shortStop(y, x, 1, -2, Piece.Name.KNIGHT, color)) return false;
        if (!shortStop(y, x, 1, 2, Piece.Name.KNIGHT, color)) return false;
        if (!shortStop(y, x, -1, -2, Piece.Name.KNIGHT, color)) return false;
        return shortStop(y, x, -1, 2, Piece.Name.KNIGHT, color);
    }


    static boolean shortStop(int y, int x, int y1, int x1, Piece.Name p, boolean color) { //шаблон для проверки коротких атак
        int i = 1;
        return !include(y + i * y1, x + i * x1) || empty(y + i * y1, x + i * x1)
                || color == pieces[y + i * y1][x + i * x1].colour || !pieces[y + i * y1][x + i * x1].name.equals(p);
    }


    static boolean stop(int y, int x, int y1, int x1, Piece.Name p, boolean color) { //шаблон для проверки длинных атак
        for (int i = 1; i <= 7; i++) { // y+
            if (!include(y + i * y1, x + i * x1)) break;
            if (!empty(y + i * y1, x + i * x1)) {
                if(color != pieces[y + i * y1][x + i * x1].colour) {
                    return (pieces[y + i * y1][x + i * x1].name.equals(p)
                            || pieces[y + i * y1][x + i * x1].name.equals(Piece.Name.QUEEN));
                }
            }
        }
        return false;
    }

    static boolean empty(int y, int x) { //наличие фигуры
        return (pieces[y][x] == null);
    }

    static boolean include(int y, int x) { //существование поля
        return !(y > 7 || y < 0 || x > 7 || x < 0);
    }

    static boolean attackReady(int y, int x, int y1, int x1) {
        return (pieces[y1][x1] != null && pieces[y1][x1].colour != pieces[y][x].colour);
    }

}
