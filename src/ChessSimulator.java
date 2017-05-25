import java.util.BitSet;

/**
 * Created by Tyrael on 5/25/2017.
 */
public class ChessSimulator {
    int[] bestMove(ChessGame chessGame){
        BitSet fullBlack = new BitSet(64);
        BitSet fullWhite = new BitSet(64);
        //Full representation of black pieces
        fullBlack.or(chessGame.blackPawns); fullBlack.or(chessGame.blackRooks); fullBlack.or(chessGame.blackKnights);
        fullBlack.or(chessGame.blackQueens); fullBlack.or(chessGame.blackKing); fullBlack.or(chessGame.blackBishops);
        //Full representation of white pieces
        fullWhite.or(chessGame.whitePawns); fullWhite.or(chessGame.whiteRooks); fullWhite.or(chessGame.whiteKnights);
        fullWhite.or(chessGame.whiteQueens); fullWhite.or(chessGame.whiteKing); fullWhite.or(chessGame.whiteBishops);

        int cutoff = Integer.MIN_VALUE;
        int[] move = new int[2];

        for(int i = chessGame.blackQueens.nextSetBit(0); i >= 0; i = chessGame.blackQueens.nextSetBit(i+1)){
            BitSet movements = chessGame.queenMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = chessGame.copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.whiteQueens);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                }
            }
        }

        for(int i = chessGame.blackPawns.nextSetBit(0); i >= 0; i = chessGame.blackPawns.nextSetBit(i+1)){
            BitSet movements = chessGame.blackPawnMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = chessGame.copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.blackPawns);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                }
            }
        }

        for(int i = chessGame.blackKnights.nextSetBit(0); i >= 0; i = chessGame.blackKnights.nextSetBit(i+1)){
            BitSet movements = chessGame.knightMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = chessGame.copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.blackKnights);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                }
            }
        }

        for(int i = chessGame.blackBishops.nextSetBit(0); i >= 0; i = chessGame.blackBishops.nextSetBit(i+1)){
            BitSet movements = chessGame.bishopMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = chessGame.copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.blackBishops);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                }
            }
        }

        for(int i = chessGame.blackRooks.nextSetBit(0); i >= 0; i = chessGame.blackRooks.nextSetBit(i+1)){
            BitSet movements = chessGame.rookMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = chessGame.copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.blackRooks);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                }
            }
        }

        for(int i = chessGame.blackKing.nextSetBit(0); i >= 0; i = chessGame.blackKing.nextSetBit(i+1)) {
            BitSet movements = chessGame.kingMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)) {
                ChessGame ChessGameCopy = chessGame.copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.blackKing);
                int boardValue = ChessGameCopy.finalMove();
                if (boardValue > cutoff) {
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                }
            }
        }
        System.out.printf("From:%d To:%d\n", move[0], move[1]);
        return(move);
    }
}
