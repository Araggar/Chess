/**
 * Created by Tyrael on 4/30/2017.
 */
public class BoardEvaluator implements Evaluator{

    private int[] blackPawnPositionalValues = {
             0,  0,  0,  0,  0,  0,  0,  0,
            50, 50, 40, 40, 40, 40, 50, 50,
            10, 10, 40, 45, 45, 40, 10, 10,
             5,  5, 10, 50, 50, 10,  5,  5,
             0,  0,  0, 20, 20,  0,  0,  0,
             5, -5,-10,  0,  0,-10, -5,  5,
             5, 10, 10,-20,-20, 10, 10,  5,
             0,  0,  0,  0,  0,  0,  0,  0
    };

    private int[] blackKnightPositionalValues = {
            -50, -45, -30, -30, -30, -30, -45, -50,
            -40, -20, 0, 0, 0, 0, -20, -40,
            -30, 0, 10, 15, 15, 10, 0, -30,
            -30, 5, 15, 20, 20, 15, 5, -30,
            -30, 0, 15, 20, 20, 15, 0, -30,
            -30, 5, 10, 15, 15, 10, 5, -30,
            -40, -20, 0, 5, 5, 0, -20, -40,
            -50, -40, -30, -30, -30, -30, -40, -50,
    };

    private int[] blackBishopPositionalValues = {
            -20, -10, -10, -10, -10, -10, -10, -20,
            -10, 0, 0, 0, 0, 0, 0, -10,
            -10, 0, 5, 10, 10, 5, 0, -10,
            -10, 5, 5, 10, 10, 5, 5, -10,
            -10, 0, 10, 10, 10, 10, 0, -10,
            -10, 10, 10, 10, 10, 10, 10, -10,
            -10, 5, 0, 0, 0, 0, 5, -10,
            -20, -10, -10, -10, -10, -10, -10, -20,
    };

    private int[] blackRookPositionalValues = {
            0, 0, 0, 0, 0, 0, 0, 0,
            5, 10, 10, 10, 10, 10, 10, 5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            -5, 0, 0, 0, 0, 0, 0, -5,
            0, 0, 0, 5, 5, 0, 0, 0
    };

    private int[] blackQueensPositionalValues = {
            -20,-10,-10, -5, -5,-10,-10,-20,
            -10,  0,  0,  0,  0,  0,  0,-10,
            -10,  0,  5,  5,  5,  5,  0,-10,
             -5,  0,  5,  5,  5,  5,  0, -5,
              0,  0,  5,  5,  5,  5,  0, -5,
            -10,  5,  5,  5,  5,  5,  0,-10,
            -10,  0,  5,  0,  0,  0,  0,-10,
            -20,-10,-10, -5, -5,-10,-10,-20
    };

    private int[] blackKingPositionalValues = {
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -30, -40, -40, -50, -50, -40, -40, -30,
            -20, -30, -30, -40, -40, -30, -30, -20,
            -10, -20, -20, -20, -20, -20, -20, -10,
             20,  20,   0,   0,   0,   0,  20,  20,
             20,  30,  10,   0,   0,  10,  30,  20
    };

    private int[] whitePawnPositionalValues = {
              0,  0,  0,  0,  0,  0,  0,  0,
             -5,-10,-10, 20, 20,-10,-10, -5,
             -5,  5, 10,  0,  0, 10,  5, -5,
              0,  0,  0,-20,-20,  0,  0,  0,
             -5, -5,-10,-25,-25,-10, -5, -5,
            -10,-10,-20,-30,-30,-20,-10,-10,
            -50,-50,-50,-50,-50,-50,-50,-50,
              0,  0,  0,  0,  0,  0,  0,  0
    };

    private int[] whiteKnightPositionalValues = {
            50, 40, 30, 30, 30, 30, 40, 50,
            40, 20,  0, -5, -5,  0, 20, 40,
            30, -5,-10,-15,-15,-10, -5, 30,
            30,  0,-15,-20,-20,-15,  0, 30,
            30, -5,-15,-20,-20,-15, -5, 30,
            30,  0,-10,-15,-15,-10,  0, 30,
            40, 20,  0,  0,  0,  0, 20, 40,
            50, 40, 30, 30, 30, 30, 40, 50,
    };

    private int[] whiteBishopPositionalValues = {
            20, 10, 10, 10, 10, 10, 10, 20,
            10, -5,  0,  0,  0,  0, -5, 10,
            10,-10,-10,-10,-10,-10,-10, 10,
            10,  0,-10,-10,-10,-10,  0, 10,
            10, -5, -5,-10,-10, -5, -5, 10,
            10,  0, -5,-10,-10, -5,  0, 10,
            10,  0,  0,  0,  0,  0,  0, 10,
            20, 10, 10, 10, 10, 10, 10, 20
    };

    private int[] whiteRookPositionalValues = {
            0,  0,  0, -5, -5,  0,  0,  0,
            5,  0,  0,  0,  0,  0,  0,  5,
            5,  0,  0,  0,  0,  0,  0,  5,
            5,  0,  0,  0,  0,  0,  0,  5,
            5,  0,  0,  0,  0,  0,  0,  5,
            5,  0,  0,  0,  0,  0,  0,  5,
            -5,-10,-10,-10,-10,-10,-10, -5,
            0,  0,  0,  0,  0,  0,  0,  0
    };

    private int[] whiteQueensPositionalValues = {
            20, 10, 10,  5,  5, 10, 10, 20,
            10,  0,  0,  0,  0, -5,  0, 10,
            10,  0, -5, -5, -5, -5, -5, 10,
             5,  0, -5, -5, -5, -5,  0,  0,
             5,  0, -5, -5, -5, -5,  0,  5,
            10,  0, -5, -5, -5, -5,  0, 10,
            10,  0,  0,  0,  0,  0,  0, 10,
            20, 10, 10,  5,  5, 10, 10, 20
    };

    private int[] whiteKingPositionalValues = {
            -20,-30,-10,  0,  0,-10,-30,-20,
            -20,-20,  0,  0,  0,  0,-20,-20,
             10, 20, 20, 20, 20, 20, 20, 10,
             20, 30, 30, 40, 40, 30, 30, 20,
             30, 40, 40, 50, 50, 40, 40, 30,
             30, 40, 40, 50, 50, 40, 40, 30,
             30, 40, 40, 50, 50, 40, 40, 30,
             30, 40, 40, 50, 50, 40, 40, 30
    };


    private int blackBoardEvaluation(BitBoard pawns, BitBoard rooks, BitBoard knights,
                                     BitBoard bishops, BitBoard queens, BitBoard king){
        int totalValue = pawns.boardValue() + rooks.boardValue() + knights.boardValue()
                + bishops.boardValue() + queens.boardValue() + king.boardValue();
        for(int index = pawns.nextSetBit(0); index > -1; index = pawns.nextSetBit(index+1)){
            totalValue = totalValue + this.blackPawnPositionalValues[index];
        }
        for(int index = rooks.nextSetBit(0); index > -1; index = rooks.nextSetBit(index+1)){
            totalValue = totalValue + this.blackRookPositionalValues[index];
        }
        for(int index = knights.nextSetBit(0); index > -1; index = knights.nextSetBit(index+1)){
            totalValue = totalValue + this.blackKnightPositionalValues[index];
        }
        for(int index = bishops.nextSetBit(0); index > -1; index = bishops.nextSetBit(index+1)){
            totalValue = totalValue + this.blackBishopPositionalValues[index];
        }
        for(int index = queens.nextSetBit(0); index > -1; index = queens.nextSetBit(index+1)){
            totalValue = totalValue + this.blackQueensPositionalValues[index];
        }
        for(int index = king.nextSetBit(0); index > -1; index = king.nextSetBit(index+1)){
            totalValue = totalValue + this.blackKingPositionalValues[index];
        }
        return totalValue;
    }

    private int whiteBoardEvaluation(BitBoard pawns, BitBoard rooks, BitBoard knights,
                                    BitBoard bishops, BitBoard queens, BitBoard king){
        int totalValue =pawns.boardValue() + rooks.boardValue() + knights.boardValue()
                + bishops.boardValue() + queens.boardValue() + king.boardValue();
        for(int index = pawns.nextSetBit(0); index > -1; index = pawns.nextSetBit(index+1)){
            totalValue = totalValue + this.whitePawnPositionalValues[index];
        }
        for(int index = rooks.nextSetBit(0); index > -1; index = rooks.nextSetBit(index+1)){
            totalValue = totalValue + this.whiteRookPositionalValues[index];
        }
        for(int index = knights.nextSetBit(0); index > -1; index = knights.nextSetBit(index+1)){
            totalValue = totalValue + this.whiteKnightPositionalValues[index];
        }
        for(int index = bishops.nextSetBit(0); index > -1; index = bishops.nextSetBit(index+1)){
            totalValue = totalValue + this.whiteBishopPositionalValues[index];
        }
        for(int index = queens.nextSetBit(0); index > -1; index = queens.nextSetBit(index+1)){
            totalValue = totalValue + this.whiteQueensPositionalValues[index];
        }
        for(int index = king.nextSetBit(0); index > -1; index = king.nextSetBit(index+1)){
            totalValue = totalValue + this.whiteKingPositionalValues[index];
        }
        return totalValue;
    }

    public int totalBoardValue(BitBoard whitePawns, BitBoard whiteRooks, BitBoard whiteKnights,
                               BitBoard whiteBishops, BitBoard whiteQueens, BitBoard whiteKing,
                               BitBoard blackPawns, BitBoard blackRooks, BitBoard blackKnights,
                               BitBoard blackBishops, BitBoard blackQueens, BitBoard blackKing){

        return this.blackBoardEvaluation(blackPawns, blackRooks, blackKnights, blackBishops, blackQueens, blackKing)
                + this.whiteBoardEvaluation( whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueens, whiteKing);
    }
}
