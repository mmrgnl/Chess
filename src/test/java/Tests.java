import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @org.junit.jupiter.api.Test

    void  checkKingTest() {

        ChessBoard.FEN("rnbq1bnr/pppppppp/8/8/k7/R7/PPPPPPPP/RNBQKBN1");
        assertFalse(ChessBoard.checkKing(4, 0, false));

        ChessBoard.FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        assertTrue(ChessBoard.checkKing(8, 7, true));

        ChessBoard.FEN("1n2bqn1/r1pppbpp/1p2Pr1p/p1R1B1PP/3NPK2/1k3P2/P2P1P2/R2Q1BN1");
        assertFalse(ChessBoard.checkKing(4, 5, true));

        ChessBoard.FEN("kn2bqn1/r1pppbpp/1p2Pr1p/p1R1B1PP/3NPK2/5P2/P2P1P2/R2Q1BN1");
        assertTrue(ChessBoard.checkKing(0, 0,  false));

    }

    @org.junit.jupiter.api.Test

    void moveTest() {

        ChessBoard.FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        ChessBoard.startGame();
        ChessBoard.actionMove(7, 7);
        ChessBoard.move(7, 7,  6, 7);
        assertFalse(ChessBoard.empty(6, 7));

        ChessBoard.FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        ChessBoard.startGame();
        ChessBoard.actionMove(7, 1);
        ChessBoard.actionMove(6, 0);
        ChessBoard.move(7, 1,  6, 0);
        assertFalse(ChessBoard.empty(6, 0));

        ChessBoard.FEN("n1b1k1n1/rPPpppB1/p7/R3bq2/6Np/3P1PPr/PN2Q1PP/1NB1K2R");
        ChessBoard.startGame();
        ChessBoard.actionMove(3, 0);
        ChessBoard.actionMove(3, 7);
        ChessBoard.move(3, 0,  3, 7);
        assertTrue(ChessBoard.empty(3, 7));

    }

    @org.junit.jupiter.api.Test

    void endGameTest() {

        ChessBoard.FEN("1nbqk1nr/2p3r1/8/2p5/1p6/p2b4/8/7K");
        ChessBoard.startGame();
        assertTrue(ChessBoard.endGame());

        ChessBoard.FEN(" 4k3/2n3b1/7r/3b1n2/1p3p2/p1r5/1p6/5q1K");
        ChessBoard.startGame();
        assertTrue(ChessBoard.endGame());

        ChessBoard.FEN(" 4k3/2n3b1/7r/3b1n2/1p3p2/p1r5/1p6/5q1K");
        ChessBoard.startGame();
        assertTrue(ChessBoard.endGame());

        ChessBoard.FEN(" 4k3/2n3b1/7r/3b1n2/1p3p2/p1r5/1p6/5q1K");
        ChessBoard.startGame();
        assertTrue(ChessBoard.endGame());

    }

}
