import java.util.BitSet;

/**
 * Created by Tyrael on 4/30/2017.
 */
public class BitBoard extends BitSet {
    private int pieceValue;

    BitBoard(int size, int pieceValue){
        super(size);
        this.pieceValue = pieceValue;
    }

    public int boardValue(){
        return pieceValue*this.cardinality();
    }
}
