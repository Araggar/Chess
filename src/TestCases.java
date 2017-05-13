import java.util.BitSet;

/**
 * Created by Tyrael on 4/29/2017.
 */
public class TestCases {

    public static void caseGame(){
        ChessGame chess = new ChessGame();
        chess.simulate2ply();
        chess.simulate2ply();
        chess.simulate2ply();
        System.out.println(chess.finalMove());
    }

    public static void caseBitBoard(){
        BitBoard test = new BitBoard(64, 5);
        System.out.println(test.boardValue());
        test.set(0, 7);
        System.out.println(test.boardValue());
        System.out.println(test);

    }

    public static void caseGenerator() {
        BitBoard blackPawns, blackRooks, blackKnights, blackBishops, blackQueen, blackKing;
        BitBoard whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueen, whiteKing;
        BitBoard fullBlack = new BitBoard(64, 0);
        BitBoard fullWhite = new BitBoard(64, 0);
        BitBoard fullBoard = new BitBoard(64, 0);
        BoardInitializer ini = new BoardInitializer();

        int counter = 0;

        //Values in Centipawns
        //Black "Suit" locations
        blackPawns = ini.generateBitset("p", 100);
        blackRooks = ini.generateBitset("r", 500);
        blackKnights = ini.generateBitset("n", 300);
        blackBishops = ini.generateBitset("b", 300);
        blackQueen = ini.generateBitset("q", 900);
        blackKing = ini.generateBitset("k", 600000);
        //White "Suit" locations
        whitePawns = ini.generateBitset("P", -100);
        whiteRooks = ini.generateBitset("R", -500);
        whiteKnights = ini.generateBitset("N", -300);
        whiteBishops = ini.generateBitset("B", -300);
        whiteQueen = ini.generateBitset("Q", -900);
        whiteKing = ini.generateBitset("K", -600000);


        //Full representation of black pieces
        fullBlack.or(blackPawns); fullBlack.or(blackRooks); fullBlack.or(blackKnights);
        fullBlack.or(blackQueen); fullBlack.or(blackKing); fullBlack.or(blackBishops);
        //Full representation of white pieces
        fullWhite.or(whitePawns); fullWhite.or(whiteRooks); fullWhite.or(whiteKnights);
        fullWhite.or(whiteQueen); fullWhite.or(whiteKing); fullWhite.or(whiteBishops);
        //Full board representation
        fullBoard.or(fullBlack); fullBoard.or(fullWhite);
        //System.out.printf("%d - ",counter);

        System.out.println("BoardInitializer test cases:");
        //0
        System.out.printf("%d - ",counter);
        System.out.println(blackPawns.toString().equals("{8, 9, 10, 11, 12, 13, 14, 15}"));
        ++counter;

        //1
        System.out.printf("%d - ",counter);
        System.out.println(blackRooks.toString().equals("{0, 7}"));
        ++counter;

        //2
        System.out.printf("%d - ",counter);
        System.out.println(blackKnights.toString().equals("{1, 6}"));
        ++counter;

        //3
        System.out.printf("%d - ",counter);
        System.out.println(blackBishops.toString().equals("{2, 5}"));
        ++counter;

        //4
        System.out.printf("%d - ",counter);
        System.out.println(blackQueen.toString().equals("{3}"));
        ++counter;

        //5
        System.out.printf("%d - ",counter);
        System.out.println(blackKing.toString().equals("{4}"));
        ++counter;

        //6
        System.out.printf("%d - ",counter);
        System.out.println(whitePawns.toString().equals("{48, 49, 50, 51, 52, 53, 54, 55}"));
        ++counter;

        //7
        System.out.printf("%d - ",counter);
        System.out.println(whiteRooks.toString().equals("{56, 63}"));
        ++counter;

        //8
        System.out.printf("%d - ",counter);
        System.out.println(whiteKnights.toString().equals("{57, 62}"));
        ++counter;

        //9
        System.out.printf("%d - ",counter);
        System.out.println(whiteBishops.toString().equals("{58, 61}"));
        ++counter;

        //10
        System.out.printf("%d - ",counter);
        System.out.println(whiteQueen.toString().equals("{59}"));
        ++counter;

        //11
        System.out.printf("%d - ",counter);
        System.out.println(whiteKing.toString().equals("{60}"));
        ++counter;

        //12
        System.out.printf("%d - ",counter);
        System.out.println(fullBlack.toString().equals(
                "{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}"
        ));
        ++counter;

        //13
        System.out.printf("%d - ",counter);
        System.out.println(fullWhite.toString().equals(
                "{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63}"
        ));
        ++counter;

        //14
        System.out.printf("%d - ",counter);
        System.out.println(fullBoard.toString().equals(
                "{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, " +
                        "48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63}"
        ));

        //15
        counter = 0;
        System.out.printf("BoardValue case\n%d - ",counter);
        BoardEvaluator b = new BoardEvaluator();
        System.out.println(b.totalBoardValue(
                whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueen, whiteKing,
                blackPawns, blackRooks, blackKnights, blackBishops, blackQueen, blackKing) == 0);
    }

    public static void caseMovement(){
        BitBoard blackPawns, blackRooks, blackKnights, blackBishops, blackQueen, blackKing;
        BitBoard whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueen, whiteKing;
        BitSet fullBlack = new BitSet(64);
        BitSet fullWhite = new BitSet(64);
        BitSet fullBoard = new BitSet(64);
        ChessMoveGenerator move = new ChessMoveGenerator();
        BoardInitializer ini = new BoardInitializer(new String[]{
                "p", " ", " ", " ", " ", " ", " ", " ",
                " ", "P", " ", " ", " ", " ", " ", "K",
                "q", "q", "q", "q", " ", " ", "q", "q",
                "R", "q", "Q", " ", "q", " ", "q", "B",
                "q", "q", "q", "q", "N", " ", "q", "q",
                "r", " ", " ", " ", " ", " ", " ", "r",
                "r", " ", " ", " ", " ", " ", " ", "P",
                " ", " ", " ", " ", " ", " ", " ", " "
        }, 64);

        int counter = 0;

        //Values in Centipawns
        //Black "Suit" locations
        blackPawns = ini.generateBitset("p", 100);
        blackRooks = ini.generateBitset("r", 500);
        blackKnights = ini.generateBitset("n", 300);
        blackBishops = ini.generateBitset("b", 300);
        blackQueen = ini.generateBitset("q", 900);
        blackKing = ini.generateBitset("k", 600000);
        //White "Suit" locations
        whitePawns = ini.generateBitset("P", -100);
        whiteRooks = ini.generateBitset("R", -500);
        whiteKnights = ini.generateBitset("N", -300);
        whiteBishops = ini.generateBitset("B", -300);
        whiteQueen = ini.generateBitset("Q", -900);
        whiteKing = ini.generateBitset("K", -600000);


        //Full representation of black pieces
        fullBlack.or(blackPawns); fullBlack.or(blackRooks); fullBlack.or(blackKnights);
        fullBlack.or(blackQueen); fullBlack.or(blackKing); fullBlack.or(blackBishops);
        //Full representation of white pieces
        fullWhite.or(whitePawns); fullWhite.or(whiteRooks); fullWhite.or(whiteKnights);
        fullWhite.or(whiteQueen); fullWhite.or(whiteKing); fullWhite.or(whiteBishops);
        //Full board representation
        fullBoard.or(fullBlack); fullBoard.or(fullWhite);



        //7
        //8
        //9

    }



}
