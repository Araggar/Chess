import java.util.BitSet;

public interface BoardGame {

    void move(int indexFrom, int indexTo,BitBoard boardPiece);

    public int gameLoop(int fromSq, int toSq);

    void saveState();

    void loadState();

    int[] blackPawnIndexes();

    int[] blackRookIndexes();

    int[] blackKnightIndexes();

    int[] blackBishopIndexes();

    int[] blackQueenIndexes();

    int[] blackKingIndexes();

    int[] whitePawnIndexes();

    int[] whiteRookIndexes();

    int[] whiteKnightIndexes();

    int[] whiteBishopIndexes();

    int[] whiteQueenIndexes();

    int[] whiteKingIndexes();

    BitSet movementIndexes(int location);
}
