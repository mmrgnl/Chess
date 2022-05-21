import static org.junit.jupiter.api.Assertions.*;

public class moveTest {

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

}
