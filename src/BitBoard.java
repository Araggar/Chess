import java.util.BitSet;

public class BitBoard extends BitSet implements BoardRepresentation {
    private int pieceValue;
    private enums.Piece pieceType;

    BitBoard(int size, int pieceValue, enums.Piece pieceType){
        super(size);
        this.pieceValue = pieceValue;
        this.pieceType = pieceType;
    }

    public int boardValue(){
        return pieceValue*this.cardinality();
    }

    public enums.Piece pieceValue(){
        return pieceType;
    }
}
