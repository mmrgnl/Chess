import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @org.junit.jupiter.api.Test

    void  checkKingTest() {

        Chess.FEN("rnbq1bnr/pppppppp/8/8/k7/R7/PPPPPPPP/RNBQKBN1");
        assertFalse(Chess.checkKing(4, 0, false));

        Chess.FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        assertTrue(Chess.checkKing(8, 7, true));

        Chess.FEN("1n2bqn1/r1pppbpp/1p2Pr1p/p1R1B1PP/3NPK2/1k3P2/P2P1P2/R2Q1BN1");
        assertFalse(Chess.checkKing(4, 5, true));

        Chess.FEN("kn2bqn1/r1pppbpp/1p2Pr1p/p1R1B1PP/3NPK2/5P2/P2P1P2/R2Q1BN1");
        assertTrue(Chess.checkKing(0, 0,  false));

    }

    @org.junit.jupiter.api.Test

    void moveTest() {

        Chess.FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        Chess.startGame();
        Chess.actionMove(7, 7);
        Chess.move(7, 7,  6, 7);
        assertFalse(Chess.empty(6, 7));

        Chess.FEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        Chess.startGame();
        Chess.actionMove(7, 1);
        Chess.actionMove(6, 0);
        Chess.move(7, 1,  6, 0);
        assertFalse(Chess.empty(6, 0));

        Chess.FEN("n1b1k1n1/rPPpppB1/p7/R3bq2/6Np/3P1PPr/PN2Q1PP/1NB1K2R");
        Chess.startGame();
        Chess.actionMove(3, 0);
        Chess.actionMove(3, 7);
        Chess.move(3, 0,  3, 7);
        assertTrue(Chess.empty(3, 7));

    }

    @org.junit.jupiter.api.Test

    void endGameTest() {

        Chess.FEN("1nbqk1nr/2p3r1/8/2p5/1p6/p2b4/8/7K");
        Chess.startGame();
        assertTrue(Chess.endGame());

        Chess.FEN(" 4k3/2n3b1/7r/3b1n2/1p3p2/p1r5/1p6/5q1K");
        Chess.startGame();
        assertTrue(Chess.endGame());

        Chess.FEN(" 4k3/2n3b1/7r/3b1n2/1p3p2/p1r5/1p6/5q1K");
        Chess.startGame();
        assertTrue(Chess.endGame());

        Chess.FEN(" 4k3/2n3b1/7r/3b1n2/1p3p2/p1r5/1p6/5q1K");
        Chess.startGame();
        assertTrue(Chess.endGame());

    }

}
