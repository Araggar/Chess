import java.util.BitSet;

/**
 * Created by Tyrael on 4/29/2017.
 */
public class TestCases {

    public static void caseGenerator() {
        BitSet blackPawns, blackRooks, blackKnights, blackBishops, blackQueen, blackKing;
        BitSet whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueen, whiteKing;
        BitSet fullBlack = new BitSet(64);
        BitSet fullWhite = new BitSet(64);
        BitSet fullBoard = new BitSet(64);
        ChessMoveGenerator move = new ChessMoveGenerator();
        BoardInitializer ini = new BoardInitializer();

        int counter = 0;

        //Black "Suit" locations
        blackPawns = ini.generateBitset("p");
        blackRooks = ini.generateBitset("r");
        blackKnights = ini.generateBitset("n");
        blackBishops = ini.generateBitset("b");
        blackQueen = ini.generateBitset("q");
        blackKing = ini.generateBitset("k");
        //White "Suit" locations
        whitePawns = ini.generateBitset("P");
        whiteRooks = ini.generateBitset("R");
        whiteKnights = ini.generateBitset("N");
        whiteBishops = ini.generateBitset("B");
        whiteQueen = ini.generateBitset("Q");
        whiteKing = ini.generateBitset("K");


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
    }

    public static void caseMovement(){
        BitSet blackPawns, blackRooks, blackKnights, blackBishops, blackQueen, blackKing;
        BitSet whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueen, whiteKing;
        BitSet fullBlack = new BitSet(64);
        BitSet fullWhite = new BitSet(64);
        BitSet fullBoard = new BitSet(64);
        ChessMoveGenerator move = new ChessMoveGenerator();
        BoardInitializer ini = new BoardInitializer(new String[]{
                " ", " ", " ", " ", " ", " ", " ", " ",
                " ", " ", " ", " ", " ", " ", " ", " ",
                "q", "q", "q", "q", " ", " ", "q", "q",
                "R", "q", "Q", " ", "q", " ", "q", "B",
                "q", "q", "q", "q", "N", " ", "q", "q",
                "r", " ", " ", " ", " ", " ", " ", "r",
                "r", " ", " ", " ", " ", " ", " ", "P",
                " ", " ", " ", " ", " ", " ", " ", " "
        }, 64);

        int counter = 0;

        //Black "Suit" locations
        blackPawns = ini.generateBitset("p");
        blackRooks = ini.generateBitset("r");
        blackKnights = ini.generateBitset("n");
        blackBishops = ini.generateBitset("b");
        blackQueen = ini.generateBitset("q");
        blackKing = ini.generateBitset("k");
        //White "Suit" locations
        whitePawns = ini.generateBitset("P");
        whiteRooks = ini.generateBitset("R");
        whiteKnights = ini.generateBitset("N");
        whiteBishops = ini.generateBitset("B");
        whiteQueen = ini.generateBitset("Q");
        whiteKing = ini.generateBitset("K");


        //Full representation of black pieces
        fullBlack.or(blackPawns); fullBlack.or(blackRooks); fullBlack.or(blackKnights);
        fullBlack.or(blackQueen); fullBlack.or(blackKing); fullBlack.or(blackBishops);
        //Full representation of white pieces
        fullWhite.or(whitePawns); fullWhite.or(whiteRooks); fullWhite.or(whiteKnights);
        fullWhite.or(whiteQueen); fullWhite.or(whiteKing); fullWhite.or(whiteBishops);
        //Full board representation
        fullBoard.or(fullBlack); fullBoard.or(fullWhite);


        System.out.println("Movement Cases");

        //0
        System.out.printf("%d - ",counter);
        System.out.println(move.queenMovement(whiteQueen, fullWhite, fullBlack).toString().equals("{17, 18, 19, 25, 27, 28, 33, 34, 35}"));
        counter++;
        //1
        System.out.printf("%d - ",counter);
        System.out.println(move.rookMovement(whiteRooks, fullWhite, fullBlack).cardinality()==3);
        counter++;
        //2
        System.out.printf("%d - ",counter);
        System.out.println(move.bishopMovement(whiteBishops, fullWhite, fullBlack).cardinality()==2);
        counter++;
        //3
        System.out.printf("%d - ",counter);
        System.out.println(move.knightMovement(whiteKnights, fullWhite, fullBlack).toString().equals("{19, 21, 30, 42, 46, 51, 53}"));
        counter++;
        //4
        //5
        //6
        //7
        //8
        //9

    }



}
