import java.util.BitSet;

/**
 * Created by Tyrael on 5/4/2017.
 */
public class Game {
    BitBoard blackPawns, blackRooks, blackKnights, blackBishops, blackQueens, blackKing;
    BitBoard whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueens, whiteKing;
    BitSet blackBoard = new BitSet(64);
    BitSet whiteBoard = new BitSet(64);
    BitSet fullBoard = new BitSet(64);
    ChessMoveGenerator movGen = new ChessMoveGenerator();
    BoardEvaluator eval = new BoardEvaluator();

    Game(BitBoard blackPawns, BitBoard blackRooks, BitBoard blackKnights, BitBoard blackBishops,
         BitBoard blackQueen, BitBoard blackKing, BitBoard whitePawns, BitBoard whiteRooks, BitBoard whiteKnights,
         BitBoard whiteBishops, BitBoard whiteQueen, BitBoard whiteKing){

        //Values in Centipawns
        //Black "Suit" locations
        this.blackPawns = blackPawns;
        this.blackRooks = blackRooks;
        this.blackKnights = blackKnights;
        this.blackBishops = blackBishops;
        this.blackQueens = blackQueen;
        this.blackKing = blackKing;
        //White "Suit" locations
        this.whitePawns = whitePawns;
        this.whiteRooks = whiteRooks;
        this.whiteKnights = whiteKnights;
        this.whiteBishops = whiteBishops;
        this.whiteQueens = whiteQueen;
        this.whiteKing = whiteKing;
    }

    Game(BoardInitializer ini){

        //Values in Centipawns
        //Black "Suit" locations
        this.blackPawns = ini.generateBitset("p", 100);
        this.blackRooks = ini.generateBitset("r", 500);
        this.blackKnights = ini.generateBitset("n", 300);
        this.blackBishops = ini.generateBitset("b", 300);
        this.blackQueens = ini.generateBitset("q", 900);
        this.blackKing = ini.generateBitset("k", 600000);
        //White "Suit" locations
        this.whitePawns = ini.generateBitset("P", -100);
        this.whiteRooks = ini.generateBitset("R", -500);
        this.whiteKnights = ini.generateBitset("N", -300);
        this.whiteBishops = ini.generateBitset("B", -300);
        this.whiteQueens = ini.generateBitset("Q", -900);
        this.whiteKing = ini.generateBitset("K", -600000);
    }

    Game(){
        this(new BoardInitializer());
    }

    public void move(int indexFrom, int indexTo,BitSet boardPiece,  BitSet blackPawns, BitSet blackRooks, BitSet blackKnights, BitSet blackBishops,
                     BitSet blackQueens, BitSet blackKing, BitSet whitePawns, BitSet whiteRooks, BitSet whiteKnights,
                     BitSet whiteBishops, BitSet whiteQueens, BitSet whiteKing){
        blackBishops.clear(indexFrom);
        blackPawns.clear(indexFrom);
        blackKing.clear(indexFrom);
        blackRooks.clear(indexFrom);
        blackKnights.clear(indexFrom);
        blackQueens.clear(indexFrom);

        whiteBishops.clear(indexFrom);
        whitePawns.clear(indexFrom);
        whiteKing.clear(indexFrom);
        whiteQueens.clear(indexFrom);
        whiteKnights.clear(indexFrom);
        whiteRooks.clear(indexFrom);

        boardPiece.set(indexTo);
    }

    public int finalMove(BitBoard allyPawns, BitBoard allyRooks, BitBoard allyKnights, BitBoard allyBishops,
                         BitBoard allyQueens, BitBoard allyKing, BitBoard enemyPawns, BitBoard enemyRooks, BitBoard enemyKnights,
                         BitBoard enemyBishops, BitBoard enemyQueens, BitBoard enemyKing){

        BitSet fullBlack = new BitSet(64);
        BitSet fullWhite = new BitSet(64);
        //Full representation of black pieces
        fullBlack.or(blackPawns); fullBlack.or(blackRooks); fullBlack.or(blackKnights);
        fullBlack.or(blackQueens); fullBlack.or(blackKing); fullBlack.or(blackBishops);
        //Full representation of white pieces
        fullWhite.or(whitePawns); fullWhite.or(whiteRooks); fullWhite.or(whiteKnights);
        fullWhite.or(whiteQueens); fullWhite.or(whiteKing); fullWhite.or(whiteBishops);

        int cutoff = Integer.MIN_VALUE;

        for(int i = allyQueens.nextSetBit(0); i >= 0; i = allyQueens.nextSetBit(i+1)){
            BitSet queenMov = movGen.queenMovement(i, fullWhite, fullBlack);
            for (int j = queenMov.nextSetBit(0); j >= 0; j = queenMov.nextSetBit(j + 1)){
                BitSet copyAllyPawns = (BitSet) allyPawns.clone();
                BitSet copyAllyRooks = (BitSet) allyRooks.clone();
                BitSet copyAllyKnights = (BitSet) allyKnights.clone();
                BitSet copyAllyBishops = (BitSet) allyBishops.clone();
                BitSet copyAllyQueens = (BitSet) allyQueens.clone();
                BitSet copyAllyKing = (BitSet) allyKing.clone();

                BitSet copyEnemyPawns = (BitSet) allyPawns.clone();
                BitSet copyEnemyRooks = (BitSet) allyRooks.clone();
                BitSet copyEnemyKnights = (BitSet) allyKnights.clone();
                BitSet copyEnemyBishops = (BitSet) allyBishops.clone();
                BitSet copyEnemyQueens = (BitSet) allyQueens.clone();
                BitSet copyEnemyKing = (BitSet) allyKing.clone();

                this.move(i, j, copyAllyQueens, copyAllyPawns, copyAllyRooks, copyAllyKnights, copyAllyBishops,
                        copyAllyQueens, copyAllyKing, copyEnemyPawns, copyEnemyRooks, copyEnemyKnights, copyEnemyBishops,
                        copyEnemyQueens, copyEnemyKing);
                int BoardValue = eval.totalBoardValue(copyAllyPawns,);
                //todo evaluator if


            }
        }

    }



}
