import static org.junit.jupiter.api.Assertions.*;

public class endGameTest {

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




