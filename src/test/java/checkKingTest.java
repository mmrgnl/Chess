import static org.junit.jupiter.api.Assertions.*;

public class checkKingTest {

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

}
