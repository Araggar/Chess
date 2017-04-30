import java.util.BitSet;

/**
 * Used to generate the pieces locations using an 'n' long String representation of a board
 */
public class BoardInitializer {
    String[] initialBoard;
    int squares;

    BoardInitializer(){
        this(new String[]{
                "r", "n", "b", "q", "k", "b", "n", "r",
                "p", "p", "p", "p", "p", "p", "p", "p",
                " ", " ", " ", " ", " ", " ", " ", " ",
                " ", " ", " ", " ", " ", " ", " ", " ",
                " ", " ", " ", " ", " ", " ", " ", " ",
                " ", " ", " ", " ", " ", " ", " ", " ",
                "P", "P", "P", "P", "P", "P", "P", "P",
                "R", "N", "B", "Q", "K", "B", "N", "R"
        }, 64);
    }

    BoardInitializer(String[] initialBoard, int squares){
        this.initialBoard = initialBoard;
        this.squares = squares;
    }



    public BitSet generateBitset(String Piece){
        BitSet blackPawnBitset = new BitSet(64);
        for (int i = 0; i < this.squares; ++i) {
            if (this.initialBoard[i].equals(Piece)) {
                blackPawnBitset.set(i);
            }
        }
        return blackPawnBitset;
    }


}
