import javax.swing.*;
import java.awt.*;
import java.util.BitSet;

/**
 * Created by Tyrael on 5/4/2017.
 */
public class ChessGame {
    BitBoard blackPawns, blackRooks, blackKnights, blackBishops, blackQueens, blackKing;
    BitBoard whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueens, whiteKing;
    BitSet blackBoard = new BitSet(64);
    BitSet whiteBoard = new BitSet(64);
    BitSet fullBoard = new BitSet(64);
    ChessMoveGenerator movGen = new ChessMoveGenerator();
    BoardEvaluator eval = new BoardEvaluator();

    ChessGame(BitBoard blackPawns, BitBoard blackRooks, BitBoard blackKnights, BitBoard blackBishops,
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

        whiteBoard.or(this.whitePawns); whiteBoard.or(this.whiteRooks); whiteBoard.or(this.whiteKnights);
        whiteBoard.or(this.whiteQueens); whiteBoard.or(this.whiteKing); whiteBoard.or(this.whiteBishops);

        blackBoard.or(this.blackPawns); blackBoard.or(this.blackRooks); blackBoard.or(this.blackKnights);
        blackBoard.or(this.blackQueens); blackBoard.or(this.blackKing); blackBoard.or(this.blackBishops);

    }

    ChessGame(BoardInitializer ini){

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

        whiteBoard.or(this.whitePawns); whiteBoard.or(this.whiteRooks); whiteBoard.or(this.whiteKnights);
        whiteBoard.or(this.whiteQueens); whiteBoard.or(this.whiteKing); whiteBoard.or(this.whiteBishops);

        blackBoard.or(this.blackPawns); blackBoard.or(this.blackRooks); blackBoard.or(this.blackKnights);
        blackBoard.or(this.blackQueens); blackBoard.or(this.blackKing); blackBoard.or(this.blackBishops);

    }

    ChessGame(){
        this(new BoardInitializer());
    }

    public void move(int indexFrom, int indexTo,BitSet boardPiece){
        this.blackBishops.clear(indexFrom);
        this.blackPawns.clear(indexFrom);
        this.blackKing.clear(indexFrom);
        this.blackRooks.clear(indexFrom);
        this.blackKnights.clear(indexFrom);
        this.blackQueens.clear(indexFrom);

        this.whiteBishops.clear(indexFrom);
        this.whitePawns.clear(indexFrom);
        this.whiteKing.clear(indexFrom);
        this.whiteQueens.clear(indexFrom);
        this.whiteKnights.clear(indexFrom);
        this.whiteRooks.clear(indexFrom);

        boardPiece.set(indexTo);
    }

    public int finalMove(){

        BitSet fullBlack = new BitSet(64);
        BitSet fullWhite = new BitSet(64);
        //Full representation of black pieces
        fullBlack.or(blackPawns); fullBlack.or(blackRooks); fullBlack.or(blackKnights);
        fullBlack.or(blackQueens); fullBlack.or(blackKing); fullBlack.or(blackBishops);
        //Full representation of white pieces
        fullWhite.or(whitePawns); fullWhite.or(whiteRooks); fullWhite.or(whiteKnights);
        fullWhite.or(whiteQueens); fullWhite.or(whiteKing); fullWhite.or(whiteBishops);

        int cutoff = Integer.MAX_VALUE;

        for(int i = whiteQueens.nextSetBit(0); i >= 0; i = whiteQueens.nextSetBit(i+1)){
            BitSet movements = movGen.queenMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());
                ChessGameCopy.move(i, j, ChessGameCopy.whiteQueens);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whitePawns.nextSetBit(0); i >= 0; i = whitePawns.nextSetBit(i+1)){
            BitSet movements = movGen.whitePawnMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

                ChessGameCopy.move(i, j, ChessGameCopy.whitePawns);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteBishops.nextSetBit(0); i >= 0; i = whiteBishops.nextSetBit(i+1)){
            BitSet movements = movGen.bishopMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

                ChessGameCopy.move(i, j, ChessGameCopy.whiteBishops);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteKnights.nextSetBit(0); i >= 0; i = whiteKnights.nextSetBit(i+1)){
            BitSet movements = movGen.knightMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

                ChessGameCopy.move(i, j, ChessGameCopy.whiteKnights);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteRooks.nextSetBit(0); i >= 0; i = whiteRooks.nextSetBit(i+1)){
            BitSet movements = movGen.rookMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

                ChessGameCopy.move(i, j, ChessGameCopy.whiteRooks);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteKing.nextSetBit(0); i >= 0; i = whiteKing.nextSetBit(i+1)){
            BitSet movements = movGen.kingMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

                ChessGameCopy.move(i, j, ChessGameCopy.whiteKing);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }
        return cutoff;
    }


    public void simulate2ply(){
        BitSet fullBlack = new BitSet(64);
        BitSet fullWhite = new BitSet(64);
        //Full representation of black pieces
        fullBlack.or(blackPawns); fullBlack.or(blackRooks); fullBlack.or(blackKnights);
        fullBlack.or(blackQueens); fullBlack.or(blackKing); fullBlack.or(blackBishops);
        //Full representation of white pieces
        fullWhite.or(whitePawns); fullWhite.or(whiteRooks); fullWhite.or(whiteKnights);
        fullWhite.or(whiteQueens); fullWhite.or(whiteKing); fullWhite.or(whiteBishops);

        int cutoff = Integer.MIN_VALUE;
        Object[] move = new Object[3];

        for(int i = blackQueens.nextSetBit(0); i >= 0; i = blackQueens.nextSetBit(i+1)){
            BitSet movements = movGen.queenMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());
                ChessGameCopy.move(i, j, ChessGameCopy.whiteQueens);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackQueens;
                }
            }
        }

        for(int i = blackPawns.nextSetBit(0); i >= 0; i = blackPawns.nextSetBit(i+1)){
            BitSet movements = movGen.blackPawnMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());
                ChessGameCopy.move(i, j, ChessGameCopy.blackPawns);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackPawns;
                }
            }
        }

        for(int i = blackKnights.nextSetBit(0); i >= 0; i = blackKnights.nextSetBit(i+1)){
            BitSet movements = movGen.knightMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());
                ChessGameCopy.move(i, j, ChessGameCopy.blackKnights);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackKnights;
                }
            }
        }

        for(int i = blackBishops.nextSetBit(0); i >= 0; i = blackBishops.nextSetBit(i+1)){
            BitSet movements = movGen.bishopMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());
                ChessGameCopy.move(i, j, ChessGameCopy.blackBishops);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackBishops;
                }
            }
        }

        for(int i = blackRooks.nextSetBit(0); i >= 0; i = blackRooks.nextSetBit(i+1)){
            BitSet movements = movGen.rookMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());
                ChessGameCopy.move(i, j, ChessGameCopy.blackRooks);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackRooks;
                }
            }
        }

        for(int i = blackKing.nextSetBit(0); i >= 0; i = blackKing.nextSetBit(i+1)) {
            BitSet movements = movGen.kingMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)) {
                ChessGame ChessGameCopy = new ChessGame((BitBoard) blackPawns.clone(), (BitBoard) blackRooks.clone(), (BitBoard) blackKnights.clone(),
                        (BitBoard) blackBishops.clone(), (BitBoard) blackQueens.clone(), (BitBoard) blackKing.clone(), (BitBoard) whitePawns.clone(), (BitBoard) whiteRooks.clone(),
                        (BitBoard) whiteKnights.clone(), (BitBoard) whiteBishops.clone(), (BitBoard) whiteQueens.clone(), (BitBoard) whiteKing.clone());
                ChessGameCopy.move(i, j, ChessGameCopy.blackKing);
                int boardValue = ChessGameCopy.finalMove();
                if (boardValue > cutoff) {
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackKing;
                }
            }
        }

        this.move((int)move[0], (int)move[1],(BitBoard) move[2]);
        System.out.printf("From:%d To:%d\n", move[0], move[1]);
    }

    public void updateBoard(JButton[] pieces){
        for(int i = 0; i < 64; ++i){
            if ((i % 2 != 0 && (i / 8) % 2 == 0) || (i % 2 == 0 && (i / 8) % 2 != 0)) {
                pieces[i].setBackground(new Color(0, 0, 0));
            } else {
                pieces[i].setBackground(new Color(255, 255, 255));
            }
            if(blackBoard.get(i)){
                pieces[i].setBackground(new Color(255,0,0));
            }else{
                if(whiteBoard.get(i)){
                    pieces[i].setBackground(new Color(0,0,255));
                }
            }
        }
    }
}
