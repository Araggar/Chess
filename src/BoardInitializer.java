import java.util.BitSet;

public class BoardInitializer {
    private String[] initialBoard;
    private int squares;

    public BoardInitializer(){
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

    public BoardInitializer(String[] initialBoard, int squares){
        this.initialBoard = initialBoard;
        this.squares = squares;
    }


    public BitBoard generateBitset(String Piece, int value, enums.Piece type){
        BitBoard pieceBitboard = new BitBoard(this.squares, value, type);
        for (int i = 0; i < this.squares; ++i) {
            if (this.initialBoard[i].equals(Piece)) {
                pieceBitboard.set(i);
            }
        }
        return pieceBitboard;
    }
}
