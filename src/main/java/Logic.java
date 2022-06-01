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
    static boolean enPassant = false;
    static int enPassantY;
    static int enPassantX;

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
                        case ('n') -> pieces[y][x1] = new Piece(Piece.Name.KNIGHT, false, false,
                                new ImageIcon("knightB.png"));
                        case ('b') -> pieces[y][x1] = new Piece(Piece.Name.BISHOP, false, false,
                                new ImageIcon("bishopB.png"));
                        case ('q') -> pieces[y][x1] = new Piece(Piece.Name.QUEEN, false, false,
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
                        case ('N') -> pieces[y][x1] = new Piece(Piece.Name.KNIGHT, true, false,
                                new ImageIcon("knightW.png"));
                        case ('B') -> pieces[y][x1] = new Piece(Piece.Name.BISHOP, true, false,
                                new ImageIcon("bishopW.png"));
                        case ('Q') -> pieces[y][x1] = new Piece(Piece.Name.QUEEN, true, false,
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

        if (ChessBoard.buttons[y][x].getBackground() == Color.red
                || (ChessBoard.buttons[y][x].getIcon() == ChessBoard.dot )) {
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
                if (ChessBoard.buttons[y][x].getIcon() == ChessBoard.dot
                        || ChessBoard.buttons[y][x].getBackground() == Color.red) {
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
            if (enPassant && y == enPassantY && x + 1 == enPassantX) {
                if (empty(y - 1, x + 1)) ChessBoard.addDot(y - 1, x + 1);
            }
            if (enPassant && y == enPassantY && x - 1 == enPassantX) {
                if (empty(y - 1, x - 1))  ChessBoard.addDot(y - 1, x - 1);
            }
            if (include(y - 1, x - 1)) {
                if (pieces[y - 1][x - 1] != null && checkShah(y, x, y - 1, x - 1)
                        && pieces[y - 1][x - 1].colour != pieces[y][x].colour)
                    ChessBoard.paintRed(y - 1, x - 1);
            }
            if (include(y - 1, x + 1)) {
                if (pieces[y - 1][x + 1] != null && checkShah(y, x, y - 1, x + 1)
                        && pieces[y - 1][x + 1].colour != pieces[y][x].colour)
                    ChessBoard.paintRed(y - 1, x + 1);
            }
            if (include(y - 1, x) && (empty(y - 1, x)) && checkShah(y, x, y - 1, x))
                ChessBoard.addDot(y - 1, x);

            if (pieces[y][x].fistMove && include(y - 2, x) && (empty(y - 2, x)) && checkShah(y, x, y - 2, x)) {
                ChessBoard.addDot(y - 2, x);
            }

        } else {
            if (enPassant && y == enPassantY && x + 1 == enPassantX) {
                if (empty(y + 1, x + 1)) ChessBoard.addDot(y + 1, x + 1);
            }
            if (enPassant && y == enPassantY && x - 1 == enPassantX) {
                if (empty(y + 1, x - 1)) ChessBoard.addDot(y + 1, x - 1);
            }
            if (include(y + 1, x + 1)) {
                if (pieces[y + 1][x + 1] != null && checkShah(y, x, y + 1, x + 1)
                        && pieces[y + 1][x + 1].colour != pieces[y][x].colour)
                    ChessBoard.paintRed(y + 1, x + 1);
            }
            if (include(y + 1, x - 1)) {
                if (pieces[y + 1][x - 1] != null && checkShah(y, x, y + 1, x - 1)
                        && pieces[y + 1][x - 1].colour != pieces[y][x].colour)
                    ChessBoard.paintRed(y + 1, x - 1);
            }
            if (include(y + 1, x) && (empty(y + 1, x)) && checkShah(y, x, y + 1, x))
                ChessBoard.addDot(y + 1, x);
            if (pieces[y][x].fistMove && include(y + 2, x) && (empty(y + 2, x)) && checkShah(y, x, y + 2, x)) {
                ChessBoard.addDot(y + 2, x);
            }
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
            ChessBoard.addDot(y + y1, x + x1);


        }
        if (include(y + y1, x + x1) && attackReady(y, x, y + y1, x + x1)
                && checkShah(y, x, y + y1, x + x1))
            ChessBoard.paintRed(y + y1, x + x1);

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
        if (enPassant && pieces[moveY][moveX].name.equals(Piece.Name.PAWN)) {
            if ((colour || mirrors) && include(y + 1, x) && !empty(y + 1, x)
                    && (y + 1 == enPassantY && x == enPassantX)) {

                if (pieces[y + 1][x].name.equals(Piece.Name.PAWN) && pieces[y + 1][x].colour != colour){
                    pieces[y + 1][x] = null;
                    ChessBoard.buttons[y + 1][x].setIcon(null);
                }

        } else {
                if (include(y - 1, x)&& (y - 1 == enPassantY && x == enPassantX) && !empty(y - 1, x)
                        && pieces[y - 1][x].name.equals(Piece.Name.PAWN)
                        && pieces[y - 1][x].colour) {
                    pieces[y - 1][x] = null;
                    ChessBoard.buttons[y - 1][x].setIcon(null);
                }
            }
        }
        if (pieces[moveY][moveX].name.equals(Piece.Name.KING)) { //рокировка
            if (x == moveX + 2) {
                pieces[y][moveX + 1] = pieces[y][moveX+3];
                ChessBoard.buttons[7][moveX + 1].setIcon(pieces[y][moveX + 1].pieceIcon);
                pieces[y][moveX + 3] = null;
                ChessBoard.buttons[y][moveX + 3].setIcon(null);
            }
            if (x == moveX - 2) {
                pieces[y][moveX - 1] = pieces[y][moveX - 4];
                ChessBoard.buttons[y][moveX - 1].setIcon(pieces[y][moveX - 1].pieceIcon);
                pieces[y][moveX - 4] = null;
                ChessBoard.buttons[y][moveX - 4].setIcon(null);
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
        if (pieces[y][x].fistMove && pieces[y][x].name == Piece.Name.PAWN && y - moveY == 2 || moveY - y == 2) {
            enPassant = true;
                if (mirrors) enPassantY = 7 - y;
                else enPassantY = y;
            enPassantX = x;
        }
        else enPassant = false;
        if (pieces[y][x].fistMove) pieces[y][x].fistMove = false;
        if (((mirrors || colour) && pieces[y][x].name.equals(Piece.Name.PAWN) && y == 0) || (!colour
                && pieces[y][x].name.equals(Piece.Name.PAWN) && y == 7)) {
            if (pieces[y][x].colour) {
                pieces[y][x] = new Piece(Piece.Name.QUEEN, true,false, new ImageIcon("queenW.png"));
            } else {
                pieces[y][x] = new Piece(Piece.Name.QUEEN, false,false, new ImageIcon("queenB.png"));
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
                ChessBoard.addDot(y + i * y1, x + i * x1);
            else {
                if (pieces[y + i * y1][x + i * x1] != null && checkShah(y, x, y + i * y1, x + i * x1)
                        && pieces[y + i * y1][x + i * x1].colour != pieces[y][x].colour)
                    ChessBoard.paintRed(y + i * y1, x + i * x1);
                break;
            }
        }

    }


    static void kingCheckMove(int y, int x, int y1, int x1, Boolean color) { //шаблон для хода короля
        int i = 1;
        if (include(y + i * y1, x + i * x1)) {
            if ((empty(y + i * y1, x + i * x1)) && checkKing(y + i * y1, x + i * x1, color))
                ChessBoard.addDot(y + i * y1, x + i * x1);
            else {
                if (!empty(y + i * y1, x + i * x1) && pieces[y + i * y1][x + i * x1].colour != pieces[y][x].colour
                        && checkKing(y + i * y1, x + i * x1, color))
                    ChessBoard.paintRed(y + i * y1, x + i * x1);
            }
        }
    }


    static void kingMove(int y, int x) { //обработка хода короля
        if (pieces[y][x].fistMove && !empty(y, 0) && empty(y, 1) && empty(y, 3)
                && pieces[y][0].name.equals(Piece.Name.ROOK)
                && pieces[y][0].fistMove) { //обработка рокировки
            kingCheckMove(y, x, 0, -2, colour);
        }

        if (pieces[y][x].fistMove && !empty(y, 7) && empty(y, 6) && pieces[y][7].name.equals(Piece.Name.ROOK)
                && pieces[y][7].fistMove) {
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

                if (color == pieces[y + i * y1][x + i * x1].colour
                        && pieces[y + i * y1][x + i * x1].name != Piece.Name.KING) return false;
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
