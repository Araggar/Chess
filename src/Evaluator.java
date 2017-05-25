/**
 * Created by Tyrael on 5/25/2017.
 */
public interface Evaluator {
    int totalBoardValue(BitBoard whitePawns, BitBoard whiteRooks, BitBoard whiteKnights,
                        BitBoard whiteBishops, BitBoard whiteQueens, BitBoard whiteKing,
                        BitBoard blackPawns, BitBoard blackRooks, BitBoard blackKnights,
                        BitBoard blackBishops, BitBoard blackQueens, BitBoard blackKing);
}
