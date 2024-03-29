import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.BitSet;
import java.util.Scanner;

/**
 * Created by Tyrael on 5/4/2017.
 */
 class ChessGame implements BoardGame {
    private BitBoard blackPawns, blackRooks, blackKnights, blackBishops, blackQueens, blackKing;
    private BitBoard whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueens, whiteKing;
    private BitSet enPassant = new BitSet(64);
    private int enPassantType; //0 - None; 1 - White; 2 - Black
    private BitSet blackBoard = new BitSet(64);
    private BitSet whiteBoard = new BitSet(64);
    private BitSet fullBoard = new BitSet(64);
    boolean whiteCastleRight, blackCastleLeft, blackCastleRight, whiteCastleLeft;
    private Scanner scannerIn;
    private String saveAs, loadFrom;
    private ObjectOutputStream writer;

    private ChessGame(
            BitBoard blackPawns, BitBoard blackRooks, BitBoard blackKnights, BitBoard blackBishops,
            BitBoard blackQueen, BitBoard blackKing, BitBoard whitePawns, BitBoard whiteRooks, BitBoard whiteKnights,
            BitBoard whiteBishops, BitBoard whiteQueen, BitBoard whiteKing, BitSet enPassant, int enPassantType, boolean whiteCastleRight,
            boolean whiteCastleLeft, boolean blackCastleRight, boolean blackCastleLeft
              ){
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

        this.enPassant = enPassant;
        this.enPassantType = enPassantType;
        this.whiteCastleLeft= whiteCastleLeft;
        this.whiteCastleRight = whiteCastleRight;
        this.blackCastleLeft = blackCastleLeft;
        this.blackCastleRight = blackCastleRight;


        whiteBoard.or(this.whitePawns); whiteBoard.or(this.whiteRooks); whiteBoard.or(this.whiteKnights);
        whiteBoard.or(this.whiteQueens); whiteBoard.or(this.whiteKing); whiteBoard.or(this.whiteBishops);

        blackBoard.or(this.blackPawns); blackBoard.or(this.blackRooks); blackBoard.or(this.blackKnights);
        blackBoard.or(this.blackQueens); blackBoard.or(this.blackKing); blackBoard.or(this.blackBishops);

    }

    ChessGame(BoardInitializer ini){

        //Values in Centipawns
        //Black "Suit" locations
        this.blackPawns = ini.generateBitset("p", 100, enums.Piece.PAWN);
        this.blackRooks = ini.generateBitset("r", 500, enums.Piece.ROOK);
        this.blackKnights = ini.generateBitset("n", 300, enums.Piece.KNIGHT);
        this.blackBishops = ini.generateBitset("b", 300, enums.Piece.BISHOP);
        this.blackQueens = ini.generateBitset("q", 900, enums.Piece.QUEEN);
        this.blackKing = ini.generateBitset("k", 600000, enums.Piece.KING);
        //White "Suit" locations
        this.whitePawns = ini.generateBitset("P", -100, enums.Piece.PAWN);
        this.whiteRooks = ini.generateBitset("R", -500, enums.Piece.ROOK);
        this.whiteKnights = ini.generateBitset("N", -300, enums.Piece.KNIGHT);
        this.whiteBishops = ini.generateBitset("B", -301, enums.Piece.BISHOP);
        this.whiteQueens = ini.generateBitset("Q", -900, enums.Piece.QUEEN);
        this.whiteKing = ini.generateBitset("K", -600000, enums.Piece.KING);

        whiteBoard.or(this.whitePawns); whiteBoard.or(this.whiteRooks); whiteBoard.or(this.whiteKnights);
        whiteBoard.or(this.whiteQueens); whiteBoard.or(this.whiteKing); whiteBoard.or(this.whiteBishops);

        blackBoard.or(this.blackPawns); blackBoard.or(this.blackRooks); blackBoard.or(this.blackKnights);
        blackBoard.or(this.blackQueens); blackBoard.or(this.blackKing); blackBoard.or(this.blackBishops);

        this.whiteCastleLeft= true;
        this.whiteCastleRight = true;
        this.blackCastleLeft = true;
        this.blackCastleRight = true;

    }

    ChessGame(){
        this(new BoardInitializer());
    }

    public void move(int indexFrom, int indexTo,BitBoard boardPiece){
        this.blackBishops.clear(indexFrom);
        this.blackRooks.clear(indexFrom);
        this.blackKnights.clear(indexFrom);
        this.blackQueens.clear(indexFrom);

        this.whiteBishops.clear(indexFrom);
        this.whiteQueens.clear(indexFrom);
        this.whiteKnights.clear(indexFrom);
        this.whiteRooks.clear(indexFrom);

        this.blackBishops.clear(indexTo);
        this.blackPawns.clear(indexTo);
        this.blackKing.clear(indexTo);
        this.blackRooks.clear(indexTo);
        this.blackKnights.clear(indexTo);
        this.blackQueens.clear(indexTo);

        this.whiteBishops.clear(indexTo);
        this.whitePawns.clear(indexTo);
        this.whiteKing.clear(indexTo);
        this.whiteQueens.clear(indexTo);
        this.whiteKnights.clear(indexTo);
        this.whiteRooks.clear(indexTo);

        if(enPassant.get(indexTo)){
            if(enPassantType == 1){
                this.whitePawns.clear(indexTo - 8);
            }else{
                if(enPassantType == 2){
                    this.blackPawns.clear(indexTo + 8);
                }
            }
        }

        switch (boardPiece.pieceValue()){

            case PAWN:
                if(this.in(indexFrom, new int[]{16,17,18,19,20,21,22,23,48,49,50,51,52,53,54,55}) &&
                        this.in(indexTo, new int[]{8,9,10,11,12,13,14,15,32,33,34,35,36,37,38,39})){
                    if(whitePawns.get(indexFrom)){
                        enPassant.set(indexFrom-8);
                        enPassantType = 1;
                    }else{
                        enPassant.set(indexFrom+8);
                        enPassantType = 2;
                    }
                }else{
                    enPassant.clear();
                    enPassantType = 0;
                }
                break;

            case KING:
                if(whiteKing.get(indexFrom)) {
                    whiteCastleRight = false;
                    whiteCastleLeft = false;
                    if(indexFrom == 60 && indexTo == 62) {
                        whiteKing.set(62);
                        whiteRooks.set(61);
                        whiteRooks.clear(63);
                        enPassant.clear();
                        enPassantType = 0;
                        blackKing.clear(indexFrom);
                        whiteKing.clear(indexFrom);
                        whitePawns.clear(indexFrom);
                        blackPawns.clear(indexFrom);
                        updateFullBoards();
                        return;
                    }
                    if(indexFrom == 60 && indexTo == 58) {
                        whiteKing.set(58);
                        whiteRooks.set(59);
                        whiteRooks.clear(56);
                        enPassant.clear();
                        enPassantType = 0;
                        blackKing.clear(indexFrom);
                        whiteKing.clear(indexFrom);
                        whitePawns.clear(indexFrom);
                        blackPawns.clear(indexFrom);
                        updateFullBoards();
                        return;
                    }
                }else {
                    blackCastleLeft = false;
                    blackCastleRight = false;
                    if(indexFrom == 4 && indexTo == 6) {
                        blackKing.set(6);
                        blackRooks.set(5);
                        blackRooks.clear(7);
                        enPassant.clear();
                        enPassantType = 0;
                        blackKing.clear(indexFrom);
                        whiteKing.clear(indexFrom);
                        whitePawns.clear(indexFrom);
                        blackPawns.clear(indexFrom);
                        updateFullBoards();
                        return;
                    }
                    if(indexFrom == 4 && indexTo == 2) {
                        blackKing.set(2);
                        blackRooks.set(3);
                        blackRooks.clear(0);
                        enPassant.clear();
                        enPassantType = 0;
                        blackKing.clear(indexFrom);
                        whiteKing.clear(indexFrom);
                        whitePawns.clear(indexFrom);
                        blackPawns.clear(indexFrom);
                        updateFullBoards();
                        return;
                    }
                }

            case ROOK:
                if(blackRooks.get(indexFrom)){
                    if(indexFrom == 0){
                        blackCastleLeft = false;
                    }else{
                        if(indexFrom == 7){
                            blackCastleRight = false;
                        }
                    }
                }else{
                    if(indexFrom == 56){
                        whiteCastleLeft = false;
                    }else{
                        if(indexFrom == 63){
                            whiteCastleRight = false;
                        }
                    }
                }

                default:
                    enPassant.clear();
                    enPassantType = 0;
                    break;
        }
        this.blackKing.clear(indexFrom);
        this.whiteKing.clear(indexFrom);
        this.whitePawns.clear(indexFrom);
        this.blackPawns.clear(indexFrom);
        boardPiece.set(indexTo);
        updateFullBoards();
    }


    private int finalMove(){

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
            BitSet movements = queenMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.whiteQueens);
                int boardValue = ChessGameCopy.totalBoardValue();
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whitePawns.nextSetBit(0); i >= 0; i = whitePawns.nextSetBit(i+1)){
            BitSet movements = whitePawnMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();

                ChessGameCopy.move(i, j, ChessGameCopy.whitePawns);
                int boardValue = ChessGameCopy.totalBoardValue();
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteBishops.nextSetBit(0); i >= 0; i = whiteBishops.nextSetBit(i+1)){
            BitSet movements = bishopMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();

                ChessGameCopy.move(i, j, ChessGameCopy.whiteBishops);
                int boardValue = ChessGameCopy.totalBoardValue();
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteKnights.nextSetBit(0); i >= 0; i = whiteKnights.nextSetBit(i+1)){
            BitSet movements = knightMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();

                ChessGameCopy.move(i, j, ChessGameCopy.whiteKnights);
                int boardValue = ChessGameCopy.totalBoardValue();
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteRooks.nextSetBit(0); i >= 0; i = whiteRooks.nextSetBit(i+1)){
            BitSet movements = rookMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();

                ChessGameCopy.move(i, j, ChessGameCopy.whiteRooks);
                int boardValue = ChessGameCopy.totalBoardValue();
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteKing.nextSetBit(0); i >= 0; i = whiteKing.nextSetBit(i+1)){
            BitSet movements = kingMovement(i, fullWhite, 1);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();

                ChessGameCopy.move(i, j, ChessGameCopy.whiteKing);
                int boardValue = ChessGameCopy.totalBoardValue();
                cutoff = Math.min(cutoff, boardValue);
            }
        }
        return cutoff;
    }


    private int simulate2ply(){
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
            BitSet movements = queenMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
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
            BitSet movements = blackPawnMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
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
            BitSet movements = knightMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
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
            BitSet movements = bishopMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
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
            BitSet movements = rookMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
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
            BitSet movements = kingMovement(i, fullBlack, 2);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)) {
                ChessGame ChessGameCopy = copyThis();
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
        return cutoff;
    }


    private Boolean legalMove(int iFrom, int iTo){
        if(whiteBoard.get(iFrom)){
            BitBoard tempBoard = pieceFinder(iFrom);
            if(legalMovement(iFrom, iTo, tempBoard)){
                if(!inCheck(iFrom, iTo, 1)){
                    return true;
                }
            }
        }
        return false; //false if index is not a white piece, is not a legal move or leaves king in check
    }

    private Boolean inCheck(int iFrom, int iTo, int type){
        ChessGame ChessGameCopy = copyThis();
        ChessGameCopy.move(iFrom, iTo, ChessGameCopy.pieceFinder(iFrom));
        BitSet allMovements = new BitSet(64);

        if(type == 1) {
            for (int i = ChessGameCopy.blackRooks.nextSetBit(0); i >= 0; i = ChessGameCopy.blackRooks.nextSetBit(i + 1)) {
                allMovements.or(rookMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
            }

            for (int i = ChessGameCopy.blackPawns.nextSetBit(0); i >= 0; i = ChessGameCopy.blackPawns.nextSetBit(i + 1)) {
                allMovements.or(blackPawnMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
            }

            for (int i = ChessGameCopy.blackBishops.nextSetBit(0); i >= 0; i = ChessGameCopy.blackBishops.nextSetBit(i + 1)) {
                allMovements.or(bishopMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
            }

            for (int i = ChessGameCopy.blackKnights.nextSetBit(0); i >= 0; i = ChessGameCopy.blackKnights.nextSetBit(i + 1)) {
                allMovements.or(knightMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
            }

            for (int i = ChessGameCopy.blackQueens.nextSetBit(0); i >= 0; i = ChessGameCopy.blackQueens.nextSetBit(i + 1)) {
                allMovements.or(queenMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
            }

            for (int i = ChessGameCopy.blackKing.nextSetBit(0); i >= 0; i = ChessGameCopy.blackKing.nextSetBit(i + 1)) {
                allMovements.or(kingMovement(i, ChessGameCopy.blackBoard, type));
            }

            return allMovements.get(ChessGameCopy.whiteKing.nextSetBit(0));
        }else{
            for (int i = ChessGameCopy.whiteRooks.nextSetBit(0); i >= 0; i = ChessGameCopy.whiteRooks.nextSetBit(i + 1)) {
                allMovements.or(rookMovement(i, ChessGameCopy.whiteBoard, ChessGameCopy.blackBoard));
            }

            for (int i = ChessGameCopy.whitePawns.nextSetBit(0); i >= 0; i = ChessGameCopy.whitePawns.nextSetBit(i + 1)) {
                allMovements.or(whitePawnMovement(i, ChessGameCopy.whiteBoard, ChessGameCopy.blackBoard));
            }

            for (int i = ChessGameCopy.whiteBishops.nextSetBit(0); i >= 0; i = ChessGameCopy.whiteBishops.nextSetBit(i + 1)) {
                allMovements.or(bishopMovement(i, ChessGameCopy.whiteBoard, ChessGameCopy.blackBoard));
            }

            for (int i = ChessGameCopy.whiteKnights.nextSetBit(0); i >= 0; i = ChessGameCopy.whiteKnights.nextSetBit(i + 1)) {
                allMovements.or(knightMovement(i, ChessGameCopy.whiteBoard, ChessGameCopy.blackBoard));
            }

            for (int i = ChessGameCopy.whiteQueens.nextSetBit(0); i >= 0; i = ChessGameCopy.whiteQueens.nextSetBit(i + 1)) {
                allMovements.or(queenMovement(i, ChessGameCopy.whiteBoard, ChessGameCopy.blackBoard));
            }

            for (int i = ChessGameCopy.whiteKing.nextSetBit(0); i >= 0; i = ChessGameCopy.whiteKing.nextSetBit(i + 1)) {
                allMovements.or(kingMovement(i, ChessGameCopy.whiteBoard, type));
            }

            return allMovements.get(ChessGameCopy.blackKing.nextSetBit(0));
        }
    }

    private BitBoard pieceFinder(int index){
        if(whitePawns.get(index)){
            return whitePawns;
        }

        if(whiteBishops.get(index)){
            return whiteBishops;
        }

        if(whiteRooks.get(index)){
            return whiteRooks;
        }

        if(whiteKnights.get(index)){
            return whiteKnights;
        }

        if(whiteQueens.get(index)){
            return whiteQueens;
        }

        if(whiteKing.get(index)){
            return whiteKing;
        }
        return new BitBoard(0,0, enums.Piece.DEFAULT);
    }

    private BitBoard pieceFinderBlack(int index){
        if(blackPawns.get(index)){
            return blackPawns;
        }

        if(blackBishops.get(index)){
            return blackBishops;
        }

        if(blackRooks.get(index)){
            return blackRooks;
        }

        if(blackKnights.get(index)){
            return blackKnights;
        }

        if(blackQueens.get(index)){
            return blackQueens;
        }

        return blackKing;
    }

    private Boolean legalMovement(int iFrom, int iTo, BitBoard piece){
        BitSet movements;
        switch (piece.pieceValue()){
            case PAWN: movements = whitePawnMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));

            case KNIGHT: movements = knightMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));

            case BISHOP: movements = bishopMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));

            case ROOK: movements = rookMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));

            case QUEEN: movements = queenMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));

            default : movements = kingMovement(iFrom, whiteBoard, 1);
                return (movements.get(iTo));
        }
    }

    private void updateFullBoards(){
        whiteBoard = new BitSet(64);
        blackBoard = new BitSet(64);
        fullBoard = new BitSet(64);

        whiteBoard.or(this.whitePawns); whiteBoard.or(this.whiteRooks); whiteBoard.or(this.whiteKnights);
        whiteBoard.or(this.whiteQueens); whiteBoard.or(this.whiteKing); whiteBoard.or(this.whiteBishops);

        blackBoard.or(this.blackPawns); blackBoard.or(this.blackRooks); blackBoard.or(this.blackKnights);
        blackBoard.or(this.blackQueens); blackBoard.or(this.blackKing); blackBoard.or(this.blackBishops);

        fullBoard.or(whiteBoard); fullBoard.or(blackBoard);

    }

    private BitSet queenMovement(int queenIndex, BitSet allyBoard, BitSet enemyBoard) {
        BitSet movement = new BitSet(64);
        int index = queenIndex;
        if(index > -1) {
            this.sideways(index, movement, allyBoard, enemyBoard);
            this.diagonal(index, movement, allyBoard, enemyBoard);
        }
        return movement;
    }

    private BitSet rookMovement(int rookIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = rookIndex;
        if(index > -1) {
            this.sideways(index, movement, allyBoard, enemyBoard);
        }
        return movement;
    }

    private BitSet bishopMovement(int bishopIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = bishopIndex;
        if(index > -1) {
            this.diagonal(index, movement, allyBoard, enemyBoard);
        }
        return movement;
    }

    private BitSet knightMovement(int knightIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = knightIndex;
        if(index > -1) {
            if (index + 10 < 64 && index % 8 < (index + 10) % 8) {
                if(!allyBoard.get(index+10)) {
                    movement.set(index + 10);
                }
            }

            if (index - 10 > -1 && index % 8 > (index - 10) % 8) {
                if(!allyBoard.get(index - 10)) {
                    movement.set(index - 10);
                }
            }

            if (index + 17 < 64 && index % 8 < (index + 17) % 8) {
                if(!allyBoard.get(index + 17)) {
                    movement.set(index + 17);
                }
            }

            if (index - 17 > -1 && index % 8 > (index - 17) % 8) {
                if(!allyBoard.get(index - 17)) {
                    movement.set(index - 17);
                }
            }

            if (index + 15 < 64 && index % 8 > (index + 15) % 8) {
                if(!allyBoard.get(index + 15)) {
                    movement.set(index + 15);
                }
            }

            if (index - 15 > -1 && index % 8 < (index - 15) % 8) {
                if(!allyBoard.get(index - 15)) {
                    movement.set(index - 15);
                }
            }

            if (index + 6 < 64 && index % 8 > (index + 6) % 8) {
                if(!allyBoard.get(index + 6)) {
                    movement.set(index + 6);
                }
            }

            if (index - 6 > -1 && index % 8 < (index - 6) % 8) {
                if(!allyBoard.get(index - 6)) {
                    movement.set(index - 6);
                }
            }
        }
        return movement;
    }

    private BitSet kingMovement(int kingIndex, BitSet allyBoard, int type){
        BitSet movement = new BitSet(64);
        BitSet allMovements = new BitSet(64);
        int index = kingIndex;

        if(index+1 < (index/8)*8 + 8 && !allyBoard.get(index+1)){
                movement.set(index + 1);
        }

        if(index+8 < 64 && !allyBoard.get(index+8)){
                movement.set(index + 8);
        }

        if(index+9 < 64 && index%8<(index+9)%8 && !allyBoard.get(index+9)){
                movement.set(index + 9);
        }

        if(index+7 < 64 && index%8>(index+7)%8 && !allyBoard.get(index+7)){
                movement.set(index + 7);
        }

        if(index-1 > (index/8)*8 -1 && !allyBoard.get(index-1)){
                movement.set(index - 1);
        }

        if(index-8 > -1 && !allyBoard.get(index-8)){
                movement.set(index - 8);
        }

        if(index-9 > -1 && index%8>(index-9)%8 && !allyBoard.get(index-9)){
                movement.set(index - 9);
        }

        if(index-7 > -1 && index%8<(index-7)%8 && !allyBoard.get(index-7)){
                movement.set(index - 7);
        }

        if(type==1){
            if(whiteBoard.get(kingIndex)){
                if(whiteCastleRight && !fullBoard.get(61) && !fullBoard.get(62) && !inCheck(kingIndex, kingIndex, type) &&
                        !inCheck(kingIndex, 61, type) && !inCheck(kingIndex, 62, type)){
                    movement.set(62);
                }else {
                    if (whiteCastleLeft && !fullBoard.get(59) && !fullBoard.get(58) && !inCheck(kingIndex, kingIndex, type) &&
                            !inCheck(kingIndex, 59, type) && !inCheck(kingIndex, 58, type)) {
                        movement.set(58);
                    }
                }
            }else {
                if (blackCastleRight && !fullBoard.get(5) && !fullBoard.get(6) &&
                        inCheck(kingIndex, kingIndex, type) &&
                        !inCheck(kingIndex, 5, type) &&
                        !inCheck(kingIndex, 6, type)) {
                    movement.set(6);
                } else {
                    if (blackCastleLeft && !fullBoard.get(3) && !fullBoard.get(2) && !inCheck(kingIndex, kingIndex, type) &&
                            !inCheck(kingIndex, 3, type) && !inCheck(kingIndex, 2, type)) {
                        movement.set(2);
                    }
                }
            }
        }else {
            for (int i = whiteRooks.nextSetBit(0); i >= 0; i = whiteRooks.nextSetBit(i + 1)) {
                allMovements.or(rookMovement(i, whiteBoard, blackBoard));
            }

            for (int i = whitePawns.nextSetBit(0); i >= 0; i = whitePawns.nextSetBit(i + 1)) {
                allMovements.or(whitePawnMovement(i, whiteBoard, blackBoard));
            }

            for (int i = whiteBishops.nextSetBit(0); i >= 0; i = whiteBishops.nextSetBit(i + 1)) {
                allMovements.or(bishopMovement(i, whiteBoard, blackBoard));
            }

            for (int i = whiteKnights.nextSetBit(0); i >= 0; i = whiteKnights.nextSetBit(i + 1)) {
                allMovements.or(knightMovement(i, whiteBoard, blackBoard));
            }

            for (int i = whiteQueens.nextSetBit(0); i >= 0; i = whiteQueens.nextSetBit(i + 1)) {
                allMovements.or(queenMovement(i, whiteBoard, blackBoard));
            }
            for (int i = allMovements.nextSetBit(0); i > -1; i = allMovements.nextSetBit(i + 1)) {
                movement.clear(i);
            }
        }
        return movement;
    }

    private BitSet blackPawnMovement(int pawnIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = pawnIndex;
        if(index >- 1) {
            if(index+8 < 64 && !allyBoard.get(index+8) && !enemyBoard.get(index+8)){
                movement.set(index+8);
            }

            if(index+9 < 64 && index%8<(index+9)%8 && enemyBoard.get(index+9)){
                movement.set(index+9);
            }

            if(index+7 < 64 && index%8>(index+7)%8 && enemyBoard.get(index+7)){
                movement.set(index+7);
            }

            if(index+16 < 64 && !allyBoard.get(index+8) && !enemyBoard.get(index+8) && !allyBoard.get(index+16) && !enemyBoard.get(index+16) && this.in(index, new int[]{8,9,10,11,12,13,14,15})){
                movement.set(index+16);
            }
            if(index+7 < 64 && index%8>(index+7)%8 && enPassant.get(index+7) && enPassantType == 1){
                movement.set(index+7);
            }
            if(index+9 < 64 && index%8<(index+9)%8 && enPassant.get(index+9) && enPassantType == 1){
                movement.set(index+9);
            }


        }
        return movement;
    }

    private BitSet whitePawnMovement(int pawnIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = pawnIndex;
        if(index >- 1) {
            if(index-8 > -1 && !allyBoard.get(index-8) && !enemyBoard.get(index-8)){
                movement.set(index-8);
            }

            if(index-9 > -1 && index%8>(index-9)%8 && enemyBoard.get(index-9)){
                movement.set(index-9);
            }

            if(index-7 > -1 && index%8<(index-7)%8 && enemyBoard.get(index-7)){
                movement.set(index-7);
            }
            if(index-16 > -1 && !allyBoard.get(index-8) && !enemyBoard.get(index-8) && !allyBoard.get(index-16) && !enemyBoard.get(index-16) && this.in(index, new int[]{48,49,50,51,52,53,54,55})){
                movement.set(index-16);
            }
            if(index-7 > -1 && index%8<(index-7)%8 && enPassant.get(index-7) && enPassantType == 2){
                movement.set(index-7);
            }
            if(index-9 > -1 && index%8>(index-9)%8 && enPassant.get(index-9) && enPassantType == 2){
                movement.set(index-9);
            }
        }
        return movement;
    }

    private void diagonal(int index, BitSet movement, BitSet allyBoard, BitSet enemyBoard){

        //Down-Right
        for (int i = index + 9; i < 64; i = i + 9) {
            if( i%8 == 0) { break;}else{
                if (allyBoard.get(i)) {
                    break;
                } else {
                    if (enemyBoard.get(i)) {
                        movement.set(i);
                        break;
                    } else {
                        movement.set(i);
                    }
                }
            }
        }

        //Down-Left
        for (int i = index + 7; i < 64; i = i + 7) {
            if (index%8 < i%8) {break;}else{
                if (allyBoard.get(i)) {
                    break;
                } else {
                    if (enemyBoard.get(i)) {
                        movement.set(i);
                        break;
                    } else {
                        movement.set(i);
                    }
                }
            }
        }

        //Up-Left
        for (int i = index - 9; i > 0; i = i - 9) {
            if (index%8 < i%8) {break;}else {
                if (allyBoard.get(i)) {
                    break;
                } else {
                    if (enemyBoard.get(i)) {
                        movement.set(i);
                        break;
                    } else {
                        movement.set(i);
                    }
                }
            }
        }

        //Up-Right
        for (int i = index - 7; i > 0; i = i - 7) {
            if(i%8 == 0){break;}else {
                if (allyBoard.get(i)) {
                    break;
                } else {
                    if (enemyBoard.get(i)) {
                        movement.set(i);
                        break;
                    } else {
                        movement.set(i);
                    }
                }
            }
        }
    }

    private void sideways(int index, BitSet movement, BitSet allyBoard, BitSet enemyBoard){
        //Down
        for (int i = index + 8; i < 64; i = i + 8) {
            if (allyBoard.get(i)) {
                break;
            } else {
                if (enemyBoard.get(i)) {
                    movement.set(i);
                    break;
                } else {
                    movement.set(i);
                }
            }
        }

        //Right
        for (int i = index + 1; i < (index/8)*8 + 8; ++i) {
            if (allyBoard.get(i)) {
                break;
            } else {
                if (enemyBoard.get(i)) {
                    movement.set(i);
                    break;
                } else {
                    movement.set(i);
                }
            }
        }

        //Up
        for (int i = index - 8; i > -1; i = i - 8) {
            if (allyBoard.get(i)) {
                break;
            } else {
                if (enemyBoard.get(i)) {
                    movement.set(i);
                    break;
                } else {
                    movement.set(i);
                }
            }
        }

        //Left
        for (int i = index - 1; i > (index/8)*8 -1; i = i - 1) {
            if (allyBoard.get(i)) {
                break;
            } else {
                if (enemyBoard.get(i)) {
                    movement.set(i);
                    break;
                } else {
                    movement.set(i);
                }
            }
        }
    }

    private boolean in (int i, int[] ugh){
        for(int u : ugh){
            if(i == u){
                return true;
            }
        }
        return false;
    }

    private ChessGame copyThis(){
        return new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone(),
                (BitSet)this.enPassant.clone(), enPassantType, this.whiteCastleRight, this.whiteCastleLeft, this.blackCastleRight, this.blackCastleLeft);
    }

    public int gameLoop(int fromSq, int toSq) {
        if (legalMove(fromSq, toSq)) {
            move(fromSq, toSq, pieceFinder(fromSq));
            if(blackInMate()){
                return 2;
            }
			long startTime = System.nanoTime();
            simulate2ply();
            if(whiteInMate()){
                return 1;
            }
			System.out.printf("\nElapsed Time: %.8f ns\n",(System.nanoTime() - startTime)*0.000000001);
            System.out.println("Your Turn");
        } else {
            System.out.println("Wrong Move");
        }
        return 0;
    }

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


    private int blackBoardEvaluation(){
        int totalValue = blackPawns.boardValue() + blackRooks.boardValue() + blackRooks.boardValue()
                + blackBishops.boardValue() + blackQueens.boardValue() + blackKing.boardValue();
        for(int index = blackPawns.nextSetBit(0); index > -1; index = blackPawns.nextSetBit(index+1)){
            totalValue = totalValue + this.blackPawnPositionalValues[index];
        }
        for(int index = blackRooks.nextSetBit(0); index > -1; index = blackRooks.nextSetBit(index+1)){
            totalValue = totalValue + this.blackRookPositionalValues[index];
        }
        for(int index = blackKnights.nextSetBit(0); index > -1; index = blackKnights.nextSetBit(index+1)){
            totalValue = totalValue + this.blackKnightPositionalValues[index];
        }
        for(int index = blackBishops.nextSetBit(0); index > -1; index = blackBishops.nextSetBit(index+1)){
            totalValue = totalValue + this.blackBishopPositionalValues[index];
        }
        for(int index = blackQueens.nextSetBit(0); index > -1; index = blackQueens.nextSetBit(index+1)){
            totalValue = totalValue + this.blackQueensPositionalValues[index];
        }
        for(int index = blackKing.nextSetBit(0); index > -1; index = blackKing.nextSetBit(index+1)){
            totalValue = totalValue + this.blackKingPositionalValues[index];
        }
        return totalValue;
    }

    private int whiteBoardEvaluation(){
        int totalValue = whitePawns.boardValue() + whiteRooks.boardValue() + whiteKnights.boardValue()
                + whiteBishops.boardValue() + whiteQueens.boardValue() + whiteKing.boardValue();
        for(int index = whitePawns.nextSetBit(0); index > -1; index = whitePawns.nextSetBit(index+1)){
            totalValue = totalValue + this.whitePawnPositionalValues[index];
        }
        for(int index = whiteRooks.nextSetBit(0); index > -1; index = whiteRooks.nextSetBit(index+1)){
            totalValue = totalValue + this.whiteRookPositionalValues[index];
        }
        for(int index = whiteKnights.nextSetBit(0); index > -1; index = whiteKnights.nextSetBit(index+1)){
            totalValue = totalValue + this.whiteKnightPositionalValues[index];
        }
        for(int index = whiteBishops.nextSetBit(0); index > -1; index = whiteBishops.nextSetBit(index+1)){
            totalValue = totalValue + this.whiteBishopPositionalValues[index];
        }
        for(int index = whiteQueens.nextSetBit(0); index > -1; index = whiteQueens.nextSetBit(index+1)){
            totalValue = totalValue + this.whiteQueensPositionalValues[index];
        }
        for(int index = whiteKing.nextSetBit(0); index > -1; index = whiteKing.nextSetBit(index+1)){
            totalValue = totalValue + this.whiteKingPositionalValues[index];
        }
        return totalValue;
    }

    private int totalBoardValue(){

        return blackBoardEvaluation()
                + whiteBoardEvaluation();
    }

    public void saveState(){
        try {
            scannerIn = new Scanner(System.in);
            System.out.print("Save as >> ");
            saveAs = scannerIn.nextLine();
            Path path = Paths.get(".","save", saveAs);
            Path folder = Paths.get(".","save");
            if(Files.exists(folder)) {
                writer = new ObjectOutputStream(new FileOutputStream(path.toString()));
            }else{
                new File("./save/").mkdir();
                writer = new ObjectOutputStream(new FileOutputStream(path.toString()));
            }
            writer.writeObject(new Object[] {
                    blackPawns, blackRooks, blackKnights, blackBishops, blackQueens, blackKing,
                    whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueens, whiteKing,
                    enPassant, enPassantType, whiteCastleRight, whiteCastleLeft,
                    blackCastleRight, blackCastleLeft
            });
        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void loadState(){
        try {
            scannerIn = new Scanner(System.in);
            System.out.print("File name >> ");
            loadFrom = scannerIn.nextLine();
            Path path = Paths.get(".","save", loadFrom);
            ObjectInputStream reader = new ObjectInputStream(new FileInputStream(path.toString()));
            Object[] boards = (Object[])reader.readObject();
            blackPawns = (BitBoard) boards[0];
            blackRooks = (BitBoard)boards[1];
            blackKnights = (BitBoard)boards[2];
            blackBishops = (BitBoard)boards[3];
            blackQueens = (BitBoard)boards[4];
            blackKing = (BitBoard)boards[5];

            whitePawns = (BitBoard)boards[6];
            whiteRooks = (BitBoard)boards[7];
            whiteKnights = (BitBoard)boards[8];
            whiteBishops = (BitBoard)boards[9];
            whiteQueens = (BitBoard)boards[10];
            whiteKing = (BitBoard)boards[11];

            enPassant = (BitSet) boards[12];
            enPassantType = (int) boards[13];

            whiteCastleRight = (boolean)boards[14];
            whiteCastleLeft = (boolean)boards[15];
            blackCastleRight = (boolean)boards[16];
            blackCastleLeft = (boolean)boards[17];
            updateFullBoards();
        }
        catch(java.io.IOException e){
            e.printStackTrace();
            //System.exit(1);
        }
        catch(java.lang.ClassNotFoundException e){
            e.printStackTrace();
            System.exit(1);
        }
        catch(java.lang.IndexOutOfBoundsException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private int[] piecesIndexes(BitSet board){
        int arraySize = board.cardinality();
        int i = 0;
        int[] indexes = new int[arraySize];
        for(int index = board.nextSetBit(0); index >= 0; index = board.nextSetBit(index+1)){
            indexes[i] = index;
            ++i;
        }
        return indexes;
    }

    public int[] blackPawnIndexes(){
        return piecesIndexes(blackPawns);
    }

    public int[] blackRookIndexes(){
        return piecesIndexes(blackRooks);
    }

    public int[] blackKnightIndexes(){
        return piecesIndexes(blackKnights);
    }

    public int[] blackBishopIndexes(){
        return piecesIndexes(blackBishops);
    }

    public int[] blackQueenIndexes(){
        return piecesIndexes(blackQueens);
    }

    public int[] blackKingIndexes(){
        return piecesIndexes(blackKing);
    }

    public int[] whitePawnIndexes(){
        return piecesIndexes(whitePawns);
    }

    public int[] whiteRookIndexes(){
        return piecesIndexes(whiteRooks);
    }

    public int[] whiteKnightIndexes(){
        return piecesIndexes(whiteKnights);
    }

    public int[] whiteBishopIndexes(){
        return piecesIndexes(whiteBishops);
    }

    public int[] whiteQueenIndexes(){
        return piecesIndexes(whiteQueens);
    }

    public int[] whiteKingIndexes(){
        return piecesIndexes(whiteKing);
    }

    public BitSet movementIndexes(int location){
        BitBoard board = pieceFinder(location);
        switch (board.pieceValue()){
            case PAWN:
                if(blackBoard.get(location)){
                    return blackPawnMovement(location, blackBoard, whiteBoard);
                }else{
                    return whitePawnMovement(location, whiteBoard, blackBoard);
                }
            case ROOK:
                if(blackBoard.get(location)){
                    return rookMovement(location, blackBoard, whiteBoard);
                }else{
                    return rookMovement(location, whiteBoard, blackBoard);
                }
            case KNIGHT:
                if(blackBoard.get(location)){
                    return knightMovement(location, blackBoard, whiteBoard);
                }else{
                    return knightMovement(location, whiteBoard, blackBoard);
                }
            case BISHOP:
                if(blackBoard.get(location)){
                    return bishopMovement(location, blackBoard, whiteBoard);
                }else{
                    return bishopMovement(location, whiteBoard, blackBoard);
                }
            case QUEEN:
                if(blackBoard.get(location)){
                    return queenMovement(location, blackBoard, whiteBoard);
                }else{
                    return queenMovement(location, whiteBoard, blackBoard);
                }
            case KING:
                if(blackBoard.get(location)){
                    return kingMovement(location, blackBoard, 2);
                }else{
                    return kingMovement(location, whiteBoard, 1);
                }
            default:
                return new BitSet(0);
        }
    }

    private boolean whiteInMate(){
        boolean isItMate = true;

        for(int i = whiteQueens.nextSetBit(0); i >= 0; i = whiteQueens.nextSetBit(i+1)){
            BitSet movements = queenMovement(i, whiteBoard, blackBoard);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                isItMate = isItMate && inCheck(i, j, 1);
            }
        }

        for(int i = whitePawns.nextSetBit(0); i >= 0; i = whitePawns.nextSetBit(i+1)){
            BitSet movements = whitePawnMovement(i, whiteBoard, blackBoard);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                isItMate = isItMate && inCheck(i, j, 1);
            }
        }

        for(int i = whiteBishops.nextSetBit(0); i >= 0; i = whiteBishops.nextSetBit(i+1)){
            BitSet movements = bishopMovement(i, whiteBoard, blackBoard);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                isItMate = isItMate && inCheck(i, j, 1);
            }
        }

        for(int i = whiteKnights.nextSetBit(0); i >= 0; i = whiteKnights.nextSetBit(i+1)){
            BitSet movements = knightMovement(i, whiteBoard, blackBoard);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                isItMate = isItMate && inCheck(i, j, 1);
            }
        }

        for(int i = whiteRooks.nextSetBit(0); i >= 0; i = whiteRooks.nextSetBit(i+1)){
            BitSet movements = rookMovement(i, whiteBoard, blackBoard);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                isItMate = isItMate && inCheck(i, j, 1);
            }
        }

        for(int i = whiteKing.nextSetBit(0); i >= 0; i = whiteKing.nextSetBit(i+1)){
            BitSet movements = kingMovement(i, whiteBoard, 1);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                isItMate = isItMate && inCheck(i, j, 1);
            }
        }
        return isItMate;
    }

    private boolean blackInMate(){

        ChessGame ChessGameCopy = copyThis();
        int value = ChessGameCopy.simulate2ply();
        return value<-500000;
    }
}
